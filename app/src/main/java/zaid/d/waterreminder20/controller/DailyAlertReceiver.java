package zaid.d.waterreminder20.controller;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import zaid.d.waterreminder20.model.DataManager;
import zaid.d.waterreminder20.view.MainActivity;
import zaid.d.waterreminder20.model.TimeManager;

public class DailyAlertReceiver extends BroadcastReceiver{

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        // Sends the notification for every day
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelTwoNotification();

        // Opens the app when notification is clicked
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        Notification notification = nb.build();
        notification.contentIntent = pendingIntent;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationHelper.getManager().notify(2, notification);

        // Cancels any hourly notifications that may have rolled over to the next day
        notificationHelper.getManager().cancel(1);

        // Updates the amount of times the user can click the button
        DataManager dataManager = new DataManager(context);
        dataManager.saveButtonClicks(dataManager.getButtonClicks()+1);

        // Cancels any alarms that overlap to the next day
        TimeManager timeManager = new TimeManager(context);
        timeManager.cancelHourlyAlarm();

        WebReciever.setReminding(context, true);

        Intent i = new Intent();
        i.setClassName("zaid.d.waterreminder20", "zaid.d.waterreminder20.view.MainActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
