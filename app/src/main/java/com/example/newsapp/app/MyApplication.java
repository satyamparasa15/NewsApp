package com.example.newsapp.app;

import android.app.Activity;
import android.app.Service;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.example.newsapp.BuildConfig;
import com.example.newsapp.di.component.DaggerAppComponent;

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
        //initRealm();

    }

    private void plantTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

//    private void initRealm() {
//        Realm.init(this);
//        RealmConfiguration defaultRealmConfiguration = new RealmConfiguration
//                .Builder()
//                .name(getInstance().getString(R.string.realm_database_name))
//                .schemaVersion(3)
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        Realm.setDefaultConfiguration(defaultRealmConfiguration);
//    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingServiceInjector;
    }
}