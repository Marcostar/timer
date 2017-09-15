package com.example.dzeko.timer;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dzeko.timer.Services.ForegroundServices;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String TIME_STATUS = "time_status";
    public static final String TIME_LEFT = "time_left";
    public static final String TOTAL_TIME_LEFT = "total_time_left";
    public static final String SAVED_SET_NUMBER = "saved_set_number";
    public static final String FINAL_SET_NUMBER = "final_set_number";
    public static final String SAVED_CYCLE_NUMBER = "saved_cycle_number";
    public static final String FINAL_CYCLE_NUMBER = "final_cycle_number";
    public static final String WARM_UP_TIME = "warm_up_time";
    public static final String REST_TIME =  "rest_time";
    public static final String WORKOUT_TIME = "workout_time";
    public static final String REST_BETWEEN_CYCLE = "rest_between_cycle";


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private TextView status, minutes, seconds, setNumber, finalSetNumber, cycleNumber,
            finalCycleNumber, playPauseStatus, timeLeft;

    private ImageButton resumeButton, pauseButton, resetButton, playButton, settingsButton;

    private LinearLayout initialPlayButton, settingsButtonLayout, playLayout, resetLayout,
            settingLayout, footerLayout;
    private CountDownTimer sessionCountDownTimer, countDownTimer;

    private long remainingTimer, totalCountdownRemainingTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        initialiseLayout();
        clickEvents();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sharedPreferences.getBoolean("firstTime", true))
        {
            editor.putInt(FINAL_SET_NUMBER, 8);
            editor.putInt(FINAL_CYCLE_NUMBER, 1);
            editor.putInt(SAVED_CYCLE_NUMBER, 1);
            editor.putInt(WARM_UP_TIME,10);
            editor.putInt(REST_TIME, 10);
            editor.putInt(WORKOUT_TIME, 20);
            editor.putInt(REST_BETWEEN_CYCLE, 30);
         //change values here
            editor.putBoolean("firstTime", false).apply();
        }
    }

    private void resetValues() {
        sessionCountDownTimer.cancel();
        countDownTimer.cancel();
        setNumber.setText("0");
        cycleNumber.setText("1");
        editor.putInt(SAVED_SET_NUMBER, 0);
        editor.putInt(SAVED_CYCLE_NUMBER, 1).apply();
    }

    private void initialiseLayout() {

        status = findViewById(R.id.status);
        minutes = findViewById(R.id.minutes);
        seconds = findViewById(R.id.seconds);
        setNumber = findViewById(R.id.setNumber);
        finalSetNumber = findViewById(R.id.finalSetNumber);
        cycleNumber = findViewById(R.id.cycleNumber);
        finalCycleNumber = findViewById(R.id.finalCycleNumber);
        playPauseStatus = findViewById(R.id.playPauseStatus);
        timeLeft = findViewById(R.id.timeLeft);
        resumeButton = findViewById(R.id.resumeButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);
        playButton = findViewById(R.id.playButton);
        settingsButton = findViewById(R.id.settingsButton);
        initialPlayButton = findViewById(R.id.initialPlayButton);
        settingsButtonLayout = findViewById(R.id.settingsButtonLayout);
        playLayout = findViewById(R.id.playLayout);
        resetLayout = findViewById(R.id.resetLayout);
        settingLayout = findViewById(R.id.settingLayout);
        footerLayout = findViewById(R.id.footerLayout);

        setNumber.setText("0");
        finalSetNumber.setText(sharedPreferences.getInt(MainActivity.FINAL_SET_NUMBER,0)+"");

        cycleNumber.setText("1");
        editor.putInt(SAVED_CYCLE_NUMBER, sharedPreferences.getInt(SAVED_CYCLE_NUMBER,0) + 1).apply();
        finalCycleNumber.setText(sharedPreferences.getInt(MainActivity.FINAL_CYCLE_NUMBER,0)+"");
    }

    private void clickEvents() {

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialPlayButton.performClick();
            }
        });

        initialPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start the countdown
                editor.putInt(SAVED_SET_NUMBER, 0);
                editor.putInt(SAVED_CYCLE_NUMBER, 1).apply();
                settingLayout.setVisibility(View.GONE);
                footerLayout.setVisibility(View.VISIBLE);
                Intent i = new Intent(MainActivity.this, ForegroundServices.class);
                startService(i);
                countTotalTime();
                startTimer();

            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsButtonLayout.performClick();
            }
        });

        settingsButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start settings activity
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetLayout.performClick();
            }
        });

        resetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Reset the clock
                settingLayout.setVisibility(View.VISIBLE);
                footerLayout.setVisibility(View.GONE);
                resetValues();
            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start timer
                playLayout.performClick();

            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pause timer
                playLayout.performClick();
            }
        });

        playLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resumeButton.getVisibility()== View.VISIBLE)
                {
                    playPauseStatus.setText(R.string.pause);
                    resumeButton.setVisibility(View.GONE);
                    pauseButton.setVisibility(View.VISIBLE);
                    setTimers();

                }
                else if (pauseButton.getVisibility()== View.VISIBLE)
                {
                    sessionCountDownTimer.cancel();
                    countDownTimer.cancel();
                    playPauseStatus.setText(R.string.resume);
                    pauseButton.setVisibility(View.GONE);
                    resumeButton.setVisibility(View.VISIBLE);
                    editor.putLong(TIME_LEFT,remainingTimer);
                    editor.putLong(TOTAL_TIME_LEFT, totalCountdownRemainingTimer).apply();
                }
            }
        });
    }

    private void setTimers() {

        switch (sharedPreferences.getString(TIME_STATUS,""))
        {
            case WARM_UP_TIME:
                startSessionTimer(sharedPreferences.getLong(TOTAL_TIME_LEFT,0));
                warmUpTime(sharedPreferences.getLong(TIME_LEFT,0));
                break;
            case REST_TIME:
                startSessionTimer(sharedPreferences.getLong(TOTAL_TIME_LEFT,0));
                startRestBetweenSets(sharedPreferences.getLong(TIME_LEFT,0));
                break;
            case REST_BETWEEN_CYCLE:
                startSessionTimer(sharedPreferences.getLong(TOTAL_TIME_LEFT,0));
                startRestBetweenCycles(sharedPreferences.getLong(TIME_LEFT,0));
                break;
            case WORKOUT_TIME:
                startSessionTimer(sharedPreferences.getLong(TOTAL_TIME_LEFT,0));
                startWorkOutTime(sharedPreferences.getLong(TIME_LEFT,0));
                break;
        }
    }


    private void startTimer()
    {
        editor.putInt(SAVED_SET_NUMBER,1).apply();
        if(sharedPreferences.getInt(SAVED_CYCLE_NUMBER,0) <= sharedPreferences.getInt(FINAL_CYCLE_NUMBER,0))
        {
            //warm up time
            warmUpTime(sharedPreferences.getInt(WARM_UP_TIME,0)*1000);
        }

    }

    private void countTotalTime() {

        int warmUpTime = sharedPreferences.getInt(FINAL_CYCLE_NUMBER,0)* sharedPreferences.getInt(WARM_UP_TIME,0);
        int workOutTime = sharedPreferences.getInt(FINAL_SET_NUMBER,0)* sharedPreferences.getInt(WORKOUT_TIME,0);
        int restTime = (sharedPreferences.getInt(FINAL_SET_NUMBER,0)-1)* sharedPreferences.getInt(REST_TIME,0);
        int restBetweenCycles = sharedPreferences.getInt(FINAL_CYCLE_NUMBER,0)* sharedPreferences.getInt(REST_BETWEEN_CYCLE,0);

        startSessionTimer(((warmUpTime+workOutTime+restTime+restBetweenCycles)- sharedPreferences.getInt(REST_BETWEEN_CYCLE,0))*1000);
    }

    private void startSessionTimer(long totalDuration) {

        sessionCountDownTimer = new CountDownTimer(totalDuration, 1000) {

            public void onTick(long millisUntilFinished) {
                setTotalSessionTime(millisUntilFinished);
                totalCountdownRemainingTimer = millisUntilFinished;
            }

            public void onFinish() {
                //Start excercies
                timeLeft.setText(00+":"+00);
            }
        }.start();
    }

    private void setTotalSessionTime(long millisUntilFinished) {
        timeLeft.setText(TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished)+":"+ (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
    }


    private void warmUpTime(long totalDuration) {
        editor.putString(TIME_STATUS,WARM_UP_TIME).apply();
        status.setText(R.string.getReady);
        updateNotification();
        countDownTimer = new CountDownTimer(totalDuration, 1000) {

            public void onTick(long millisUntilFinished) {
                setTime(millisUntilFinished);
                remainingTimer = millisUntilFinished;
            }

            public void onFinish() {
                //Start excercies
                if(sharedPreferences.getInt(SAVED_SET_NUMBER,0) <= sharedPreferences.getInt(FINAL_SET_NUMBER,0))
                {
                    //warm up time
                    startExercise();
                }
            }
        }.start();

    }

    private void setTime(long millisUntilFinished) {
        minutes.setText(TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished)+"");
        seconds.setText(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))+"");
    }

    private void startExercise() {

        if(sharedPreferences.getInt(SAVED_SET_NUMBER,0) <= sharedPreferences.getInt(FINAL_SET_NUMBER,0))
        {
            updateNotification();
            //Exercise time
            setNumber.setText(sharedPreferences.getInt(SAVED_SET_NUMBER,0)+"");
            startWorkOutTime(sharedPreferences.getInt(WORKOUT_TIME,0)*1000);
        }
        else
        {
            editor.putInt(SAVED_CYCLE_NUMBER, sharedPreferences.getInt(SAVED_CYCLE_NUMBER,0) + 1).apply();
            if(sharedPreferences.getInt(SAVED_CYCLE_NUMBER,0) <= sharedPreferences.getInt(FINAL_CYCLE_NUMBER,0))
            {
                updateNotification();
                cycleNumber.setText(sharedPreferences.getInt(SAVED_CYCLE_NUMBER,0)+"");
                startRestBetweenCycles(sharedPreferences.getInt(REST_BETWEEN_CYCLE,0)*1000);
            }
            else
            {
                //end
                //reset all if needed
                status.setText(R.string.done);
                status.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.pastelGreenColor));
                resetValues();
            }


        }


    }

    private void startRestBetweenCycles(long totalDuration) {
        editor.putString(TIME_STATUS,REST_BETWEEN_CYCLE).apply();
        status.setText(R.string.coolDown);
        status.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.pastelGreenColor));
        updateNotification();
        countDownTimer = new CountDownTimer(totalDuration, 1000) {

            public void onTick(long millisUntilFinished) {
                setTime(millisUntilFinished);
                remainingTimer = millisUntilFinished;
            }

            public void onFinish() {
                //Start excercies
                startTimer();
            }
        }.start();
    }

    private void startWorkOutTime(long totalDuration) {
        editor.putString(TIME_STATUS,WORKOUT_TIME).apply();
        status.setText(R.string.exercise);
        status.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.pastelRedColor));
        updateNotification();
        countDownTimer = new CountDownTimer(totalDuration, 1000) {

            public void onTick(long millisUntilFinished) {
                setTime(millisUntilFinished);
                remainingTimer = millisUntilFinished;
            }

            public void onFinish() {
                //Rest between sets
                if (sharedPreferences.getInt(SAVED_SET_NUMBER,0) < sharedPreferences.getInt(FINAL_SET_NUMBER,0))
                {
                    editor.putInt(SAVED_SET_NUMBER, sharedPreferences.getInt(SAVED_SET_NUMBER,0) + 1).apply();
                    startRestBetweenSets(sharedPreferences.getInt(REST_TIME,0)*1000);
                }
                else if (sharedPreferences.getInt(SAVED_SET_NUMBER,0) == sharedPreferences.getInt(FINAL_SET_NUMBER,0))
                {
                    editor.putInt(SAVED_SET_NUMBER, sharedPreferences.getInt(SAVED_SET_NUMBER,0) + 1).apply();
                    startExercise();
                }
            }
        }.start();
    }

    private void startRestBetweenSets(long totalDuration) {
        editor.putString(TIME_STATUS,REST_TIME).apply();
        status.setText(R.string.rest);
        status.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.pastelGreenColor));
        updateNotification();
        countDownTimer = new CountDownTimer(totalDuration, 1000) {

            public void onTick(long millisUntilFinished) {
                setTime(millisUntilFinished);
                remainingTimer = millisUntilFinished;
            }

            public void onFinish() {
                //Rest between sets
                //editor.putInt(SAVED_SET_NUMBER, sharedPreferences.getInt(SAVED_SET_NUMBER,0) + 1).apply();
                startExercise();
                updateNotification();
            }
        }.start();


    }

    private void updateNotification() {

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);


        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, "Exercise")
                    .setContentTitle(getText(R.string.app_name))
                    .setContentText(status.getText().toString()+" Sets "+ sharedPreferences.getInt(SAVED_SET_NUMBER,0) +"/"+
                            sharedPreferences.getInt(FINAL_SET_NUMBER,0)+" "
                            +"Cycles "+ sharedPreferences.getInt(SAVED_CYCLE_NUMBER,0) +"/"
                            + sharedPreferences.getInt(FINAL_CYCLE_NUMBER,0))
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentIntent(pendingIntent)
                    .setTicker("Nothing much")
                    .build();
        }
        else
        {
            notification = new NotificationCompat.Builder(this)
                    .setContentTitle(getText(R.string.app_name))
                    .setContentText(status.getText().toString()+" Sets "+ sharedPreferences.getInt(SAVED_SET_NUMBER,0) +"/"+
                            sharedPreferences.getInt(FINAL_SET_NUMBER,0)+" "
                            +"Cycles "+ sharedPreferences.getInt(SAVED_CYCLE_NUMBER,0) +"/"
                            + sharedPreferences.getInt(FINAL_CYCLE_NUMBER,0))
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentIntent(pendingIntent)
                    .setTicker("Nothing much")
                    .build();
        }

        //startForeground(24392, notification);
        mNotificationManager.notify(
                24392,
                notification);

    }
}

