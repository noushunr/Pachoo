package com.presentation.app.dealsnest.fcm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.ui.notification.notification_details.NotificationDetailActivity;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = "MyFirebase";
    private Intent intent = null;
    private String ID = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("FireBaseMessage", remoteMessage.getData().toString());

        if (remoteMessage.getData().containsKey("notification_id")) {
            ID = remoteMessage.getData().get("notification_id");
        }

        try {
            intent = new Intent(this, NotificationDetailActivity.class);
            intent.putExtra("id", ID);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0  /*Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                @SuppressLint("WrongConstant")
                NotificationChannel notificationChannel = new NotificationChannel("notification_id", "n_channel", NotificationManager.IMPORTANCE_MAX);
                notificationChannel.setDescription("description");
                notificationChannel.setName("Channel Name");
                notificationManager.createNotificationChannel(notificationChannel);
            }

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this, "")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(getString(R.string.dealsnest))
                            .setContentText(remoteMessage.getData().get("title"))
                            .setSubText(remoteMessage.getData().get("body"))
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setChannelId("notification_id")
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

            notificationManager.notify(0, builder.build());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}