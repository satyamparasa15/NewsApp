package com.example.newsapp.base;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.R;
import com.example.newsapp.utils.NoConnectivityEvent;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.Subscribe;

import dagger.android.AndroidInjection;
import timber.log.Timber;

public class BaseActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
	}

	private void performDependencyInjection() {
		AndroidInjection.inject(this);
	}

	@Override
	public void setContentView(final int layoutId) {
		View view = getLayoutInflater().inflate(R.layout.activity_base, null);
		super.setContentView(view);

	}

	public void enableToolbarBackNavigation() {
		if (getSupportActionBar() != null)
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

	public void handleApiError(Throwable throwable) {
		// TODO - handle network error
		Timber.e("Network error:  %s", throwable.getMessage());
	}

	@Subscribe
	public final void onNoConnectivityEvent(NoConnectivityEvent e) {
		showNoConnectivitySnack();
	}


}
