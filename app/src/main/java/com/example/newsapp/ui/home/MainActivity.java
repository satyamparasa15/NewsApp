package com.example.newsapp.ui.home;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.newsapp.R;
import com.example.newsapp.base.BaseActivity;
import com.example.newsapp.databinding.ActivityMainBinding;
import com.example.newsapp.network.ErrorResponse;
import com.example.newsapp.ui.newsFeed.NewsFeedActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import javax.inject.Inject;

import timber.log.Timber;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel>
		implements MainActivityNavigator {

	@Inject
	MainActivityViewModel mViewModel;

	@Inject
	ViewModelProvider.Factory mViewModelProviderFactory;

	private ActivityMainBinding mViewBinding;

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
			Intent intentIndia = new Intent(MainActivity.this, NewsFeedActivity.class);
			intentIndia.putExtra("location", "india");
			startActivity(intentIndia);
		});
		mViewBinding.cardViewUs.setOnClickListener(view -> {
			Intent intentIndia = new Intent(MainActivity.this, NewsFeedActivity.class);
			intentIndia.putExtra("location", "us");
			startActivity(intentIndia);
		});
		mViewBinding.cardViewCurrentLocation.setOnClickListener(view -> {
			requestLocationPermission();
//			Intent intentIndia = new Intent(MainActivity.this, NewsFeedActivity.class);
//			intentIndia.putExtra("location", "current");
//			startActivity(intentIndia);
		});
	}

	public void requestLocationPermission() {
		Dexter.withActivity(this)
				.withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
				.withListener(new PermissionListener() {
					@Override
					public void onPermissionGranted(PermissionGrantedResponse response) {
					//	getUserCurrentLocation();
						//getDeviceLocation();
					}

					@Override
					public void onPermissionDenied(PermissionDeniedResponse response) {
						if (response.isPermanentlyDenied()) {
							Timber.e("Need Permissions");
							return;
						}

					}

					@Override
					public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
					                                               PermissionToken token) {
						token.continuePermissionRequest();
					}
				})
				.onSameThread()
				.check();
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


}
