package com.example.newsapp.ui.newsFeed;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.di.ViewModelProviderFactory;
import com.example.newsapp.network.ApiService;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsFeedActivityModule {

	@Provides
	ViewModelProvider.Factory newsFeedViewModelProvider(NewsFeedActivityViewModel mViewModel) {
		return new ViewModelProviderFactory<>(mViewModel);
	}

	@Provides
	NewsFeedActivityViewModel providesNewsFeedActivityViewModel(
			ApiService apiService) {
		return new NewsFeedActivityViewModel(apiService);
	}

	@Provides
	FeedAdapter providersNewsFeedAdapter(NewsFeedActivity activity) {
		return new FeedAdapter(new ArrayList<>(), activity.getApplicationContext(), activity);
	}

	@Provides
	RecyclerView.LayoutManager providesLayoutManager(NewsFeedActivity activity) {
		return new LinearLayoutManager(activity);
	}


}
