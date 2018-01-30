package com.solarapp.solarweather.models.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by vereskun on 01.10.2017.
 */

public class Datum extends RealmObject {

    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("precipIntensity")
    @Expose
    private Double precipIntensity;
    @SerializedName("precipProbability")
    @Expose
    private Double precipProbability;

    public Integer getTime() {
        return time!=null?time:-1000;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Double getPrecipIntensity() {
        return precipIntensity!=null?precipIntensity:-1000;
    }

    public void setPrecipIntensity(Double precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public Double getPrecipProbability() {
        return precipProbability!=null?precipProbability:-1000;
    }

    public void setPrecipProbability(Double precipProbability) {
        this.precipProbability = precipProbability;
    }

}
