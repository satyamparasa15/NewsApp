package com.example.newsapp.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.example.newsapp.R;
import com.example.newsapp.utils.NoConnectivityEvent;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.Subscribe;

import dagger.android.AndroidInjection;
import timber.log.Timber;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel>
        extends AppCompatActivity {
    private T mViewDataBinding;
    private V mViewModel;

    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        performDataBinding();
        initLoader();
    }

    public void initLoader() {
        mViewModel.getLoading().observe(this, (Observer<Boolean>) aBoolean -> {

            if (aBoolean != null && aBoolean) {
                // showProgress();
            } else {
                // hideProgress();
            }
        });
    }


    private void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = view.findViewById(R.id.activity_content_holder);
        mViewDataBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(),
                activityContainer, true);
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
        super.setContentView(view);


    }


    public void enableToolbarBackNavigation() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void performDataBinding() {
        View view = getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = view.findViewById(R.id.activity_content_holder);
        mViewDataBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), activityContainer, true);
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    private void showNoConnectivitySnack() {
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar
                .make(rootView, getString(R.string.no_internet), Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public abstract V getViewModel();

    public abstract int getBindingVariable();

    public abstract
    @LayoutRes
    int getLayoutId();

    public void handleApiError(Throwable throwable) {
        // TODO - handle network error
        Timber.e("Network error:  %s", throwable.getMessage());
    }

    @Subscribe
    public final void onNoConnectivityEvent(NoConnectivityEvent e) {
        showNoConnectivitySnack();
    }


}
