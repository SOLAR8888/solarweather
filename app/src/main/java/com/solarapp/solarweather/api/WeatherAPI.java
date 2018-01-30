package com.solarapp.solarweather.api;

import com.solarapp.solarweather.models.forecast.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by vereskun on 30.09.2017.
 */

public interface WeatherAPI {

    //https://api.darksky.net/forecast/[key]/[latitude],[longitude]

    String BASE_URL = "https://api.darksky.net/forecast/";
    String API_KEY = "2eeb6f1df5bea97eade32d1213b497e4";

    @GET("{key}/{latitude},{longitude}")
    Observable<Forecast> getForecastObservable( @Path("key") String key, @Path("latitude") String latitude,
                                               @Path("longitude") String longitude,
                                                @Query("lang") String lang,@Query("units") String units);

    @GET("{key}/{latitude},{longitude}")
    Call<Forecast> getForecast(@Path("key") String key, @Path("latitude") String latitude,
                              @Path("longitude") String longitude,
                               @Query("lang") String lang,@Query("units") String units);

}
