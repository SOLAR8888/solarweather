package com.solarapp.solarweather.contracts;

import com.google.android.gms.location.places.Place;
import com.solarapp.solarweather.models.City;
import com.solarapp.solarweather.models.Photo;
import com.solarapp.solarweather.models.forecast.Forecast;

import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmList;
import rx.Observable;

/**
 * Created by vereskun on 01.10.2017.
 */

public class ContractMain {

    public interface IMainView{
        void showSearchWidget();
        void showMessage(String text);
        void updateAdapter(ArrayList<City> cityArrayList);
        void showDetailsActivity(String id);
    }
    public interface IMainPresenter{
        void onBtnAddClick();
        void onResultPlaceSearch(Place place);
        void onActivityCreate();
        void onCityListChanged();
        void updateCityList();
        void onBtnItemRemoveClick(String id);
        void onItemClick(int position);
        void onImageCityClick(String id);
    }
    public interface IMainModel{
        Observable<Forecast> getForecastObservable(City city);
        Forecast getForecast(City city);
        Observable<ArrayList<City>> getCityList();
        void putCityToRealmDB(City city);
        void putPhotoToRealmDB(Photo photo);
        ArrayList<City> getCitiesFromRealmDB();
        RealmList<Photo> getPhotosByID(String id);
        City getCityFromRealmDBbyID(String id);
        void deleteCityFromRealmDB(String id);
        ArrayList<City> getUpdateCityList();
        void updateCityForecast(String id, Forecast forecast, Date date);
        void updateCityPhoto(String id, RealmList<Photo> byteArray);

    }

}
