package com.solarapp.solarweather.models.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by vereskun on 01.10.2017.
 */

public class Hourly extends RealmObject {

    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("data")
    @Expose
    private RealmList<Datum_> data = null;



    public String getSummary() {
        return summary!=null?summary:"";
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon!=null?icon:"";
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public RealmList<Datum_> getData() {
        return data;
    }

    public void setData(RealmList<Datum_> data) {
        this.data = data;
    }

}