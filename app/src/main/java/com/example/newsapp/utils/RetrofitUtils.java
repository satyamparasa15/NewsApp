package com.example.newsapp.utils;

import android.text.TextUtils;

import com.example.newsapp.R;
import com.example.newsapp.app.Constants;
import com.example.newsapp.app.MyApplication;
import com.example.newsapp.network.ErrorResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class RetrofitUtils {
    public static boolean isApiError(Throwable throwable) {
        // Ignoring 401 response code here as it's handled in HTTP interceptor
        return (throwable instanceof HttpException) && ((HttpException) throwable).code() != 401;
    }

    public static ErrorResponse getApiError(Throwable throwable) {
        String errorMessage = null;
        if (throwable instanceof HttpException) {
            ResponseBody body = ((HttpException) throwable).response().errorBody();
            Gson gson = new Gson();
            try {
                String json = body.string();
                JSONObject jsonObject = new JSONObject(json);

                if (jsonObject.has(Constants.ERRORS)) {
                    Object obj = jsonObject.get(Constants.ERRORS);
                    if (obj instanceof String) {
                        errorMessage = jsonObject.getString(Constants.ERRORS);

                    } else if (obj instanceof JSONArray) {

                        if ((String) jsonObject.getJSONArray(Constants.ERRORS).get(0) != null) {
                            errorMessage = (String) jsonObject.getJSONArray(Constants.ERRORS).get(0);
                        }
                    }
                } else if (jsonObject.has(Constants.ERROR)) {
                    errorMessage = jsonObject.getString(Constants.ERROR);
                }


            } catch (JSONException e) {

            } catch (Exception e) {

            }
            errorMessage = TextUtils.isEmpty(errorMessage) ? "Un known error" : errorMessage;
        }
        return new ErrorResponse(errorMessage);
    }
}
