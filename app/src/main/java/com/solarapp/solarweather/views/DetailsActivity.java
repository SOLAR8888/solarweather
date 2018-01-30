package com.solarapp.solarweather.views;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.solarapp.solarweather.R;
import com.solarapp.solarweather.contracts.ContractDetails;
import com.solarapp.solarweather.models.ModelMain;
import com.solarapp.solarweather.presenters.PresenterDetails;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity implements ContractDetails.IDetailsView{


    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    private String id;
    private ModelMain modelMain;
    private PresenterDetails presenterDetails;
    private Toolbar toolbar;
    private ActionBar actionBar;
    //public static City selectedCity;
    private Details_tab1 detailsTab1;
    private Details_tab2 detailsTab2;
    private Details_tab3 detailsTab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("info");
        actionBar = getSupportActionBar();
        toolbar.setBackground(new ColorDrawable(getResources()
                .getColor(R.color.cardview_dark_background)));
        id = getIntent().getStringExtra("id");
        modelMain = new ModelMain();
        presenterDetails = new PresenterDetails(this, modelMain);
        presenterDetails.setSelectedCity(id);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        presenterDetails.onActivityCreate();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0: detailsTab1 = new Details_tab1();
                    return detailsTab1;
                case 1: detailsTab2 = new Details_tab2();
                    return detailsTab2;
                case 2: detailsTab3 = new Details_tab3();
                    return detailsTab3;
                default: return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.currently);
                case 1:
                    return getResources().getString(R.string.hourly);
                case 2:
                    return getResources().getString(R.string.daily);
            }
            return null;
        }
    }

    @Override
    public void setTitle(String name) {

        int cityDateForecast = PresenterDetails.selectedCity.getForecast().getCurrently().getTime();
        long dv = Long.valueOf(cityDateForecast)*1000;// its need to be in milisecond
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("EEE, d MMM yyyy\nHH:mm", Locale.getDefault()).format(df);
        toolbar.setTitle(name+" "+vv);
    }

}
