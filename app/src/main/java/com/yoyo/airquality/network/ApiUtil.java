package com.yoyo.airquality.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtil {

    static String BASE_URL = "https://api.breezometer.com/air-quality/v2/";

    public static Api getRetrofitApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        return api;

    }

}

