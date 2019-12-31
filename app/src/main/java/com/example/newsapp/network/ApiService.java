package com.example.newsapp.network;

import com.example.newsapp.network.model.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
	@GET("top-headlines")
	Single<NewsResponse> getNewsByCountry(@Query("country") String country, @Query("apiKey") String apiKey);

}
