package com.Ariyan.demo.service;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.Ariyan.demo.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationReadReceiver extends FirebaseMessagingService {
    private static final String TAG = NotificationReadReceiver.class.getSimpleName();
    private static final int NOTIFICATION_ID = 505;
    private static final String CHANNEL_ID = "delivery_ch";
    private static final int REQUEST_CODE_NOTIFICATION_PERMISSION = 101;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM", "Refreshed token: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        // Handle data payload of the message
        if (message.getData().size() > 0) {
            Log.d("FCM", "Message data payload: " + message.getData());
            // Extract data from message and perform actions
        }

        // Check for notification and display it
        if (message.getNotification() != null) {
            Log.d("FCM", "Message notification body: " + message.getNotification().getBody());
            notification(message);
            // Use NotificationCompat to build and display notification
        }
    }

    private void notification(RemoteMessage remoteMessage) {
        try {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            // Create a notification (you can customize this)
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(title)
                            .setContentText(body)
                            .setAutoCancel(true);

            // Alternatively, you can handle data payloads from the message here

            // Send notification (consider using NotificationManagerCompat)
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                        PackageManager.PERMISSION_GRANTED) {

                }
            }
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        }catch (Exception e){
        }
    }

}