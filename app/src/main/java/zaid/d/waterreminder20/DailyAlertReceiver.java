package zaid.d.waterreminder20;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


import androidx.annotation.RequiresApi;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

public class DailyAlertReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        // Sends the notification for every day
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelTwoNotification();
        notificationHelper.getManager().notify(2, nb.build());
        DataManager dataManager = new DataManager(context);
        dataManager.saveButtonClicks(dataManager.getButtonClicks()+1);

    }
}
