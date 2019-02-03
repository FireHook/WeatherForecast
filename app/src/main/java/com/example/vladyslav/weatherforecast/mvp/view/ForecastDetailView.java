package com.example.vladyslav.weatherforecast.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.example.vladyslav.weatherforecast.mvp.model.ForecastToAdapter;

import java.util.List;

public interface ForecastDetailView extends MvpView {
    void initRecyclerAdapter(List<ForecastToAdapter> list);
}
