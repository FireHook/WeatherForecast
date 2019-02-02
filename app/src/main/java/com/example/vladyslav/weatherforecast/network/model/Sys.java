package com.example.vladyslav.weatherforecast.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("country") @Expose public String country;
    @SerializedName("sunrise") @Expose public Integer sunrise;
    @SerializedName("sunset")  @Expose public Integer sunset;

}
