package com.example.vladyslav.weatherforecast;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladyslav.weatherforecast.network.model.Forecast;
import com.example.vladyslav.weatherforecast.network.model.ForecastToAdapter;
import com.example.vladyslav.weatherforecast.network.model.Root;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ForecastDetailFragment extends Fragment {

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private Root mRoot;
    private ForecastDetailAdapter mAdapter;

    public static Fragment newInstance(Root root) {
        ForecastDetailFragment instance = new ForecastDetailFragment();
        instance.mRoot = root;
        return instance;
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<ForecastToAdapter> forecastToAdapterList = migrateForecastToAdapter(sortForecasts(mRoot.list));

        for (ForecastToAdapter f : forecastToAdapterList) {
            Timber.d("---> %s, %s, %s, %s, %s", f.getDate(), f.getDayTemperature(), f.getNightTemperature(), f.getDescription(), f.getIcon());
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ForecastDetailAdapter(forecastToAdapterList);
        mRecyclerView.setAdapter(mAdapter);

    }

    List<Forecast> sortForecasts(List<Forecast> forecastList) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        List<Forecast> list = new ArrayList<>();

        for (Forecast forecast : forecastList) {

            try {
                if (new Date().after(new SimpleDateFormat("HH:mm").parse("12:00"))) {
                    if (list.isEmpty()) {
                        list.add(forecast);
                    } else {
                        String trimedDate = forecast.dtTxt.substring(forecast.dtTxt.lastIndexOf(" ") + 1);
                        if (trimedDate.equals("00:00:00") ||
                                trimedDate.equals("12:00:00")) {
                            list.add(forecast);
                        }
                    }
                } else {
                    String trimedDate = forecast.dtTxt.substring(forecast.dtTxt.lastIndexOf(" ") + 1);
                    if (trimedDate.equals("00:00:00") ||
                            trimedDate.equals("12:00:00")) {
                        list.add(forecast);
                    }
                }
            } catch (ParseException e) { e.printStackTrace(); }
        }
        return list;
    }

    List<ForecastToAdapter> migrateForecastToAdapter(List<Forecast> sortedForecast) {
        List<ForecastToAdapter> list = new ArrayList<>();

        for (int i = 0; i < sortedForecast.size() - 1; i+=2) {
            ForecastToAdapter forecastToAdapter = new ForecastToAdapter();
            SimpleDateFormat monthAndDay = new SimpleDateFormat("MMM d");
            SimpleDateFormat dayOfWeek = new SimpleDateFormat("EE");
            if (list.isEmpty()) {
                forecastToAdapter.setDate(monthAndDay.format(Calendar.getInstance().getTime()));
            } else {
                String trimedDate = sortedForecast.get(i).dtTxt.substring(0, sortedForecast.get(i).dtTxt.indexOf(" "));
                SimpleDateFormat forma=new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date d = null;
                try {
                    d = forma.parse(trimedDate);
                } catch (ParseException e) { e.printStackTrace(); }

                String res = new SimpleDateFormat("EEEE", Locale.US).format(d);
                forecastToAdapter.setDate(res);
            }
            forecastToAdapter.setDayTemperature(String.valueOf(sortedForecast.get(i).main.temp.intValue() - 273));
            forecastToAdapter.setNightTemperature(String.valueOf(sortedForecast.get(i+1).main.temp.intValue() - 273));
            forecastToAdapter.setDescription(sortedForecast.get(i).weather.get(0).description);
            forecastToAdapter.setIcon(sortedForecast.get(i).weather.get(0).icon);
            list.add(forecastToAdapter);
        }
        return list;
    }
}
