package com.example.vladyslav.weatherforecast.network.retrofit;

import com.example.vladyslav.weatherforecast.network.model.Root;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/forecast?APPID=96cebf9e24262c1ebbe7a0e5b4e0f32e")
    Observable<Root> getForecast(@Query("lat") Double lat, @Query("lon") Double lon);
}
