package com.example.vladyslav.weatherforecast;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladyslav.weatherforecast.network.model.Forecast;

public class ForecastDetailFragment extends Fragment {

    private Forecast mForecast;

    public static Fragment newInstance(Forecast forecast) {
        ForecastDetailFragment instance = new ForecastDetailFragment();
        instance.mForecast = forecast;
        return instance;
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
