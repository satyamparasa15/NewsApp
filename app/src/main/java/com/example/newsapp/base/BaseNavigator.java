package com.example.newsapp.base;

import com.example.newsapp.network.ErrorResponse;

public interface BaseNavigator {
    void onApiError(ErrorResponse error);
}