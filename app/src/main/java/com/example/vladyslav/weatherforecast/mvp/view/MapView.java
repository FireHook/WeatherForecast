package com.example.vladyslav.weatherforecast.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.vladyslav.weatherforecast.network.model.Root;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface MapView extends MvpView {
    void openForecastDetailScreen(Root root);
    void loadMap();
    void showToastText(int message);
}
