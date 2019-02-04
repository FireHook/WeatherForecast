package com.example.vladyslav.weatherforecast.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladyslav.weatherforecast.R;
import com.example.vladyslav.weatherforecast.WeatherApp;
import com.example.vladyslav.weatherforecast.WeatherManager;
import com.example.vladyslav.weatherforecast.mvp.view.MapView;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {

    @Inject WeatherManager mWeatherManager;

    public MapPresenter() {
        WeatherApp.sAppComponent.inject(this);
    }

    public void showMarker() {
        mWeatherManager.getSavedLocation()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LatLng>() {
                    @Override public void onSubscribe(Disposable d) {/* No need */}
                    @Override public void onNext(LatLng latLng) { getViewState().drawMarker(latLng); }
                    @Override public void onError(Throwable e) { getViewState().showError(R.string.cache_error); }
                    @Override public void onComplete() {/* No need */}
                });
    }

    public void saveCoordinates(LatLng latLng) { mWeatherManager.saveLocation(latLng); }

    public void openForecast() { getViewState().openForecastDetailScreen(); }
}
