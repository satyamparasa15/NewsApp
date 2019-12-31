package com.example.newsapp.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {
    @SerializedName("status")
    public String status;
    @SerializedName("totalResults")
    public Integer totalResults;
    @SerializedName("articles")
    public List<Article> articles = null;

}
