package com.yoyo.airquality.models;

import com.google.gson.annotations.SerializedName;

public class BreeZoMeter {
    @SerializedName(value = "metadata")
    private String metadata = null;
    @SerializedName(value = "data")
    Data data;
    @SerializedName(value = "error")
    private String error = null;


    // Getter Methods

    public String getMetadata() {
        return metadata;
    }

    public Data getData() {
        return data;
    }

    public String getError() {
        return error;
    }

}

