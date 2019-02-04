package com.example.vladyslav.weatherforecast;

import android.app.Application;

import com.example.vladyslav.weatherforecast.injection.ApplicationComponent;
import com.example.vladyslav.weatherforecast.injection.ApplicationModule;
import com.example.vladyslav.weatherforecast.injection.DaggerApplicationComponent;

import timber.log.Timber;

public class WeatherApp extends Application {

    public static ApplicationComponent sAppComponent;

    @Override public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) Timber.plant(new MyTimberTree());

        sAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(getApplicationContext())).build();
    }
}
