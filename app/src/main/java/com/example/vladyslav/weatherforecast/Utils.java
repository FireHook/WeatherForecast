package com.example.vladyslav.weatherforecast;

import android.content.Context;
import android.support.annotation.DrawableRes;

public class Utils {

    @DrawableRes
    public static int getWeatherIcon(Context context, String weatherConditionCode) {
    
        if (weatherConditionCode.equalsIgnoreCase("01d") || weatherConditionCode.equalsIgnoreCase("01n")) return R.drawable.clear_sky;
        if (weatherConditionCode.equalsIgnoreCase("02d") || weatherConditionCode.equalsIgnoreCase("02n")) return R.drawable.few_clouds;
        if (weatherConditionCode.equalsIgnoreCase("03d") || weatherConditionCode.equalsIgnoreCase("03n")) return R.drawable.scattered_clouds;
        if (weatherConditionCode.equalsIgnoreCase("04d") || weatherConditionCode.equalsIgnoreCase("04n")) return R.drawable.broken_clouds;
        if (weatherConditionCode.equalsIgnoreCase("09d") || weatherConditionCode.equalsIgnoreCase("09n")) return R.drawable.shower_rain;
        if (weatherConditionCode.equalsIgnoreCase("10d") || weatherConditionCode.equalsIgnoreCase("10n")) return R.drawable.rain;
        if (weatherConditionCode.equalsIgnoreCase("11d") || weatherConditionCode.equalsIgnoreCase("11n")) return R.drawable.thunderstorm;
        if (weatherConditionCode.equalsIgnoreCase("13d") || weatherConditionCode.equalsIgnoreCase("13n")) return R.drawable.snow;
        if (weatherConditionCode.equalsIgnoreCase("50d") || weatherConditionCode.equalsIgnoreCase("50n")) return R.drawable.mist;
        
        return R.drawable.icon_na;
    }
}
