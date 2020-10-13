package com.yoyo.airquality.network;

import com.yoyo.airquality.models.BreeZoMeter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Api {

    //add lon lat
    @GET("current-conditions")
    Call<BreeZoMeter> getData(@Query("lat") String lat,
                              @Query("lon") String lon,
                              @Query("key") String apiKey);

}

