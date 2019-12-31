package com.example.newsapp.base;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.network.ApiService;

import java.lang.ref.WeakReference;


import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel<N> extends ViewModel {

    private final ApiService apiService;
    private final MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MutableLiveData<Throwable> error = new MutableLiveData<>();
    private WeakReference<N> mNavigator;

    public BaseViewModel( ApiService apiService) {
        this.apiService = apiService;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public ApiService getApiService() {
        return apiService;
    }

    public MutableLiveData<Boolean> getLoading() {
        return mIsLoading;
    }

    public LiveData<Throwable> getError() {
        return error;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.setValue(isLoading);
    }

    public void setError(Throwable throwable) {
        error.setValue(throwable);
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public N getNavigator() {
        return mNavigator.get();
    }
}
