package com.yoyo.airquality.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yoyo.airquality.models.BreeZoMeter;
import com.yoyo.airquality.network.Api;
import com.yoyo.airquality.network.ApiUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreeZoMeterViewModel extends ViewModel {
    private String API_KEY = "15b90a2ff6424fea867dd34b2b7f38f0";
    private MutableLiveData<BreeZoMeter> mData;
    private Context mContext;
    private double mLat;
    private double mLon;

    public void setLatLon(double lat, double lon) {
        this.mLat = lat;
        this.mLon = lon;
        loadMovies();
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public LiveData<BreeZoMeter> getData() {
        //if the data not created
        if (mData == null) {
            mData = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
        }
        //finally we will return the list
        return mData;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadMovies() {

        Api api = ApiUtil.getRetrofitApi();

        Call<BreeZoMeter> call = api.getData(Double.toString(mLat), Double.toString(mLon), API_KEY);

        call.enqueue(new Callback<BreeZoMeter>() {
            @Override
            public void onResponse(Call<BreeZoMeter> call, Response<BreeZoMeter> response) {

                //finally we are setting the data to our MutableLiveData
                mData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<BreeZoMeter> call, Throwable t) {

                Toast.makeText(mContext,"No Internet Connection",Toast.LENGTH_LONG).show();
            }
        });
    }

}
