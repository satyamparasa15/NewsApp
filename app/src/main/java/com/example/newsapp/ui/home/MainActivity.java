package com.example.newsapp.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.newsapp.R;
import com.example.newsapp.app.Constants;
import com.example.newsapp.base.BaseActivity;
import com.example.newsapp.databinding.ActivityMainBinding;
import com.example.newsapp.network.ErrorResponse;
import com.example.newsapp.ui.newsFeed.NewsFeedActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import timber.log.Timber;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel>
		implements MainActivityNavigator, LocationListener {

	protected Context context;
	protected LocationManager mLocationManager;
	@Inject
	MainActivityViewModel mViewModel;
	@Inject
	ViewModelProvider.Factory mViewModelProviderFactory;
	private ActivityMainBinding mViewBinding;
	private FusedLocationProviderClient mFusedLocationClient;
	private Location mLocation;
	private Geocoder mGeoCoder;
	private String mUserCountryCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mViewBinding = getViewDataBinding();
		mViewModel.setNavigator(this);
		setUp();
	}

	private void setUp() {
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mGeoCoder = new Geocoder(MainActivity.this, Locale.getDefault());

		mViewBinding.cardViewIndia.setOnClickListener(view -> {
			Intent intentIndia = new Intent(MainActivity.this, NewsFeedActivity.class);
			intentIndia.putExtra(Constants.LOCATION, Constants.INDIA);
			startActivity(intentIndia);
		});
		mViewBinding.cardViewUs.setOnClickListener(view -> {
			Intent intentIndia = new Intent(MainActivity.this, NewsFeedActivity.class);
			intentIndia.putExtra(Constants.LOCATION, Constants.US);
			startActivity(intentIndia);
		});
		mViewBinding.cardViewCurrentLocation.setOnClickListener(view -> {
			requestLocationPermission();
		});

	}

	public void requestLocationPermission() {
		Dexter.withActivity(this)
				.withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
				.withListener(new PermissionListener() {
					@Override
					public void onPermissionGranted(PermissionGrantedResponse response) {
						getCurrentLocation();
					}

					@Override
					public void onPermissionDenied(PermissionDeniedResponse response) {
						if (response.isPermanentlyDenied()) {
							Toast.makeText(MainActivity.this,
									getString(R.string.permissions_mes), Toast.LENGTH_LONG).show();
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


	private void getCurrentLocation() {

		if (ActivityCompat.checkSelfPermission(MainActivity.this,
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
				ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
						!= PackageManager.PERMISSION_GRANTED) {
			Toast.makeText(this, getString(R.string.permissions_mes), Toast.LENGTH_LONG).show();
			return;
		}
		mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
		mFusedLocationClient.getLastLocation()
				.addOnSuccessListener(this, location -> {
					// Got last known location. In some rare situations this can be null.
					if (location != null) {
						Timber.e("Location found %s ,%s", location.getLongitude(), location.getLatitude());
						try {
							List<Address> addresses = mGeoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
							Address obj = addresses.get(0);
							Timber.e("Country name: %s", obj.getCountryCode());
							mUserCountryCode = obj.getCountryCode();
							if (mUserCountryCode != null) {
								Intent intentIndia = new Intent(MainActivity.this, NewsFeedActivity.class);
								intentIndia.putExtra(Constants.LOCATION, mUserCountryCode);
								startActivity(intentIndia);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						mLocation = location;
					}
				});
		if (mLocation == null) {
			mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
			mLocationManager.removeUpdates(this);
		}

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
		Timber.e("on Location changed  %s ,%s", location.getLongitude(), location.getLatitude());
		if (location != null) {
			try {
				List<Address> addresses = mGeoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
				Address obj = addresses.get(0);
				Timber.e("Country name: %s", obj.getCountryCode());
				mUserCountryCode = obj.getCountryCode();
				mLocationManager.removeUpdates(this);
				if (mUserCountryCode != null) {
					Intent intentIndia = new Intent(MainActivity.this, NewsFeedActivity.class);
					intentIndia.putExtra(Constants.LOCATION, mUserCountryCode);
					startActivity(intentIndia);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void onStatusChanged(String s, int i, Bundle bundle) {

	}

	@Override
	public void onProviderEnabled(String s) {

	}

	@Override
	public void onProviderDisabled(String s) {

	}
}
