package com.example.newsapp.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.newsapp.R;
import com.example.newsapp.base.BaseActivity;
import com.example.newsapp.databinding.ActivityMainBinding;
import com.example.newsapp.network.ErrorResponse;
import com.example.newsapp.ui.newsFeed.NewsFeedActivity;
import com.google.android.gms.location.LocationListener;

import java.util.Timer;

import javax.inject.Inject;

import timber.log.Timber;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel>
        implements MainActivityNavigator, LocationListener {

    @Inject
    MainActivityViewModel mViewModel;

    @Inject
    ViewModelProvider.Factory mViewModelProviderFactory;

    private ActivityMainBinding mViewBinding;

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        setUp();
    }

    private void setUp() {


        mViewBinding.cardViewIndia.setOnClickListener(view -> {
            Timber.e("on location india clicked");
            Intent intentIndia = new Intent(MainActivity.this, NewsFeedActivity.class);
            intentIndia.putExtra("location", "in");
            startActivity(intentIndia);
        });
        mViewBinding.cardViewUs.setOnClickListener(view -> {
            Intent intentIndia = new Intent(MainActivity.this, NewsFeedActivity.class);
            intentIndia.putExtra("location", "us");
            startActivity(intentIndia);
        });
        mViewBinding.cardViewCurrentLocation.setOnClickListener(view -> {
            //  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) this);

//			Intent intentIndia = new Intent(MainActivity.this, NewsFeedActivity.class);
//			intentIndia.putExtra("location", "current");
//			startActivity(intentIndia);
        });

    }


    @Override
    public MainActivityViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this, mViewModelProviderFactory)
                .get(MainActivityViewModel.class);
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return com.example.newsapp.BR.mainActivityViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void getDataFromNetwork() {
        mViewModel.getNewsByCountry();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onApiError(ErrorResponse error) {

    }


    @Override
    public void onLocationChanged(Location location) {
        Timber.e("On location changed %s", location.toString());
    }
}
