package com.example.newsapp.ui.newsFeed;

import com.example.newsapp.BuildConfig;
import com.example.newsapp.base.BaseViewModel;
import com.example.newsapp.network.ApiService;
import com.example.newsapp.utils.RetrofitUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class NewsFeedActivityViewModel extends BaseViewModel<NewsFeedNavigator> {

	public NewsFeedActivityViewModel(ApiService apiService) {
		super(apiService);
	}

	public void getNewsByCountry(String location) {
		getCompositeDisposable().add(
				getApiService().getNewsByCountry(location, BuildConfig.NEWS_API_KEY)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.doOnSubscribe(disposable -> setIsLoading(true))
						.doFinally(() -> setIsLoading(false))
						.subscribe(newsResponse -> {
							getNavigator().onResponseSuccess(newsResponse.articles);
						}, throwable -> {
							Timber.e(throwable, "error occurred.");
							if (RetrofitUtils.isApiError(throwable)) {
								getNavigator().onApiError(RetrofitUtils.getApiError(throwable));
							}
						})

		);

	}
}
