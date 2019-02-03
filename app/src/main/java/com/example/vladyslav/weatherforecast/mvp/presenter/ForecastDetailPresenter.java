package com.example.vladyslav.weatherforecast.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladyslav.weatherforecast.mvp.model.ForecastToAdapter;
import com.example.vladyslav.weatherforecast.mvp.view.ForecastDetailView;
import com.example.vladyslav.weatherforecast.network.model.Forecast;
import com.example.vladyslav.weatherforecast.network.model.Root;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@InjectViewState
public class ForecastDetailPresenter extends MvpPresenter<ForecastDetailView> {

    private Root mRoot;

    public  ForecastDetailPresenter() { }

    public void setRoot(Root root) {
        this.mRoot = root;
        getViewState().initRecyclerAdapter(migrateForecastToAdapter(sortForecasts(mRoot.list)));
    }

    List<Forecast> sortForecasts(List<Forecast> forecastList) {

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
