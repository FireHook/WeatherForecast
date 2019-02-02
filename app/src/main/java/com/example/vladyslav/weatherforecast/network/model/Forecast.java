package com.example.vladyslav.weatherforecast.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Forecast {

    @SerializedName("coord")   @Expose public Coord coord;
    @SerializedName("sys")     @Expose public Sys sys;
    @SerializedName("weather") @Expose public List<Weather> weather = new ArrayList<Weather>();
    @SerializedName("main")    @Expose public Main main;
    @SerializedName("wind")    @Expose public Wind wind;
    @SerializedName("rain")    @Expose public Rain rain;
    @SerializedName("clouds")  @Expose public Clouds clouds;
    @SerializedName("dt")      @Expose public Integer dt;
    @SerializedName("id")      @Expose public Integer id;
    @SerializedName("name")    @Expose public String name;
    @SerializedName("cod")     @Expose public Integer cod;

}
