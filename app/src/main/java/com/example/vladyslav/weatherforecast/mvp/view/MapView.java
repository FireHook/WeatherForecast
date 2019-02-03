package com.example.vladyslav.weatherforecast.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.example.vladyslav.weatherforecast.network.model.Root;

public interface MapView extends MvpView {
    void openForecastDetailScreen(Root root);
    void showToastText(String message);
}
