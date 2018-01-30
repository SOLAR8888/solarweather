package com.solarapp.solarweather.models;

import com.solarapp.solarweather.models.forecast.Forecast;
import com.solarapp.solarweather.models.forecast.RealmLatLng;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vereskun on 30.09.2017.
 */

public class City extends RealmObject{


    @PrimaryKey
    private String id;
    private String name;
    private Forecast forecast;
    private Date updateDate;
    private Date addDate;
    private RealmLatLng latLng;
    @Ignore
    private RealmList<Photo> byteArrayPhoto;
    @Ignore
    private boolean photoIsSetted;

    public City() {
    }
    public City(City city){
        this.id = city.getId();
        this.name = city.getName();
        this.forecast = city.getForecast();
        this.updateDate = city.getUpdateDate();
        this.latLng = city.getLatLng();
        this.byteArrayPhoto = city.getPhoto();
        this.addDate = city.getAddDate();
    }

    public RealmList<Photo> getPhoto() {
        return byteArrayPhoto;
    }

    public void setPhoto(RealmList<Photo> photo) {
        this.byteArrayPhoto = photo;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public RealmLatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(RealmLatLng latLng) {
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getPhotoIsSetted() {
        return photoIsSetted;
    }

    public void setPhotoIsSetted(boolean photoIsSetted) {
        this.photoIsSetted = photoIsSetted;
    }

}
