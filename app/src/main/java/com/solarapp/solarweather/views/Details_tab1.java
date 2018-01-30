package com.solarapp.solarweather.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solarapp.solarweather.R;
import com.solarapp.solarweather.models.Icons;
import com.solarapp.solarweather.models.forecast.Currently;
import com.solarapp.solarweather.presenters.PresenterDetails;

/**
 * Created by vereskun on 02.01.2018.
 */

public class Details_tab1 extends Fragment {

    private TextView summary;
    private ImageView icon;
    //private TextView nearestStormDistance;
    //private TextView nearestStormBearing;
    private TextView precipIntensity;
    private TextView precipProbability;
    private TextView precipType;
    private TextView temperature;
    private TextView apparentTemperature;
    private TextView dewPoint;
    private TextView humidity;
    private TextView pressure;
    private TextView windSpeed;
    private TextView windBearing;
    private TextView cloudCover;
    private TextView visibility;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.details_tab1_frag,container,false);
        summary = (TextView) view.findViewById(R.id.curSummary);
        icon = (ImageView) view.findViewById(R.id.curIcon);
        precipIntensity = (TextView) view.findViewById(R.id.curprecipIntensity);
        precipProbability = (TextView) view.findViewById(R.id.curprecipProbability);
        precipType = (TextView) view.findViewById(R.id.curprecipType);
        temperature = (TextView) view.findViewById(R.id.curtemperature);
        apparentTemperature = (TextView) view.findViewById(R.id.curapparentTemperature);
        dewPoint = (TextView) view.findViewById(R.id.curdewPoint);
        humidity = (TextView) view.findViewById(R.id.curhumidity);
        pressure = (TextView) view.findViewById(R.id.curpressure);
        windSpeed = (TextView) view.findViewById(R.id.curwindSpeed);
        windBearing = (TextView) view.findViewById(R.id.curwindBearing);
        cloudCover = (TextView) view.findViewById(R.id.curcloudCover);
        visibility = (TextView) view.findViewById(R.id.curvisibility);

            Currently currently = PresenterDetails.selectedCity.getForecast().getCurrently();
            String _summary = currently.getSummary();
            String _icon = currently.getIcon();
            double _precipIntensity = currently.getPrecipIntensity();
            double _precipProbability = currently.getPrecipProbability();
            String _precipType = currently.getPrecipType();
            double _temperature = currently.getTemperature();
            double _apparentTemperature = currently.getApparentTemperature();
            double _dewPoint = currently.getDewPoint();
            double _humidity = currently.getHumidity();
            double _pressure = currently.getPressure();
            double  _windSpeed = currently.getWindSpeed();
            int _windBearing = currently.getWindBearing();
            double _cloudCover = currently.getCloudCover();
            double _visibility = currently.getVisibility();
            summary.setText(_summary);
            icon.setImageResource(Icons.getDrawableID(_icon));
            precipIntensity.setText(PresenterDetails.dataToString(_precipIntensity)+" mm/h");
            precipProbability.setText(String.valueOf(_precipProbability));
            precipType.setText(_precipType);
            temperature.setText(PresenterDetails.dataToString(_temperature)+"°C");
            apparentTemperature.setText(PresenterDetails.dataToString(_apparentTemperature)+"°C");
            dewPoint.setText(PresenterDetails.dataToString(_dewPoint)+"°C");
            humidity.setText(PresenterDetails.dataToString(_humidity));
            pressure.setText(PresenterDetails.dataToString(_pressure)+" hPa");
            windSpeed.setText(PresenterDetails.dataToString(_windSpeed)+" m/s");
            windBearing.setText(PresenterDetails.dataToString(_windBearing)+"°");
            cloudCover.setText(PresenterDetails.dataToString(_cloudCover));
            visibility.setText(PresenterDetails.dataToString(_visibility)+" km");

        return view;
    }
}
