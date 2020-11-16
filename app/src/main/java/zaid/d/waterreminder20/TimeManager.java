package zaid.d.waterreminder20;


import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class TimeManager extends AppCompatActivity {

    // ATTRIBUTES //
    private Context context;
    private DataManager dataManager;


    // CONSTRUCTOR //
    public TimeManager(Context c) {
        context = c; // Gets the context of the main activity
        dataManager = new DataManager(context); // Gives the class access to app's shared preferences
    }

    // METHODS //

    // Checks if it is 18 hours from the first time button was clicked
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean isNextDay() {
        return (System.currentTimeMillis() > dataManager.getNextDayTime());
    }

    // If it is a new day, update the storage
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void nextDay() {
        if (isNextDay()) {
            cancelDailyAlarm();
            Toast.makeText(context,"new day",Toast.LENGTH_SHORT).show();
            dataManager.saveFirstOpenedTime(System.currentTimeMillis());
            dataManager.saveButtonClicks(1);
            dataManager.savePreviousGlassesConsumed(0);
            startDailyAlarm(dataManager.getNextDayTime());
        }
    }

    // If it is time for the user to drink, increase the amount of button clicks
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void increaseClicks() {
        if (System.currentTimeMillis() > dataManager.getNextDrinkTime() && dataManager.getPreviousGlassesConsumed() <= 8) {
            dataManager.saveButtonClicks(dataManager.getButtonClicks()+1);
        }
    }

    // Sets a new time for the user to make their next drink
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void scheduleNextDrink() {
        cancelHourlyAlarm();
        if (dataManager.getPreviousGlassesConsumed() < 8) {
            dataManager.saveNextDrinkTime(System.currentTimeMillis() + MainActivity.MILLIS_PER_HOUR_AND_HALF);
            startHourlyAlarm(dataManager.getNextDrinkTime());
        }
    }

    // A compilation of TimeManger methods used for the main activity
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void timeCheck() {
        firstTimeSetup();
        nextDay();
    }

    // Starts the alarm to remind the user to drink water every hour and a half
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startHourlyAlarm(long alarmTime) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, HourlyAlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

        assert alarmManager != null;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
    }


    // Cancels the alarm to remind the user to drink water every hour and a half
    public void cancelHourlyAlarm() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, HourlyAlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
        assert notificationManager != null;
        notificationManager.cancel(1);
    }


    // Starts the alarm to remind user to start drinking water
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startDailyAlarm(long alarmTime) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyAlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, intent, 0);

        assert alarmManager != null;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
    }

    // Cancels the alarm to remind user to start drinking water
    public void  cancelDailyAlarm() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyAlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, intent, 0);

        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
        assert notificationManager != null;
        notificationManager.cancel(2);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void firstTimeSetup() {
        long firstTime = dataManager.getFirstOpenedTime();
        if (firstTime==0) {
            startDailyAlarm(System.currentTimeMillis());
            dataManager.saveFirstOpenedTime(System.currentTimeMillis());
        }
    }




}










