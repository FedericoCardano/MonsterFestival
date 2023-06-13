package com.example.monsterfestival;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.TimeUnit;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        boolean firstLaunch = sharedPreferences.getBoolean("firstLaunch", true);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (firstLaunch) {
            editor.putBoolean("firstLaunch", false);
            editor.apply();

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

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            finish();
        }, 3000);

    }

}