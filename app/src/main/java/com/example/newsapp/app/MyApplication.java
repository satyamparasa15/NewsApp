package com.example.newsapp.app;

import android.app.Activity;
import android.app.Application;

import com.example.newsapp.BuildConfig;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class MyApplication extends Application implements HasActivityInjector {

	private static MyApplication mMyAppicationInstance;
	@Inject
	DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

	public static MyApplication getMyAppicationInstance() {
		return mMyAppicationInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mMyAppicationInstance = this;
//		DaggerAppComponent.builder()
//				.application(this)
//				.build()
//				.inject(this);
		plantTree();
	}

	private void plantTree() {
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
	}

	@Override
	public AndroidInjector<Activity> activityInjector() {
		return activityDispatchingAndroidInjector;
	}

}
