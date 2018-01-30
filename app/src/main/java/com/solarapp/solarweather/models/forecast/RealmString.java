package com.solarapp.solarweather.models.forecast;

import io.realm.RealmObject;

/**
 * Created by vereskun on 01.10.2017.
 */

public class RealmString extends RealmObject{
    private String string;

    public RealmString() {
    }

    @Override
    public String toString() {
        return string;
    }

    public RealmString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
