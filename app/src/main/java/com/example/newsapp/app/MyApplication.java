package com.example.newsapp.app;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDexApplication;

import com.example.newsapp.BuildConfig;
import com.example.newsapp.di.component.DaggerAppComponent;
import com.example.newsapp.ui.news.AboutArticleActivity;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import timber.log.Timber;

public class MyApplication extends MultiDexApplication implements HasActivityInjector, HasServiceInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Service> dispatchingServiceInjector;

    private static MyApplication mInstance;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Places.initialize(getApplicationContext(), BuildConfig.PLACES_API_KEY);
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
        mInstance = this;
        plantTimber();
        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationOpenedHandler(new MyNotificationOpenedHandler(this))
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


    }

    private void plantTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingServiceInjector;
    }

    private class MyNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        public MyNotificationOpenedHandler(MyApplication myApplication) {
        }

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            JSONObject data = result.notification.payload.additionalData;
            if (data != null) {
                String newsUrl = data.optString(Constants.URL);
                Intent intent = new Intent(getBaseContext(), AboutArticleActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Constants.URL, newsUrl);
                startActivity(intent);
            }
        }
    }

}