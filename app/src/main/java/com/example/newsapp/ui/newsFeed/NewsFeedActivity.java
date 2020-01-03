package com.example.newsapp.ui.newsFeed;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.app.Constants;
import com.example.newsapp.base.BaseActivity;
import com.example.newsapp.databinding.ActivityNewsFeedBinding;
import com.example.newsapp.network.ErrorResponse;
import com.example.newsapp.network.model.Article;
import com.example.newsapp.ui.news.AboutArticleActivity;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class NewsFeedActivity extends BaseActivity<ActivityNewsFeedBinding, NewsFeedActivityViewModel>
        implements NewsFeedNavigator, FeedAdapter.FeedInterface {

    @Inject
    NewsFeedActivityViewModel mViewModel;
    @Inject
    ViewModelProvider.Factory mViewModelProviderFactory;
    @Inject
    FeedAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    private ActivityNewsFeedBinding mActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        mActivityBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        enableToolbarBackNavigation();
        setUp();
        getNewsFeed();
    }

    private void getNewsFeed() {
        String location = getIntent().getStringExtra("location");
        Timber.e("Selected string %s", location);
        if (location != null)
            mViewModel.getNewsByCountry(location);
    }

    private void setUp() {
        MobileAds.initialize(this, "ca-app-pub-7491030810733834/4105027046");
        mActivityBinding.feedRv.setLayoutManager(mLayoutManager);
        mActivityBinding.feedRv.setAdapter(mAdapter);
    }

    @Override
    public NewsFeedActivityViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this, mViewModelProviderFactory)
                .get(NewsFeedActivityViewModel.class);
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return com.example.newsapp.BR.mainActivityViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_feed;
    }

    @Override
    public void onApiError(ErrorResponse error) {

    }

    @Override
    public void onNewClick(Article article) {
        Intent intent = new Intent(NewsFeedActivity.this, AboutArticleActivity.class);
        intent.putExtra(Constants.URL, article.url);
        startActivity(intent);

    }

    @Override
    public void onResponseSuccess(List<Article> articles) {
        mAdapter.setNewFeedResponse(articles);
    }

    @Override
    public void onResponseFailed() {
        Timber.e("On Responser Failed..");
    }
}
