package com.example.newsapp.ui.home;

import com.example.newsapp.BuildConfig;
import com.example.newsapp.app.Constants;
import com.example.newsapp.base.BaseNavigator;
import com.example.newsapp.base.BaseViewModel;
import com.example.newsapp.network.ApiService;
import com.example.newsapp.utils.RetrofitUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivityViewModel extends BaseViewModel<BaseNavigator> {

	public MainActivityViewModel(ApiService apiService) {
		super(apiService);
	}

	public void getNewsByCountry() {
		getCompositeDisposable().add(
				getApiService().getNewsByCountry("us", BuildConfig.NEWS_API_KEY)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.doOnSubscribe(disposable -> setIsLoading(true))
						.doFinally(() -> setIsLoading(false))
						.subscribe(newsResponse -> {
						}, throwable -> {
							Timber.e(throwable, "error occurred.");
							if (RetrofitUtils.isApiError(throwable)) {
								getNavigator().onApiError(RetrofitUtils.getApiError(throwable));
							}
						})

		);

	}
}
