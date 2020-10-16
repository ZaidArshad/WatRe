package zaid.d.waterreminder20;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.text.SimpleDateFormat;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    final static public int MILLIS_PER_HOUR = 3600000; // One hour in milliseconds
    final static public int MILLIS_PER_HOUR_AND_HALF = 5400000; // One hour and 30 minutes in milliseconds

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm:ss a", Locale.CANADA);

    // Times used for testing the app is a short amount of time
    //final static public int MILLIS_PER_HOUR = 3000; // 3 seconds
    //final static public int MILLIS_PER_HOUR_AND_HALF = 5000; // 5 seconds

    private static int previousGlassesConsumed;

    /* DEBUG
    private static int buttonClicks;
    private static long firstTimeOpened;
    private static long nextDayTime;
    private static long nextDrinkTime;

     */


    private TimeManager timeManager = new TimeManager(this);
    private DataManager dataManager = new DataManager(this);

    public DrinkButton drinkButton;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    // When app is opened
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checks if it is a new day - resets data, checks if user can press button
        timeManager.timeCheck();

        // Gets main variables from storage
        loadData();

        // Setups layout objects
        setUp();

        // Checks if the button is clicked
        drinkButton.buttonClicked();

        TextView debug = findViewById(R.id.test);
        debug.setText("");

        /* DEBUG
        TextView debug = findViewById(R.id.test);
        String glassesConsumed = "Glasses Consumed: " + previousGlassesConsumed + "\n";
        String buttonClicksLeft = "Button clicks left: " + buttonClicks + "\n";
        String firstTime = "First time opened: " + simpleDateFormat.format(firstTimeOpened) + "\n";
        String nextDay = "Next Day: " + simpleDateFormat.format(nextDayTime) + "\n";
        String nextTime = "Next drink time" + simpleDateFormat.format(nextDrinkTime) + "\n";
        String debugString = glassesConsumed + buttonClicksLeft + firstTime + nextDay + nextTime;
        debug.setText(debugString);
         */
    }


    // Every time app is opened following is run
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUp() {
        setContentView(R.layout.activity_main);

        // Background animation
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setAlpha(0.5f);
        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        Background background = new Background(this, progressBar , constraintLayout);
        background.animate();

        // Setting the layout objects
        TextView glassesText = findViewById(R.id.glassesText);
        glassesText.setText(String.valueOf(previousGlassesConsumed));
        TextView nextDrinkText = findViewById(R.id.nextDrinkTimeText);
        nextDrinkText.setText(simpleDateFormat.format(dataManager.getNextDrinkTime()));

        // Builds the main button
        Button addButton = findViewById(R.id.addButton);
        drinkButton = new DrinkButton(this, addButton, glassesText, nextDrinkText, background);

        // Setting up the background to match the amount of water the user has consumed
        background.setProgressBar();

        // Enables the button if the user can drink
        drinkButton.isButtonClickable();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadData() {
        previousGlassesConsumed = dataManager.getPreviousGlassesConsumed();

        /* DEBUG
        buttonClicks = dataManager.getButtonClicks();
        firstTimeOpened = dataManager.getFirstOpenedTime();
        nextDayTime = dataManager.getNextDayTime();
        nextDrinkTime = dataManager.getNextDrinkTime();
         */
    }


}



















