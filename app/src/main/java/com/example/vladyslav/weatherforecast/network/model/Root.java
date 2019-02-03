package com.example.vladyslav.weatherforecast.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Root {

    @SerializedName("cod")     @Expose public String cod;
    @SerializedName("message") @Expose public Double message;
    @SerializedName("cnt")     @Expose public Integer cnt;
    @SerializedName("list")    @Expose public List<Forecast> list = new ArrayList<Forecast>();
    @SerializedName("city")    @Expose public City city;

}
