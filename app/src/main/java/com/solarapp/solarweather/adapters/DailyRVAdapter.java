package com.solarapp.solarweather.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solarapp.solarweather.R;
import com.solarapp.solarweather.models.Icons;
import com.solarapp.solarweather.models.forecast.Daily;
import com.solarapp.solarweather.presenters.PresenterDetails;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vereskun on 07.10.2017.
 */

public class DailyRVAdapter extends RecyclerView.Adapter<DailyRVAdapter.ViewHolder>{

    private Daily daily = PresenterDetails.selectedCity.getForecast().getDaily();

    public DailyRVAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String summary = daily.getData().get(position).getSummary();
        holder.tvSummary.setText(summary);

        int time = daily.getData().get(position).getTime();
        long dv = Long.valueOf(time) * 1000;// its need to be in milisecond
        Date df = new Date(dv);
        String vv = new SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.getDefault()).format(df);
        holder.dailyListTime.setText(vv);

        String icon = daily.getData().get(position).getIcon();
        holder.imageViewCity.setImageResource(Icons.getDrawableID(icon));

        double temperatureLow = daily.getData().get(position).getTemperatureLow();
        double temperatureHigh = daily.getData().get(position).getTemperatureHigh();
        holder.dailyListTemperature.setText(
                PresenterDetails.dataToString(temperatureLow)
                + " ... "
                + PresenterDetails.dataToString(temperatureHigh)
                + " °C");
    }



    @Override
    public int getItemCount() {
        if (daily.getData() != null){
            return daily.getData().size();
        }
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{


        public TextView tvSummary;
        private TextView dailyListTime;
        public ImageView imageViewCity;
        private TextView dailyListTemperature;


        public ViewHolder(View itemView) {
            super(itemView);

            tvSummary = (TextView) itemView.findViewById(R.id.dailyListSummary);
            dailyListTime = (TextView) itemView.findViewById(R.id.dailyListTime);
            imageViewCity = (ImageView) itemView.findViewById(R.id.dailyListIcon);
            dailyListTemperature = (TextView) itemView.findViewById(R.id.dailyListTemperature);

        }
    }


}
