package com.solarapp.solarweather.models;

import com.solarapp.solarweather.R;

/**
 * Created by vereskun on 04.01.2018.
 */

public class Icons {

    public static final String CLEAR_DAY = "clear-day";
    public static final String CLEAR_NIGHT = "clear-night";
    public static final String RAIN = "rain";
    public static final String SNOW = "snow";
    public static final String SLEET = "sleet";
    public static final String WIND = "wind";
    public static final String FOG = "fog";
    public static final String CLOUDY = "cloudy";
    public static final String PARTLY_CLOUDY_DAY = "partly-cloudy-day";
    public static final String PARTLY_CLOUDY_NIGHT = "partly-cloudy-night";

    public static int getDrawableID(String icon){
        int drawable = R.drawable.ic_wi_day_sunny;
        switch (icon){
            case Icons.CLEAR_DAY: drawable = R.drawable.ic_wi_day_sunny;
                break;
            case Icons.CLEAR_NIGHT: drawable = R.drawable.ic_wi_night_clear;
                break;
            case Icons.RAIN: drawable = R.drawable.ic_wi_rain;
                break;
            case Icons.SNOW: drawable = R.drawable.ic_wi_snow;
                break;
            case Icons.SLEET: drawable = R.drawable.ic_wi_sleet;
                break;
            case Icons.WIND: drawable = R.drawable.ic_wi_windy;
                break;
            case Icons.FOG: drawable = R.drawable.ic_wi_fog;
                break;
            case  Icons.CLOUDY: drawable = R.drawable.ic_wi_cloudy;
                break;
            case  Icons.PARTLY_CLOUDY_DAY: drawable = R.drawable.ic_wi_day_cloudy;
                break;
            case  Icons.PARTLY_CLOUDY_NIGHT: drawable = R.drawable.ic_wi_night_alt_cloudy;
                break;
        }
        return drawable;
    }

}
