package com.example.monsterfestival;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private final String Tag = "MyFirebaseMessagingService";
    
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(Tag, "From: " + remoteMessage.getFrom());

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(DatabaseUpdateWorker.class).setConstraints(constraints).build();
        WorkManager.getInstance(this).enqueue(workRequest);

    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(Tag, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DeviceTokens");
        databaseReference.child(token).setValue(true);
    }

}