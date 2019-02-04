package com.example.vladyslav.weatherforecast.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.example.vladyslav.weatherforecast.mvp.model.ForecastItem;

import java.util.List;

public interface ForecastDetailView extends MvpView {
    void initRecyclerAdapter(List<ForecastItem> list);
    void startRefreshing();
    void stopRefreshing();
    void showError(int message);
}
