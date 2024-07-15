package com.Ariyan.demo.service;


import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.Ariyan.demo.R;
import com.Ariyan.demo.controller.MyControl;
import com.Ariyan.demo.network.Api;
import com.Ariyan.demo.network.RetrofitClient;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationReadReceiver extends FirebaseMessagingService {
    private static final String TAG = NotificationReadReceiver.class.getSimpleName();
    private static final int NOTIFICATION_ID = 505;
    private SharedPreferences sharedPreferences;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM", "Refreshed token: " + token);
        sharedPreferences = getSharedPreferences(MyControl.SHARED_DATA,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MyControl.TOKEN,token);
        editor.apply();
        String id = sharedPreferences.getString(MyControl.UID, "");
        if (!id.isEmpty()){
            Api api = RetrofitClient.getInstance();
            Map<String, String> map = new HashMap<>();
            map.put(MyControl.UID,id);
            map.put(MyControl.TOKEN,token);
            api.UpdateToken(map).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(NotificationReadReceiver.this, response.body(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    Toast.makeText(NotificationReadReceiver.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle(title)
                            .setContentText(body)
                            .setAutoCancel(false)
                            .setSound(defaultSoundUri)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);

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