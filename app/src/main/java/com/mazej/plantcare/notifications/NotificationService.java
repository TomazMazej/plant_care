package com.mazej.plantcare.notifications;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.mazej.plantcare.R;

import java.util.Timer;
import java.util.TimerTask;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationService extends Service {

    private Timer timer;
    private TimerTask timerTask;
    private String TAG = "Timers";
    public static int daysUntilWater = 0;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        startTimer();

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        stoptimertask();
        super.onDestroy();
    }

    // We are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    public void startTimer() {
        // Set a new Timer
        timer = new Timer();

        // Initialize the TimerTask's job
        initializeTimerTask();

        // Schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, daysUntilWater * 1000 * 86400); //
    }

    public void stoptimertask() {
        // Stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {

                // Use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        createNotification();
                    }
                });
            }
        };
    }

    public void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "id")
                .setSmallIcon(R.drawable.ic_local_florist_black_24dp)
                .setContentTitle("Water your plants!")
                .setContentText("Some of your plants needs to be watered!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(100, builder.build());
    }
}
