package com.solarapp.solarweather.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solarapp.solarweather.R;
import com.solarapp.solarweather.models.Icons;
import com.solarapp.solarweather.models.forecast.Hourly;
import com.solarapp.solarweather.presenters.PresenterDetails;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vereskun on 07.10.2017.
 */

public class HourRVAdapter extends RecyclerView.Adapter<HourRVAdapter.ViewHolder>{

    private Hourly hourly = PresenterDetails.selectedCity.getForecast().getHourly();

    public HourRVAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hour_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String summary = hourly.getData().get(position).getSummary();
        holder.tvSummary.setText(summary);

        int time = hourly.getData().get(position).getTime();
        long dv = Long.valueOf(time) * 1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.getDefault()).format(df);
        holder.hourListTime.setText(vv);

        String icon = hourly.getData().get(position).getIcon();
        holder.imageViewCity.setImageResource(Icons.getDrawableID(icon));

        double temperature = hourly.getData().get(position).getTemperature();
        holder.hourListTemperature.setText(PresenterDetails.dataToString(temperature)+"°C");
    }



    @Override
    public int getItemCount() {
        if (hourly.getData() != null){
            return hourly.getData().size();
        }
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{


        public TextView tvSummary;
        private TextView hourListTime;
        public ImageView imageViewCity;
        private TextView hourListTemperature;


        public ViewHolder(View itemView) {
            super(itemView);

            tvSummary = (TextView) itemView.findViewById(R.id.hourListSummary);
            hourListTime = (TextView) itemView.findViewById(R.id.hourListTime);
            imageViewCity = (ImageView) itemView.findViewById(R.id.hourListIcon);
            hourListTemperature = (TextView) itemView.findViewById(R.id.hourListTemperature);

        }
    }


}
