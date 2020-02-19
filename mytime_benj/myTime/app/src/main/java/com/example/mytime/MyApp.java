package com.example.mytime;

import android.app.Application;

import java.util.Timer;
import java.util.TimerTask;

public class MyApp extends Application {

    private LogoutListener listener;

    private Timer timer,timer1;


    public void startUserSession() {

        cancelTimer();

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                listener.onSessionLogout();

            }
        },60000);
    }

    private void cancelTimer() {
        if(timer!=null)timer.cancel();
    }

    public void cancel(){
        timer.cancel();
    }

    public void registerSessionListener(LogoutListener listener) {
        this.listener=listener;
    }

    public void onUserInteraction() {

        startUserSession();
    }
}
