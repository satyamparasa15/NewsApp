package com.example.newsapp.network;

import com.example.newsapp.network.model.NewsResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    //https://newsapi.org/v2/top-headlines?country=us&apiKey=b1ad8dd468d8438697f055b98f057228

    @GET("top-headlines")
    Single<NewsResponse> getNewsByCountry(@Path("country") String country);

}
