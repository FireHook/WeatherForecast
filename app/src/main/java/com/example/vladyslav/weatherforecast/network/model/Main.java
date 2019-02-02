package com.example.vladyslav.weatherforecast.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")     @Expose public Double temp;
    @SerializedName("humidity") @Expose public Integer humidity;
    @SerializedName("pressure") @Expose public Integer pressure;
    @SerializedName("temp_min") @Expose public Double tempMin;
    @SerializedName("temp_max") @Expose public Double tempMax;

}
