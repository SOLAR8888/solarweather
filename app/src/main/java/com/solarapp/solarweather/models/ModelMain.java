package com.solarapp.solarweather.models;

import com.solarapp.solarweather.App;
import com.solarapp.solarweather.api.WeatherAPI;
import com.solarapp.solarweather.contracts.ContractMain;
import com.solarapp.solarweather.models.forecast.Forecast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Callable;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by vereskun on 03.10.2017.
 */

public class ModelMain implements ContractMain.IMainModel {

    private Observable<Forecast> forecastObservable;
    private Forecast forecast;
    private Realm realm = Realm.getDefaultInstance();
    private ArrayList<City> cityToUpdate;
    private City city;


    @Override
    public Forecast getForecast(City city) {
        String latitude = String.valueOf(city.getLatLng().getLatitude());
        String longitude = String.valueOf(city.getLatLng().getLongitude());
        System.out.println("getFirstForecast");
        String lang = Locale.getDefault().getLanguage();
        try {
            forecast = App.getWeatherAPI()
                    .getForecast(WeatherAPI.API_KEY,latitude,longitude,lang,"si").execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("forecast loaded");
        return forecast;
    }

    @Override
    public Observable<Forecast> getForecastObservable(City city) {
        String latitude = String.valueOf(city.getLatLng().getLatitude());
        String longitude = String.valueOf(city.getLatLng().getLongitude());
        System.out.println("getFirstForecast");
        String lang = Locale.getDefault().getLanguage();
        forecastObservable = App.getWeatherAPI()
                .getForecastObservable(WeatherAPI.API_KEY,latitude,longitude,lang,"si");
        return forecastObservable;
    }

    @Override
    public void putCityToRealmDB(final City putCity) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (putCity.getPhoto() != null) {
                    System.out.println("putCityToRealmDB: " + putCity.getPhoto().size());
                }
                realm.copyToRealmOrUpdate(putCity);
            }
        });
    }

    @Override
    public void putPhotoToRealmDB(final Photo photo) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(photo);
            }
        });
    }

    @Override
    public void updateCityForecast(final String id, final Forecast forecast, final Date date) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        City city = realm.where(City.class).equalTo("id",id).findFirst();
        city.setUpdateDate(date);
        city.setForecast(forecast);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void updateCityPhoto(String id, RealmList<Photo> byteArray) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        City city = realm.where(City.class).equalTo("id",id).findFirst();
        city.setPhoto(byteArray);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public ArrayList<City> getCitiesFromRealmDB() {
        RealmResults<City> realmResults = realm.where(City.class).findAllSorted("addDate", Sort.DESCENDING);
        System.out.println("getCitiesFromRealmDB count: " + realmResults.size());
        ArrayList<City> list = new ArrayList<>();
        list.addAll(realmResults);
        for (int i = 0; i < list.size(); i++) {
            String id = list.get(i).getId();
            list.get(i).setPhoto(getPhotosByID(id));
        }
        System.out.println("Cities in DB");
        cityToUpdate = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getName().toString());
            City city = list.get(i);
            Calendar calendar = Calendar.getInstance();
            Date updateTime = city.getUpdateDate();
            Date curTime = calendar.getTime();
            if (updateTime == null){
                updateTime = curTime;
            }
            int time = 7200000;
            //int time = 20000;
            if ((curTime.getTime() - updateTime.getTime()) > time){
                //need update this city
                System.out.println(city.getName() + " is needed to update");
                cityToUpdate.add(city);
            }
        }
        return list;
    }

    @Override
    public RealmList<Photo> getPhotosByID(String id) {
        RealmList<Photo> array = new RealmList<>();
        RealmResults<Photo> realmResults = realm.where(Photo.class).equalTo("id",id).findAll();
        array.addAll(realmResults);
        return array;
    }

    @Override
    public City getCityFromRealmDBbyID(String id) {
        return realm.where(City.class).equalTo("id",id).findFirst();
    }

    @Override
    public ArrayList<City> getUpdateCityList() {
        return cityToUpdate;
    }

    @Override
    public void deleteCityFromRealmDB(final String id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(City.class).equalTo("id",id).findFirst().deleteFromRealm();
            }
        });
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Photo.class).equalTo("id",id).findAll().deleteAllFromRealm();
            }
        });
    }

    @Override
    public Observable<ArrayList<City>> getCityList() {
        return makeObservable(getCallableCities()).subscribeOn(Schedulers.computation());
    }

    private Callable<ArrayList<City>> getCallableCities(){
        return new Callable<ArrayList<City>>() {
            @Override
            public ArrayList<City> call() throws Exception {
                ArrayList<City> list = getCitiesFromRealmDB();
                System.out.println("cityList.size() = " + list.size());
                for (int i = 0; i < list.size(); i++) {
                    City city = list.get(i);
                    Calendar calendar = Calendar.getInstance();
                    Date updateTime = city.getUpdateDate();
                    Date curTime = calendar.getTime();
                    if ((curTime.getTime() - updateTime.getTime()) > 7200){
                        //need update this city
                        System.out.println("City is needed to update");
                        Forecast forecast = getForecast(city);
                        //city.setForecast(forecast);
                        //city.setUpdateDate(calendar.getTime());
                        System.out.println("City is updated");
                    }
                }
                return list;
            }
        };
    }

    private static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            subscriber.onNext(func.call());
                        } catch(Exception ex) {
                            System.out.println("error from read db");
                            System.out.println(ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                });
    }


}
