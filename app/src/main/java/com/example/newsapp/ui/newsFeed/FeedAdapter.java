package com.example.newsapp.ui.newsFeed;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.app.Constants;
import com.example.newsapp.base.BaseViewHolder;
import com.example.newsapp.databinding.FeedItemBinding;
import com.example.newsapp.network.model.Article;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import timber.log.Timber;

public class FeedAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<Article> mArticleList;
    private SimpleDateFormat input = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);
    private SimpleDateFormat output = new SimpleDateFormat(Constants.TIME_FORMAT, Locale.ENGLISH);
    private boolean isShowAds;
    private FirebaseRemoteConfig mRemoteConfig;
    private Context mContext;
    private FeedInterface mListener;


    public FeedAdapter(List<Article> articles, Context mContext, FeedInterface mListener) {
        this.mArticleList = articles;
        this.mContext = mContext;
        this.mListener = mListener;
        setUpFirebaseConfig();
    }

    private void setUpFirebaseConfig() {
        mRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mRemoteConfig.setConfigSettingsAsync(configSettings);
        mRemoteConfig.fetch(0).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mRemoteConfig.fetchAndActivate();
                Timber.e("on success state");
                isShowAds = Boolean.parseBoolean(mRemoteConfig.getString("is_show_ads"));
                Timber.e("data %s", mRemoteConfig.getString("is_show_ads"));
            } else {
                Timber.e("on filed state...");
            }
        });

    }

    public void setNewFeedResponse(List<Article> articles) {
        this.mArticleList.clear();
        this.mArticleList.addAll(articles);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FeedItemBinding feedItemBinding = FeedItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewsFeedHolder(feedItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return mArticleList == null ? 0 : mArticleList.size();
    }

    public interface FeedInterface {
        void onNewClick(Article article);
    }

    private class NewsFeedHolder extends BaseViewHolder {
        private FeedItemBinding mFeedItemBinding;
        private Article mArticle;

        public NewsFeedHolder(FeedItemBinding feedItemBinding) {
            super(feedItemBinding.getRoot());
            this.mFeedItemBinding = feedItemBinding;
            mFeedItemBinding.feedCard.setOnClickListener(view -> {
                if (mListener != null) {
                    mListener.onNewClick(mArticle);
                }
            });

        }

        @Override
        public void onBind(int position) {
            mArticle = mArticleList.get(position);
            if (mArticle.urlToImage != null)
                Glide.with(mContext).load(mArticle.urlToImage)
                        .into(mFeedItemBinding.feedImg);

            if (mArticle.title != null) {
                mFeedItemBinding.feedTitle.setText(mArticle.title);
            }
            if (mArticle.description != null) {
                mFeedItemBinding.feedDes.setText(mArticle.description);
            }
            if (mArticle.author != null) {
                mFeedItemBinding.auther.setText(mArticle.author);
            }
            if (mArticle.publishedAt != null) {
                try {
                    mFeedItemBinding.publishedDate.setText(output.format(input.parse(mArticle.publishedAt)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            AdRequest adRequest = new AdRequest.Builder().build();
            if (isShowAds) {
                mFeedItemBinding.adView2.setVisibility(View.VISIBLE);
                mFeedItemBinding.adView2.loadAd(adRequest);
            } else {
                mFeedItemBinding.adView2.setVisibility(View.GONE);
            }

        }
    }
}
