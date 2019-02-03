package com.example.vladyslav.weatherforecast.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladyslav.weatherforecast.mvp.view.MapView;
import com.example.vladyslav.weatherforecast.network.model.Root;
import com.example.vladyslav.weatherforecast.network.retrofit.WeatherApiClient;
import com.example.vladyslav.weatherforecast.network.retrofit.WeatherService;
import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {

    private LatLng mCoordinates;

    public MapPresenter() { }

    public void setCoordinates(LatLng coordinates) {
        this.mCoordinates = new LatLng(coordinates.latitude, coordinates.longitude);
    }

    public void loadForecast() {

        if (mCoordinates == null) return;

        WeatherService mWeatherService = WeatherApiClient.getClient().create(WeatherService.class);
        mWeatherService.getForecast(mCoordinates.latitude, mCoordinates.longitude)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Root>() {
                    @Override public void onSubscribe(Disposable d) { /* No need */ }
                    @Override public void onNext(Root root) { getViewState().openForecastDetailScreen(root); }
                    @Override public void onError(Throwable e) { getViewState().showToastText("Turn ON Network Connection"); }
                    @Override public void onComplete() { /* No need */ }
                });
    }

}
