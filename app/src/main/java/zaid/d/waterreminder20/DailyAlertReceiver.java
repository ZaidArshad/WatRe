package zaid.d.waterreminder20;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class DailyAlertReceiver extends BroadcastReceiver {
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

    }
}
