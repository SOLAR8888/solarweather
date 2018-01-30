package com.solarapp.solarweather.presenters;

import android.graphics.Bitmap;

import com.google.android.gms.location.places.Place;
import com.solarapp.solarweather.contracts.ContractMain;
import com.solarapp.solarweather.models.City;
import com.solarapp.solarweather.models.ModelMain;
import com.solarapp.solarweather.models.Photo;
import com.solarapp.solarweather.models.PhotoTask;
import com.solarapp.solarweather.models.forecast.Forecast;
import com.solarapp.solarweather.models.forecast.RealmLatLng;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmList;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vereskun on 01.10.2017.
 */

public class PresenterMain implements ContractMain.IMainPresenter {

    private ContractMain.IMainView view;
    private Place place;
    private ModelMain mainModel;
    private ArrayList<City> cityArrayList;

    public PresenterMain(ContractMain.IMainView view, ModelMain mainModel) {
        this.view = view;
        this.mainModel = mainModel;
    }

    @Override
    public void onActivityCreate() {
        cityArrayList = mainModel.getCitiesFromRealmDB();
        view.updateAdapter(cityArrayList);
        updateCityList();
    }

    @Override
    public void onBtnAddClick() {
        view.showSearchWidget();
    }

    @Override
    public void onBtnItemRemoveClick(String id) {
        mainModel.deleteCityFromRealmDB(id);
        onCityListChanged();
    }

    @Override
    public void onItemClick(int position) {
        view.showMessage("click to: "+position);

    }
    @Override
    public void onImageCityClick(String id) {
        //view.showMessage("click to image: "+id);
        view.showDetailsActivity(id);
    }

    @Override
    public void onCityListChanged() {
        cityArrayList = mainModel.getCitiesFromRealmDB();
        view.updateAdapter(cityArrayList);
    }

    @Override
    public void onResultPlaceSearch(Place place) {
        this.place = place;
        view.showMessage(place.getName().toString()+" added to favorits");
        //create new city
        City city = new City();
        System.out.println(place.getName()+": "+place.getLatLng().toString());
        city.setLatLng(new RealmLatLng(place.getLatLng()));
        city.setName(place.getName().toString());
        city.setId(place.getId());
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        city.setAddDate(date);
        mainModel.putCityToRealmDB(city);
        cityUpdate(new City(city));

    }
    private byte[] getByteFromBitmap(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void cityUpdate(final City city){
        Observable<Forecast> forecastObservable = mainModel.getForecastObservable(city);
        forecastObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Forecast>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("cityUpdate: onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("cityUpdate: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Forecast forecast) {
                        System.out.println("cityUpdate: onNext");
                        City updateCity = new City(city);
                        updateCity.setForecast(forecast);
                        Calendar calendar = Calendar.getInstance();
                        Date date = calendar.getTime();
                        updateCity.setUpdateDate(date);

                        //System.out.println(city.getPhoto().get(0).getArray());
                        if (updateCity.getPhoto() == null) {

                            System.out.println("city.getPhoto() == null");
                            placePhotosTask(updateCity);

                        }
                        else {
                            //put city to realmDB
                            RealmList<Photo> photo = city.getPhoto();
                            updateCity.setPhoto(photo);
                            System.out.println(updateCity.getPhoto().size());
                            if (updateCity.getPhoto().size() > 0) {
                                //mainModel.putCityToRealmDB(updateCity);

                                //onCityListChanged();
                            }
                            else {
                                placePhotosTask(updateCity);
                            }
                        }

                        mainModel.putCityToRealmDB(updateCity);
                        System.out.println(updateCity.getName() + " updated");
                        onCityListChanged();
                    }
                });

    }

    @Override
    public void updateCityList() {
        if (mainModel.getUpdateCityList().size() > 0){
            for (int i = 0; i < mainModel.getUpdateCityList().size(); i++) {
                City city = mainModel.getUpdateCityList().get(i);
                cityUpdate(city);
            }
        }
    }

    private void placePhotosTask(final City city) {

        // Create a new AsyncTask that displays the bitmap and attribution once loaded.
        new PhotoTask(500, 300) {
            @Override
            protected void onPreExecute() {
                System.out.println("placePhotosTask.onPreExecute");
            }

            @Override
            protected void onPostExecute(AttributedPhoto attributedPhoto) {
                System.out.println("placePhotosTask.onPostExecute");
                if (attributedPhoto != null) {
                    // Photo has been loaded, display it.
                    //mImageView.setImageBitmap(attributedPhoto.bitmap);
                    //mainModel.updateCityPhoto(city.getId(),getByteFromBitmap(attributedPhoto.bitmap));
                    RealmList<Photo> array = new RealmList<>();
                    int n = attributedPhoto.bitmap.length;
                    //n = 2;
                    for (int i = 0; i < n; i++) {
                        Photo photo = new Photo(city.getId(),getByteFromBitmap(attributedPhoto.bitmap[i]));
                        array.add(photo);
                        mainModel.putPhotoToRealmDB(photo);
                    }
                    //city.setPhoto(array);
                    //mainModel.putCityToRealmDB(city);
                    System.out.println(city.getName() + " updated");
                    onCityListChanged();
                }
            }
        }.execute(city.getId());
    }

}
