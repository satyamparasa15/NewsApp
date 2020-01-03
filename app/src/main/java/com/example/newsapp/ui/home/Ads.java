package com.example.newsapp.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Ads extends AppCompatActivity {
	private AdView mAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ads);
		//MobileAds.initialize(this, "ca-app-pub-7491030810733834~8684960958");
//		mAdView.setAdSize(AdSize.BANNER);
//		mAdView.setAdUnitId("ca-app-pub-3940256099942544/2247696110");

//		MobileAds.initialize(this, new OnInitializationCompleteListener() {
//			@Override
//			public void onInitializationComplete(InitializationStatus initializationStatus) {
//			}
//		});
		mAdView = findViewById(R.id.adView1);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);


	}
}
