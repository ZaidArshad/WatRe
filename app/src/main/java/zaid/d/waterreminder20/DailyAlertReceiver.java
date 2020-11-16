package zaid.d.waterreminder20;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class DailyAlertReceiver extends BroadcastReceiver{

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        // Sends the notification for every day
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelTwoNotification();
        notificationHelper.getManager().notify(2, nb.build());

        // Cancels any hourly notifications that may have rolled over to the next day
        notificationHelper.getManager().cancel(1);

        // Updates the amount of times the user can click the button
        DataManager dataManager = new DataManager(context);
        dataManager.saveButtonClicks(dataManager.getButtonClicks()+1);

        // Cancels any alarms that overlap to the next day
        TimeManager timeManager = new TimeManager(context);
        timeManager.cancelHourlyAlarm();


        Intent i = new Intent();
        i.setClassName("zaid.d.waterreminder20", "zaid.d.waterreminder20.MainActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
