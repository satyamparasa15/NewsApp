package com.example.newsapp.ui.news;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.R;
import com.example.newsapp.app.Constants;

import timber.log.Timber;

public class AboutArticleActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_article);
		setUp();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
		}
		return true;
	}

	private void setUp() {
		WebView webView = findViewById(R.id.article_web);
		ProgressBar progressBar = findViewById(R.id.progress_circular);
		String url = getIntent().getStringExtra(Constants.URL);
		Timber.e("news artcle url %s , ", url);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		if (url != null) {
			webView.setWebViewClient(new WebViewClient() {
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					progressBar.setVisibility(View.VISIBLE);
					view.loadUrl(url);
					return true;
				}

				public void onPageFinished(WebView view, String url) {
					// do your stuff here
					progressBar.setVisibility(View.INVISIBLE);
					webView.setVisibility(View.VISIBLE);

				}
			});
			webView.loadUrl(url);
		}
	}
}
