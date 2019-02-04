package com.example.vladyslav.weatherforecast;

import android.app.Application;

import timber.log.Timber;

public class WeatherApp extends Application {

    @Override public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) Timber.plant(new MyTimberTree());
    }
}
