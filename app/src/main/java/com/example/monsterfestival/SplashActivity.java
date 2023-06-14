package com.example.monsterfestival;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.TimeUnit;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    Button buttonPopUp;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        startCode();

    }

    private void startCode() {

        if (sharedPreferences.getBoolean("firstLaunch", true)) {

            try {

                PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
                ActivityCompat.requestPermissions(this, packageInfo.requestedPermissions, 0);

            } catch (PackageManager.NameNotFoundException e) {
                // Handle the exception (TO DO)
            }

            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> { });
            FirebaseMessaging.getInstance().subscribeToTopic("Aggiornamenti_Database").addOnCompleteListener(task -> { });

            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                    DatabaseUpdateWorker.class,
                    /* intervallo di controllo in millisecondi */ 24 * 60 * 60 * 1000,
                    TimeUnit.MILLISECONDS
            ).build();


            Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(DatabaseUpdateWorker.class).setConstraints(constraints).build();
            WorkManager.getInstance(this).enqueue(workRequest);
            WorkManager.getInstance(this).enqueueUniquePeriodicWork("Aggiornamenti_Database", ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);
        }
        else
            continueCode();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        continueCode();

    }

    private void continueCode() {

        if (sharedPreferences.getBoolean("firstLaunch", true)) {
            if (isNetworkConnected(this)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("firstLaunch", false);
                editor.apply();

                startApp();
            }
            else {
                dialog = new Dialog(SplashActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.no_internet_pop_up);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                dialog.show();

                buttonPopUp = dialog.findViewById(R.id.retry_button);
                buttonPopUp.setOnClickListener(view -> {
                    dialog.dismiss();
                    startCode();
                });
            }
        }
        else
            startApp();



    }

    private void startApp() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            finish();
        }, 3000);
    }

    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork != null) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
                return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
            }
        }

        return false;
    }

}