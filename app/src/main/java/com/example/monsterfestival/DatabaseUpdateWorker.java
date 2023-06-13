package com.example.monsterfestival;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.customsearchlibrary.NativeLib;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class DatabaseUpdateWorker extends Worker {

    private final Object ThreadLock = new Object();
    private int operationCounter = 0;

    public DatabaseUpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

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

        return Result.success();
    }
}
