package com.sagycorp.fitbeatsP.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.sagycorp.fitbeatsP.R;
import com.sagycorp.fitbeatsP.TimerActivity;

import static com.sagycorp.fitbeatsP.TimerActivity.FINAL_CYCLE_NUMBER;
import static com.sagycorp.fitbeatsP.TimerActivity.FINAL_SET_NUMBER;
import static com.sagycorp.fitbeatsP.TimerActivity.MyPREFERENCES;
import static com.sagycorp.fitbeatsP.TimerActivity.SAVED_CYCLE_NUMBER;
import static com.sagycorp.fitbeatsP.TimerActivity.SAVED_SET_NUMBER;

public class ForegroundServices extends Service {

    private SharedPreferences sharedPreferences;

    public ForegroundServices() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //startForeground(24392,//notification);

        Intent notificationIntent = new Intent(this, TimerActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, "Exercise")
                    .setContentTitle(getText(R.string.app_name))
                    .setContentText("Sets"+ sharedPreferences.getInt(SAVED_SET_NUMBER,0) +"/"+
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
                    .setContentText("Sets"+ sharedPreferences.getInt(SAVED_SET_NUMBER,0) +"/"+
                            sharedPreferences.getInt(FINAL_SET_NUMBER,0)+" "
                            +"Cycles "+ sharedPreferences.getInt(SAVED_CYCLE_NUMBER,0) +"/"
                            + sharedPreferences.getInt(FINAL_CYCLE_NUMBER,0))
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentIntent(pendingIntent)
                    .setTicker("Nothing much")
                    .build();
        }

        startForeground(24393, notification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
