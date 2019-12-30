package com.example.newsapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {

	private Context mContext;

	public ConnectivityInterceptor(Context context) {
		this.mContext = context;
	}


	@Override
	public Response intercept(Chain chain) throws IOException {
		if (!isNetworkAvailable()) {
			EventBus.getDefault().post(NoConnectivityEvent.instance());
			throw new NoConnectivityException(mContext);
		} else {
			Response response = chain.proceed(chain.request());
			return response;
		}

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager cm =
				(ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}
}
