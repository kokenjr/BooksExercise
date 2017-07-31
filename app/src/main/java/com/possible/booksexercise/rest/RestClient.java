package com.possible.booksexercise.rest;

import android.content.Context;

import com.google.gson.Gson;
import com.possible.booksexercise.common.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by korji on 7/29/17.
 */

public class RestClient {
    private ApiService apiService;

    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.REST_ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService(){
        return apiService;
    }
}
