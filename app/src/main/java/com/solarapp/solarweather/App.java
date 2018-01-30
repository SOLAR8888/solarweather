package com.solarapp.solarweather;

import android.app.Application;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.solarapp.solarweather.api.WeatherAPI;

import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vereskun on 30.09.2017.
 */

public class App extends Application{

    private static WeatherAPI weatherAPI;
    private Retrofit retrofit;
    private static GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(WeatherAPI.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                //.addConverterFactory(JacksonConverterFactory.create())
                .build();
        weatherAPI = retrofit.create(WeatherAPI.class);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
    }
    public static WeatherAPI getWeatherAPI() {
        return weatherAPI;
    }
    public static GoogleApiClient getGoogleApiClient(){
        return mGoogleApiClient;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mGoogleApiClient.disconnect();
    }
}
