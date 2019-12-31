package com.example.newsapp.ui.home;

import androidx.lifecycle.ViewModelProvider;

import com.example.newsapp.di.ViewModelProviderFactory;
import com.example.newsapp.network.ApiService;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    @Provides
    ViewModelProvider.Factory filtersViewModelProvider(MainActivityViewModel mViewModel) {
        return new ViewModelProviderFactory<>(mViewModel);
    }
    @Provides
    MainActivityViewModel providesMainActivityViewModel(
            ApiService apiService) {
        return new MainActivityViewModel(apiService);
    }




}
