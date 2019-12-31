package com.example.newsapp.di.builder;

import com.example.newsapp.ui.home.MainActivity;
import com.example.newsapp.ui.home.MainActivityModule;
import com.example.newsapp.ui.newsFeed.NewsFeedActivity;
import com.example.newsapp.ui.newsFeed.NewsFeedActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

	@ContributesAndroidInjector(modules = MainActivityModule.class)
	abstract MainActivity bindMainctivity();

	@ContributesAndroidInjector(modules = NewsFeedActivityModule.class)
	abstract NewsFeedActivity bindNeewsFeeedActivity();
}
