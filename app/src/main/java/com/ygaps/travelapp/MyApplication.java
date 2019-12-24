package com.ygaps.travelapp;

import android.app.Application;

public class MyApplication extends Application {
    public String userToken;
    public String userId;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
