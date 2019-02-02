package com.example.vladyslav.weatherforecast.network.retrofit;

import com.example.vladyslav.weatherforecast.network.model.Forecast;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/weather?APPID=96cebf9e24262c1ebbe7a0e5b4e0f32e")
    Observable<Forecast> getForecast(@Query("lat") Double lat, @Query("lon") Double lon);
}
