package com.example.newsapp.utils;

public class NoConnectivityEvent {
	private NoConnectivityEvent() {

	}

	public static NoConnectivityEvent instance() {
		return new NoConnectivityEvent();
	}
}
