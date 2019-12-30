package com.example.newsapp.utils;


import androidx.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class UnauthorizedInterceptor implements Interceptor {

	@Override
	public Response intercept(@NonNull Chain chain) throws IOException {
		Response response = chain.proceed(chain.request());
		if (response.code() == 401) {
			EventBus.getDefault().post(UnauthorizedEvent.instance(response.body()));
		}
		return response;
	}
}
