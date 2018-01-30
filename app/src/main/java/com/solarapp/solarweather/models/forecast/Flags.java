package com.solarapp.solarweather.models.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by vereskun on 01.10.2017.
 */

public class Flags extends RealmObject {

    @SerializedName("sources")
    @Expose
    private RealmList<RealmString> sources = null;
    @SerializedName("isd-stations")
    @Expose
    private RealmList<RealmString> isdStations = null;
    @SerializedName("units")
    @Expose
    private String units;

    public RealmList<RealmString> getSources() {
        return sources;
    }

    public void setSources(RealmList<RealmString> sources) {
        this.sources = sources;
    }

    public RealmList<RealmString> getIsdStations() {
        return isdStations;
    }

    public void setIsdStations(RealmList<RealmString> isdStations) {
        this.isdStations = isdStations;
    }

    public String getUnits() {
        return units!=null?units:"";
    }

    public void setUnits(String units) {
        this.units = units;
    }

}
