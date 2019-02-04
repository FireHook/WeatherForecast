package com.example.vladyslav.weatherforecast.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladyslav.weatherforecast.WeatherApp;
import com.example.vladyslav.weatherforecast.WeatherManager;
import com.example.vladyslav.weatherforecast.mvp.model.ForecastItem;
import com.example.vladyslav.weatherforecast.mvp.view.ForecastDetailView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ForecastDetailPresenter extends MvpPresenter<ForecastDetailView> {

    @Inject WeatherManager mWeatherManager;

    public  ForecastDetailPresenter() {
        WeatherApp.sAppComponent.inject(this);
    }



    public void loadForecast() {
        mWeatherManager.loadForecast().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ForecastItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ForecastItem> forecastItems) {
                        getViewState().initRecyclerAdapter(forecastItems);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
