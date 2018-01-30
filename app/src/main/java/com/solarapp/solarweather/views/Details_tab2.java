package com.solarapp.solarweather.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solarapp.solarweather.R;
import com.solarapp.solarweather.adapters.HourRVAdapter;
import com.solarapp.solarweather.models.Icons;
import com.solarapp.solarweather.models.forecast.Hourly;
import com.solarapp.solarweather.presenters.PresenterDetails;

/**
 * Created by vereskun on 02.01.2018.
 */

public class Details_tab2 extends Fragment {

    private TextView summary;
    private ImageView icon;
    private RecyclerView hourRV;
    private HourRVAdapter hourRVAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.details_tab2_frag,container,false);
        summary = (TextView) view.findViewById(R.id.hourSummary);
        icon = (ImageView) view.findViewById(R.id.hourIcon);


        Hourly hourly = PresenterDetails.selectedCity.getForecast().getHourly();
        String _summary = hourly.getSummary();
        String _icon = hourly.getIcon();

        summary.setText(_summary);
        icon.setImageResource(Icons.getDrawableID(_icon));

        hourRV = (RecyclerView) view.findViewById(R.id.hourRV);
        layoutManager = new LinearLayoutManager(getContext());
        hourRV.setLayoutManager(layoutManager);
        hourRVAdapter = new HourRVAdapter();
        hourRV.setAdapter(hourRVAdapter);

        return view;
    }

}
