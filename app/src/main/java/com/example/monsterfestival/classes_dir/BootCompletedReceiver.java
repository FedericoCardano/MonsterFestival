package com.example.monsterfestival.classes_dir;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                    DatabaseUpdateWorker.class,
                    sharedPreferences.getInt("intervalPeriodicWork", 24 * 60 * 60 * 1000),
                    TimeUnit.MILLISECONDS)
                    .build();

            WorkManager.getInstance(context).enqueue(periodicWorkRequest);
        }
    }
}