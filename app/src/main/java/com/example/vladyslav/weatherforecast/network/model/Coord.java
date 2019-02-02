package com.example.vladyslav.weatherforecast.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coord {

    @SerializedName("lon") @Expose public Integer lon;
    @SerializedName("lat") @Expose public Integer lat;

}