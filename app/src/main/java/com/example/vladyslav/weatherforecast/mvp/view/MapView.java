package com.example.vladyslav.weatherforecast.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface MapView extends MvpView {
    void openForecastDetailScreen();
    void drawMarker(LatLng coords);
    void showToastText(int message);
}
