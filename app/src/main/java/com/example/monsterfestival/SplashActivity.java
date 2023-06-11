package com.example.monsterfestival;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;

import com.example.customsearchlibrary.NativeLib;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private final Object ThreadLock = new Object();
    private int operationCounter = 0;

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
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Monster");

        String temp = sharedPreferences.getString("objectNativeLib", "");
        NativeLib objectNativeLib;
        if (temp.equals(""))
            objectNativeLib = new NativeLib();
        else
            objectNativeLib = new Gson().fromJson(temp, NativeLib.class);
        databaseReference.child("Filtri").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                objectNativeLib.setFiltri(dataSnapshot);
                synchronized (ThreadLock) {
                    if (++operationCounter > 1) {
                        objectNativeLib.updateDatabase();
                        editor.putString("objectNativeLib", new Gson().toJson(objectNativeLib));
                        editor.apply();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        databaseReference.child("ID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                objectNativeLib.setID(dataSnapshot);
                synchronized (ThreadLock) {
                    if (++operationCounter > 1) {
                        objectNativeLib.updateDatabase();
                        editor.putString("objectNativeLib", new Gson().toJson(objectNativeLib));
                        editor.apply();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            finish();
        }, 3000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}