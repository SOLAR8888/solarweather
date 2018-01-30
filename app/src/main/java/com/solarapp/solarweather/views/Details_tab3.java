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
import com.solarapp.solarweather.adapters.DailyRVAdapter;
import com.solarapp.solarweather.models.Icons;
import com.solarapp.solarweather.models.forecast.Daily;
import com.solarapp.solarweather.presenters.PresenterDetails;

/**
 * Created by vereskun on 02.01.2018.
 */

public class Details_tab3 extends Fragment {

    private TextView summary;
    private ImageView icon;
    private RecyclerView dailyRV;
    private DailyRVAdapter dailyRVAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.details_tab3_frag,container,false);
        summary = (TextView) view.findViewById(R.id.dailySummary);
        icon = (ImageView) view.findViewById(R.id.dailyIcon);

        Daily daily = PresenterDetails.selectedCity.getForecast().getDaily();
        String _summary = daily.getSummary();
        String _icon = daily.getIcon();

        summary.setText(_summary);
        icon.setImageResource(Icons.getDrawableID(_icon));

        dailyRV = (RecyclerView) view.findViewById(R.id.dailyRV);
        layoutManager = new LinearLayoutManager(getContext());
        dailyRV.setLayoutManager(layoutManager);
        dailyRVAdapter = new DailyRVAdapter();
        dailyRV.setAdapter(dailyRVAdapter);

        return view;
    }

}
