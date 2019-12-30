package com.example.newsapp.utils;

import android.content.Context;

import com.example.newsapp.R;

import java.io.IOException;

public class NoConnectivityException extends IOException {
    private Context context;

    public NoConnectivityException(Context context) {
        this.context = context;
    }

    @Override
    public String getMessage() {
        return context.getString(R.string.no_internet);
    }
}
