package zaid.d.waterreminder20;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.WrapperListAdapter;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class HourlyAlertReceiver extends BroadcastReceiver  {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Sends the notification for every hour
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelOneNotification();
        notificationHelper.getManager().notify(1, nb.build());
        DataManager dataManager = new DataManager(context);
        dataManager.saveButtonClicks(dataManager.getButtonClicks()+1);

    }
}
