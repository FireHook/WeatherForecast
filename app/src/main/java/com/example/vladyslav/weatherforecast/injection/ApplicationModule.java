package com.example.vladyslav.weatherforecast.injection;

import android.content.Context;

import com.example.vladyslav.weatherforecast.WeatherManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Context mContext;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return mContext;
    }

    @Provides @Singleton
    WeatherManager provideWeatherManager() {
        return new WeatherManager(mContext);
    }

}
