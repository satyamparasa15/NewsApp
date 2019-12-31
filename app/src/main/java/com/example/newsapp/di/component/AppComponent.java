package com.example.newsapp.di.component;

import android.app.Application;

import com.example.newsapp.app.MyApplication;
import com.example.newsapp.di.AppModule;
import com.example.newsapp.di.builder.ActivityBuilder;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})

public interface AppComponent {

	void inject(MyApplication app);

	@Component.Builder
	interface Builder {

		@BindsInstance
		Builder application(Application application);

		AppComponent build();
	}
}

