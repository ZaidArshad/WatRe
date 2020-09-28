package zaid.d.waterreminder20;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Background extends Application {

    // ATTRIBUTES //
    private Context context;
    private ProgressBar progressBar;
    private ConstraintLayout constraintLayout;
    private DataManager dataManager;

    // CONSTRUCTOR //
    public Background(Context c, ProgressBar pg, ConstraintLayout cL) {
        context = c;
        dataManager = new DataManager(c);
        progressBar = pg;
        constraintLayout = cL;
    }

    // METHODS //

    // Sets the background to a progress bar that increases with every button click
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setProgressBar() {
        int progressValue = (13*  dataManager.getPreviousGlassesConsumed());
        progressBar.setProgress(progressValue,true);
    }

    // Animates the gradient in the background
    public void animate() {
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }
}
