package com.solarapp.solarweather.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solarapp.solarweather.R;
import com.solarapp.solarweather.models.City;
import com.solarapp.solarweather.presenters.PresenterMain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static com.solarapp.solarweather.models.Icons.getDrawableID;

/**
 * Created by vereskun on 07.10.2017.
 */

public class MainCityRVAdapter extends RecyclerView.Adapter<MainCityRVAdapter.ViewHolder>{

    private ArrayList<City> cityArrayList;
    private PresenterMain presenterMain;

    public MainCityRVAdapter(PresenterMain presenterMain) {
        this.presenterMain = presenterMain;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_city_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (cityArrayList != null){
            //city name
            String cityName = cityArrayList.get(position).getName();
            holder.tvCityName.setText(cityName);

            //photo

            if (cityArrayList.get(position).getPhoto() != null
                    && cityArrayList.get(position).getPhoto().size() > 0
                    && cityArrayList.get(position).getPhotoIsSetted() == false) {
                //System.out.println(cityArrayList.get(position).getPhoto().size());
                Random random = new Random();
                int photoIndex = random.nextInt(cityArrayList.get(position).getPhoto().size());
                byte[] byteArray = cityArrayList.get(position).getPhoto().get(photoIndex).getArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                holder.imageViewCity.setImageBitmap(bitmap);
                //cityArrayList.get(position).setPhotoIsSetted(true);
            }
            else {
                holder.imageViewCity.setImageResource(R.drawable.ic_cogwheel_outline);
            }
            holder.btnNextPict.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cityArrayList.get(position).getPhoto() != null
                            && cityArrayList.get(position).getPhoto().size() > 0
                            && cityArrayList.get(position).getPhotoIsSetted() == false) {
                        //System.out.println(cityArrayList.get(position).getPhoto().size());
                        Random random = new Random();
                        int photoIndex = random.nextInt(cityArrayList.get(position).getPhoto().size());
                        byte[] byteArray = cityArrayList.get(position).getPhoto().get(photoIndex).getArray();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        holder.imageViewCity.setImageBitmap(bitmap);
                        //cityArrayList.get(position).setPhotoIsSetted(true);
                    }
                }
            });
            //time of forecast
            if (cityArrayList.get(position).getForecast() != null) {
                int cityDateForecast = cityArrayList.get(position).getForecast().getCurrently().getTime();
                long dv = Long.valueOf(cityDateForecast) * 1000;// its need to be in milisecond
                Date df = new java.util.Date(dv);
                String vv = new SimpleDateFormat("EEE, d MMM yyyy\nHH:mm", Locale.getDefault()).format(df);
                holder.tvDateForecast.setText(vv);

                holder.tapMask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenterMain.onImageCityClick(cityArrayList.get(position).getId());
                    }
                });
                //icon currently
                String icon = cityArrayList.get(position).getForecast().getCurrently().getIcon();
                holder.iconCurrently.setImageResource(getDrawableID(icon));
                //temperature
                double temperature = cityArrayList.get(position).getForecast().getCurrently().getTemperature();
                //holder.tvTempCurrently.setText(String.valueOf(temperature));
                //summary currently
                String summary = cityArrayList.get(position).getForecast().getCurrently().getSummary();
                holder.tvSummary.setText(String.valueOf(temperature) + "°C" + ", " + summary);
            }
            //remove icon
            holder.cityItemRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenterMain.onBtnItemRemoveClick(cityArrayList.get(position).getId());
                }
            });


        }
    }



    @Override
    public int getItemCount() {
        if (cityArrayList != null){
            return cityArrayList.size();
        }
        return 0;
    }

    public MainCityRVAdapter(ArrayList<City> cityArrayList, PresenterMain presenterMain) {
        this.cityArrayList = cityArrayList;
        this.presenterMain = presenterMain;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvCityName;
        public TextView tvSummary;
        public TextView tvDateForecast;
        //public TextView tvTempCurrently;
        public ImageView imageViewCity;
        public ImageButton cityItemRemove;
        public ImageButton btnNextPict;
        public ImageView iconCurrently;
        public LinearLayout tapMask;


        public ViewHolder(View itemView) {
            super(itemView);
            tvCityName = (TextView) itemView.findViewById(R.id.tvCityName);
            tvSummary = (TextView) itemView.findViewById(R.id.tvSummary);
            tvDateForecast = (TextView) itemView.findViewById(R.id.tvDateForecast);
            //tvTempCurrently = (TextView) itemView.findViewById(R.id.tvTempCurrently);
            imageViewCity = (ImageView) itemView.findViewById(R.id.imageViewCity);
            cityItemRemove = (ImageButton) itemView.findViewById(R.id.cityItemRemove);
            btnNextPict = (ImageButton) itemView.findViewById(R.id.btnNextPict);
            iconCurrently = (ImageView) itemView.findViewById(R.id.iconCurrently);
            tapMask = (LinearLayout) itemView.findViewById(R.id.tapMask);
        }
    }

    public void update (ArrayList<City> arrayList){
        if (cityArrayList != null) {
            cityArrayList.clear();
            cityArrayList.addAll(arrayList);
        }
        else {
            cityArrayList = arrayList;
        }
        notifyDataSetChanged();
    }
}
