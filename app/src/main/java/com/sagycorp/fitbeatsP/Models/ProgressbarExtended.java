package com.sagycorp.fitbeatsP.Models;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

/**
 * Created by Dzeko on 7/4/2017.
 */

public class ProgressbarExtended extends CircularProgressBar {

    private ObjectAnimator objectAnimator;



    public ProgressbarExtended(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setProgressWithAnimation(float progress, int duration) {
        objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }

    public void cancelAnimation(){
        if(objectAnimator != null){
            objectAnimator.cancel();
        }
    }
}
