package com.example.vladyslav.weatherforecast.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladyslav.weatherforecast.R;
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
import timber.log.Timber;

@InjectViewState
public class ForecastDetailPresenter extends MvpPresenter<ForecastDetailView> {

    @Inject WeatherManager mWeatherManager;

    public  ForecastDetailPresenter() {
        WeatherApp.sAppComponent.inject(this);
    }

    public void loadForecast() {

        getViewState().startRefreshing();

        if (!mWeatherManager.isNetworkAvailable()) {
            getViewState().showError(R.string.network_error);
            return;
        }

        mWeatherManager.loadForecast().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ForecastItem>>() {
                    @Override public void onSubscribe(Disposable d) {/* No need */}
                    @Override public void onNext(List<ForecastItem> forecastItems) {
                        getViewState().stopRefreshing();
                        getViewState().initRecyclerAdapter(forecastItems);
                    }
                    @Override public void onError(Throwable e) {
                        Timber.d("Err: %s", e.getMessage());
                        getViewState().stopRefreshing();
                        getViewState().showError(R.string.network_error);
                    }
                    @Override public void onComplete() {/* No need */}
                });
    }
}
