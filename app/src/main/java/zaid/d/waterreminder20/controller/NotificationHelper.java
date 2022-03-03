package zaid.d.waterreminder20.controller;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import zaid.d.waterreminder20.R;

public class NotificationHelper extends ContextWrapper {

    // The channels of where to send the notifications
    public static final String CHANNEL_ONE_ID = "channelID";
    public static final String CHANNEL_TWO_ID = "channel2ID";
    // Channels names of where to send the notification
    public static final String HOURLY = "Hourly";
    public static final String DAILY = "Daily";

    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        // Creating the channel for the hourly notifications
        NotificationChannel channelOne = new NotificationChannel(CHANNEL_ONE_ID, HOURLY, NotificationManager.IMPORTANCE_HIGH);
        channelOne.enableLights(true);
        channelOne.enableVibration(true);
        channelOne.setLightColor(R.color.colorAccent);
        channelOne.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channelOne);

        // Creating the channel for the daily notification
        NotificationChannel channelTwo = new NotificationChannel(CHANNEL_TWO_ID, DAILY, NotificationManager.IMPORTANCE_HIGH);
        channelTwo.enableLights(true);
        channelTwo.enableVibration(true);
        channelTwo.setLightColor(R.color.colorAccent);
        channelTwo.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channelTwo);
    }
    public NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public NotificationCompat.Builder getChannelOneNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ONE_ID)
                .setContentTitle("Drink Water")
                .setContentText("Drink your hourly glass of water")
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setSmallIcon(R.drawable.ic_cup)
                .setOngoing(true);
    }
    public NotificationCompat.Builder getChannelTwoNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_TWO_ID)
                .setContentTitle("Good Morning")
                .setContentText("It's time to start drinking water")
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setSmallIcon(R.drawable.ic_cup)
                .setOngoing(true);
    }

}