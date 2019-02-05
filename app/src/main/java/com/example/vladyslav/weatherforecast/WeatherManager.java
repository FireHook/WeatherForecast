package com.example.vladyslav.weatherforecast;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.example.vladyslav.weatherforecast.mvp.model.ForecastItem;
import com.example.vladyslav.weatherforecast.network.model.City;
import com.example.vladyslav.weatherforecast.network.model.Forecast;
import com.example.vladyslav.weatherforecast.network.model.Root;
import com.example.vladyslav.weatherforecast.network.retrofit.WeatherApiClient;
import com.example.vladyslav.weatherforecast.network.retrofit.WeatherService;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class WeatherManager {

    private Context mApplicationContext;

    public WeatherManager(Context applicationContext) {
        mApplicationContext = applicationContext;
    }

    public Observable<LatLng> getSavedLocation() {
        return Observable.just(readLatLngFromPrefs());
    }

    public void saveLocation(LatLng coords) {
        Completable.fromRunnable(() -> saveLatLngToPrefs(coords))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override public void onSubscribe(Disposable d) { }
                    @Override public void onComplete() {
                        Timber.d("Prefs saved well");
                    }
                    @Override public void onError(Throwable e) { }
                });
    }

    public Observable<List<ForecastItem>> loadForecast() {
        return getSavedLocation().flatMap((Function<LatLng, ObservableSource<Root>>) latLng -> {
            WeatherService weatherService = WeatherApiClient.getClient().create(WeatherService.class);
            return weatherService.getForecast(latLng.latitude, latLng.longitude);
        }).flatMap((Function<Root, ObservableSource<List<ForecastItem>>>) root -> {
            List<ForecastItem> forecastItems = convertToForecastItems(filterForecasts(root.list), root.city);
            return Observable.just(forecastItems);
        });
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connManager = (ConnectivityManager) mApplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connManager != null) activeNetworkInfo = connManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private LatLng readLatLngFromPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mApplicationContext);
        String latitude = prefs.getString(Utils.LATITUDE_KEY, "50.4501");
        String longitude = prefs.getString(Utils.LONGITUDE_KEY, "30.5234");

        return new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
    }

    private void saveLatLngToPrefs(LatLng coords) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mApplicationContext);
        prefs.edit()
                .putString(Utils.LATITUDE_KEY, String.valueOf(coords.latitude))
                .putString(Utils.LONGITUDE_KEY, String.valueOf(coords.longitude))
                .apply();
    }

    private List<Forecast> filterForecasts(List<Forecast> forecastList) throws ParseException {

        List<Forecast> filteredForecasts = new ArrayList<>();
        SimpleDateFormat hoursFormat = new SimpleDateFormat("HH:mm");

        for (Forecast forecast : forecastList) {

            String trimmedDate = forecast.dtTxt.substring(forecast.dtTxt.lastIndexOf(" ") + 1);

            if (filteredForecasts.size() == 9) {
                if (trimmedDate.equals("21:00:00")) {
                    filteredForecasts.add(forecast);
                }
            }

            if (hoursFormat.parse(hoursFormat.format(new Date())).after(hoursFormat.parse("12:00"))) {
                if (!filteredForecasts.isEmpty()) {
                    if (trimmedDate.equals("12:00:00") || trimmedDate.equals("00:00:00")) {
                        filteredForecasts.add(forecast);
                    }
                } else {
                    filteredForecasts.add(forecast);
                }
            } else {
                if (trimmedDate.equals("00:00:00") || trimmedDate.equals("12:00:00")) {
                    filteredForecasts.add(forecast);
                }
            }
        }
        return filteredForecasts;
    }

    private List<ForecastItem> convertToForecastItems(List<Forecast> sortedForecast, City city) {
        List<ForecastItem> convertedForecasts = new ArrayList<>();

        for (int i = 0; i < sortedForecast.size() - 1; i += 2) {

            ForecastItem forecastItem = new ForecastItem();
            SimpleDateFormat monthAndDay = new SimpleDateFormat("MMM d", Locale.US);
            if (convertedForecasts.isEmpty()) {
                forecastItem.setDate(monthAndDay.format(Calendar.getInstance().getTime()));
            } else {
                String trimmedDate = sortedForecast.get(i).dtTxt.substring(0, sortedForecast.get(i).dtTxt.indexOf(" "));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date date = null;
                try {
                    date = dateFormat.parse(trimmedDate);
                } catch (ParseException e) { e.printStackTrace(); }

                String res = new SimpleDateFormat("EEEE", Locale.US).format(date);
                forecastItem.setDate(res);
            }

            forecastItem.setDayTemperature(String.valueOf(sortedForecast.get(i).main.temp.intValue() - 273));
            forecastItem.setNightTemperature(String.valueOf(sortedForecast.get(i+1).main.temp.intValue() - 273));
            String description = sortedForecast.get(i).weather.get(0).description;
            forecastItem.setDescription(description.substring(0, 1).toUpperCase().concat(description.substring(1)));
            forecastItem.setIcon(sortedForecast.get(i).weather.get(0).icon);
            if (city.country != null && city.name != null)
                forecastItem.setCity(city.name.concat(", ").concat(city.country));
            convertedForecasts.add(forecastItem);
        }
        return convertedForecasts;
    }
}
