package com.example.newsapp.ui.newsFeed;

import com.example.newsapp.base.BaseNavigator;
import com.example.newsapp.network.model.Article;
import com.example.newsapp.network.model.NewsResponse;

import java.util.List;

public interface NewsFeedNavigator extends BaseNavigator {

	public void onResponseSuccess(List<Article> responses);

	public  void onResponseFailed();
}
