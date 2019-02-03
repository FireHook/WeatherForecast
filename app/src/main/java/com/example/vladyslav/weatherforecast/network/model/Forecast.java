package com.example.vladyslav.weatherforecast.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Forecast {

    @SerializedName("sys")     @Expose public Sys sys;
    @SerializedName("weather") @Expose public List<Weather> weather = new ArrayList<Weather>();
    @SerializedName("main")    @Expose public Main main;
    @SerializedName("wind")    @Expose public Wind wind;
    @SerializedName("rain")    @Expose public Rain rain;
    @SerializedName("clouds")  @Expose public Clouds clouds;
    @SerializedName("dt")      @Expose public double dt;
    @SerializedName("dt_txt")  @Expose public String dtTxt;

}
