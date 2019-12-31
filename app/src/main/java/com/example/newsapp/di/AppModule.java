package com.example.newsapp.di;

import android.app.Application;
import android.content.Context;

import com.example.newsapp.BuildConfig;
import com.example.newsapp.app.Constants;
import com.example.newsapp.app.MyApplication;
import com.example.newsapp.data.AppDatabase;
import com.example.newsapp.data.AppPref;
import com.example.newsapp.network.ApiService;
import com.example.newsapp.utils.ConnectivityInterceptor;
import com.example.newsapp.utils.UnauthorizedInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.newsapp.app.Constants.NEW_API_KEY;

@Module
public class AppModule {

    @Provides
    @Singleton
    AppPref provideAppPrefHelper() {
        return new AppPref(MyApplication.getInstance().getApplicationContext());
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabaseHelper() {
        return new AppDatabase();
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {
        return new AppDatabase();
    }


    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

//	@Provides
//	@PreferenceInfo
//	String providePreferenceName() {
//		return Constants.PREF_NAME;
//	}

//	@Provides
//	@Singleton
//	AppPref provideAppPrefs() {
//		return AppPref.getInstance();
//	}

    @Provides
    @Singleton
    ApiService providesApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, Context context, AppPref appPref) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        builder.hostnameVerifier((hostname, session) -> true);

        if (BuildConfig.DEBUG) {
            // Added interceptor for http logging
            builder.addInterceptor(httpLoggingInterceptor);
        }

        builder.addInterceptor(new UnauthorizedInterceptor());
        builder.addInterceptor(new ConnectivityInterceptor(context));

        builder.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json");
            requestBuilder.addHeader("Authorization", "Bearer " + NEW_API_KEY);
//			//	String userToken = appPref.getAuthToken();
//				if (!TextUtils.isEmpty(userToken)) {
//				requestBuilder.addHeader("Authorization", "Bearer " + userToken);
//				}

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        return builder.build();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


}
