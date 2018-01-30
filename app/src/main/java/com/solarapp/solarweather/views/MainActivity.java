package com.solarapp.solarweather.views;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.RewardedVideoCallbacks;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.solarapp.solarweather.R;
import com.solarapp.solarweather.adapters.MainCityRVAdapter;
import com.solarapp.solarweather.contracts.ContractMain;
import com.solarapp.solarweather.models.City;
import com.solarapp.solarweather.models.ModelMain;
import com.solarapp.solarweather.presenters.PresenterMain;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContractMain.IMainView{

    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private PresenterMain presenterMain;
    private ModelMain modelMain;
    private MainCityRVAdapter rvAdapter;
    private RecyclerView mainCityList;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fabAdd;
    private ActionBar actionBar;
    private boolean tryExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modelMain = new ModelMain();
        presenterMain = new PresenterMain(this, modelMain);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.cardview_dark_background)));
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAddCity);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterMain.onBtnAddClick();
            }
        });
        mainCityList = (RecyclerView) findViewById(R.id.mainCityList);
        mLayoutManager = new LinearLayoutManager(this);
        mainCityList.setLayoutManager(mLayoutManager);
        rvAdapter = new MainCityRVAdapter(presenterMain);
        mainCityList.setAdapter(rvAdapter);
//        mainCityList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                presenterMain.onItemClick(position);
//
//            }
//        }));
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setBackgroundColor(getResources().getColor(R.color.cast_expanded_controller_ad_container_white_stripe_color));
        presenterMain.onActivityCreate();

        String appKey = getResources().getString(R.string.appodeal_key);
        Appodeal.initialize(this, appKey, Appodeal.REWARDED_VIDEO);
    }

    @Override
    public void showSearchWidget() {
        if (Appodeal.isLoaded(Appodeal.REWARDED_VIDEO)){
            Appodeal.show(this, Appodeal.REWARDED_VIDEO);
        }
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            System.out.println("showSearchWidget: "+e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            System.out.println("showSearchWidget: "+e.getMessage());
        }
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void updateAdapter(ArrayList<City> cityArrayList) {
        rvAdapter.update(cityArrayList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                presenterMain.onResultPlaceSearch(place);
                Log.i("onActivityResult", "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("onActivityResult", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("onActivityResult: user canceled");
            }
        }
    }

    @Override
    public void showDetailsActivity(String id) {
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        tryExit = true;
        if (Appodeal.isLoaded(Appodeal.REWARDED_VIDEO)){
            Appodeal.show(this, Appodeal.REWARDED_VIDEO);
            Appodeal.setRewardedVideoCallbacks(new RewardedVideoCallbacks() {
                @Override
                public void onRewardedVideoLoaded() {

                }

                @Override
                public void onRewardedVideoFailedToLoad() {
                    if (tryExit){
                        finish();
                    }
                }

                @Override
                public void onRewardedVideoShown() {

                }

                @Override
                public void onRewardedVideoFinished(int i, String s) {

                }

                @Override
                public void onRewardedVideoClosed(boolean b) {
                    if (tryExit){
                        finish();
                    }
                }
            });
        }
        else {
            super.onBackPressed();
            finish();
        }

    }

}
