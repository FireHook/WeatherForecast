package com.example.vladyslav.weatherforecast.injection;

import com.example.vladyslav.weatherforecast.mvp.presenter.ForecastDetailPresenter;
import com.example.vladyslav.weatherforecast.mvp.presenter.MapPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MapPresenter mapPresenter);
    void inject(ForecastDetailPresenter forecastDetailPresenter);
}
