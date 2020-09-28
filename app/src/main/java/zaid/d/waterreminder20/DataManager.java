package zaid.d.waterreminder20;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import static android.widget.Toast.LENGTH_SHORT;

public class DataManager extends AppCompatActivity {

    // ATTRIBUTES //
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String PREVIOUS_GLASSES_CONSUMED = "previousWaterConsumed";
    public static final String BUTTON_CLICKS = "buttonClicks";
    public static final String FIRST_OPENED_TIME = "firstOpenedTime";
    public static final String LAST_BUTTON_PRESS_TIME = "lastButtonPressTime";
    public static final String NEXT_DRINK_TIME = "nextDrinkTime";

    private Context context;

    // CONSTRUCTOR //
    public DataManager(Context c) {
        context = c;
    }


    // GETTERS //

    // Gets the amount of glasses that the user has consumed for the current day
    public int getPreviousGlassesConsumed() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt(PREVIOUS_GLASSES_CONSUMED, 0);
    }

    // Gets the amount of times that the user can press the button
    public int getButtonClicks() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt(BUTTON_CLICKS, 0);
    }

    // Gets the first time the user pressed the button for the day
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public long getFirstOpenedTime() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getLong(FIRST_OPENED_TIME, 0);
    }

    // Gets the last time the user pressed the button
    public long getLastButtonPressTime() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getLong(LAST_BUTTON_PRESS_TIME, 0);
    }

    // Gets the next time that the button can be pressed
    public long getNextDrinkTime() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getLong(NEXT_DRINK_TIME, System.currentTimeMillis() + MainActivity.MILLIS_PER_HOUR_AND_HALF);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public long getNextDayTime() {
        return (getFirstOpenedTime() + (18 * MainActivity.MILLIS_PER_HOUR));
    }

    // SETTERS //

    // Saves the amount of glasses the user has consumed for the current day
    public void savePreviousGlassesConsumed(int glassesConsumed) {
        SharedPreferences.Editor editor = loadEditor();
        editor.putInt(PREVIOUS_GLASSES_CONSUMED, glassesConsumed);
        editor.apply();
    }

    // Saves the amount of times the user can press the button
    public void saveButtonClicks(int buttonClicks) {
        SharedPreferences.Editor editor = loadEditor();
        editor.putInt(BUTTON_CLICKS, buttonClicks);
        editor.apply();
    }

    // Saves the first time of the day the button was pressed
    public void saveFirstOpenedTime(long firstOpenedTime) {
        SharedPreferences.Editor editor = loadEditor();
        editor.putLong(FIRST_OPENED_TIME, firstOpenedTime);
        editor.apply();
    }

    // Saves the the last time the button was pressed
    public void saveLastButtonPressTime(long lastButtonPressTime) {
        SharedPreferences.Editor editor = loadEditor();
        editor.putLong(LAST_BUTTON_PRESS_TIME, lastButtonPressTime);
        editor.apply();
    }

    public void saveNextDrinkTime(long nextDrinkTime) {
        SharedPreferences.Editor editor = loadEditor();
        editor.putLong(NEXT_DRINK_TIME, nextDrinkTime);
        editor.apply();
    }



    // OTHER METHODS //

    // Loads the editor for the apps saved data
    public SharedPreferences.Editor loadEditor() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.edit();
    }

}














