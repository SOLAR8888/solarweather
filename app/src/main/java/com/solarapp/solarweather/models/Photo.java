package com.solarapp.solarweather.models;

import io.realm.RealmObject;

/**
 * Created by vereskun on 13.10.2017.
 */

public class Photo extends RealmObject {

    private byte[] array;
    private String id;

    public Photo() {
    }

    public Photo(String id,byte[] array) {
        this.array = array;
        this.id = id;
    }

    public byte[] getArray() {
        return array;
    }

    public void setArray(byte[] array) {
        this.array = array;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
