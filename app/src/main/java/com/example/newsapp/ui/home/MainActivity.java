package com.example.newsapp.ui.home;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.newsapp.BR;
import com.example.newsapp.R;
import com.example.newsapp.base.BaseActivity;
import com.example.newsapp.databinding.ActivityMainBinding;
import com.example.newsapp.network.ErrorResponse;

import javax.inject.Inject;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel>
        implements MainActivityNavigator {

    @Inject
    MainActivityViewModel mViewModel;

    @Inject
    ViewModelProvider.Factory mViewModelProviderFactory;

    private ActivityMainBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewBinding = getViewDataBinding();
     //   mViewModel.setNavigator(this);
        getDataFromNetwork();
    }

    @Override
    public MainActivityViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this, mViewModelProviderFactory)
                .get(MainActivityViewModel.class);
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return com.example.newsapp.BR.mainActivityViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void getDataFromNetwork() {
        mViewModel.getNewsByCountry();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onApiError(ErrorResponse error) {

    }


}
