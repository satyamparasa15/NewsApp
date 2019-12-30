package com.example.newsapp.di.builder;

import com.example.newsapp.ui.MainActivity;
import com.example.newsapp.ui.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
@Module
public abstract  class ActivityBuilder {
	@ContributesAndroidInjector(modules = MainActivityModule.class)
	abstract MainActivity bindMainctivity();
}
