package com.example.vladyslav.weatherforecast.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("id")      @Expose public Integer id;
    @SerializedName("name")    @Expose public String name;
    @SerializedName("coord")   @Expose public Coord coord;
    @SerializedName("country") @Expose public String country;

}
