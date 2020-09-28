package zaid.d.waterreminder20;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    final static public int MILLIS_PER_HOUR = 3600000;
    final static public int MILLIS_PER_HOUR_AND_HALF = 5400000;
    //final static public int MILLIS_PER_HOUR = 3000;
    //final static public int MILLIS_PER_HOUR_AND_HALF = 5000;


    private static int previousGlassesConsumed;
    private static int buttonClicks;
    private static long firstTimeOpened;
    private static long nextDayTime;
    private static long nextDrinkTime;

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

        // DEBUG
        TextView debug = findViewById(R.id.test);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm:ss a", Locale.CANADA);

        String glassesConsumed = "Glasses Consumed: " + previousGlassesConsumed + "\n";
        String buttonClicksLeft = "Button clicks left: " + buttonClicks + "\n";
        String firstTime = "First time opened: " + simpleDateFormat.format(firstTimeOpened) + "\n";
        String nextDay = "Next Day: " + simpleDateFormat.format(nextDayTime) + "\n";
        String nextTime = "Next drink time" + simpleDateFormat.format(nextDrinkTime) + "\n";
        String debugString = glassesConsumed + buttonClicksLeft + firstTime + nextDay + nextTime;
        debug.setText(debugString);
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

        // Builds the main button
        Button addButton = findViewById(R.id.addButton);
        drinkButton = new DrinkButton(this, addButton, glassesText, background);

        // Setting up the background to match the amount of water the user has consumed
        background.setProgressBar();

        // Enables the button if the user can drink
        drinkButton.isButtonClickable();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadData() {
        previousGlassesConsumed = dataManager.getPreviousGlassesConsumed();
        buttonClicks = dataManager.getButtonClicks();
        firstTimeOpened = dataManager.getFirstOpenedTime();
        nextDayTime = dataManager.getNextDayTime();
        nextDrinkTime = dataManager.getNextDrinkTime();
    }


}



















