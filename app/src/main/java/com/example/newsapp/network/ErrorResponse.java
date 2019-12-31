package com.example.newsapp.network;

import timber.log.Timber;

public class ErrorResponse {
	public String error;

	public ErrorResponse(String error) {
		this.error = error;
	}

	public String getError() {
		Timber.e("error mes %s", error);
		return error;
	}
}
