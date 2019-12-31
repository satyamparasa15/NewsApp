package com.example.newsapp.data;

import android.content.Context;

import com.example.newsapp.app.MyApplication;
import com.google.gson.Gson;

import static com.example.newsapp.app.Constants.PREF_NAME;

public class AppPref {
    private static AppPref singleTonInstance = null;
    private Context context;

    public AppPref(Context context) {
        super();
        this.context = context;

    }

    public static AppPref getInstance() {
        if (singleTonInstance == null) {
            singleTonInstance = new AppPref(MyApplication.getInstance().getApplicationContext());
        }
        return singleTonInstance;
    }
}
