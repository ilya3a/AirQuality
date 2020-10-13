package com.yoyo.airquality.models;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName(value = "datetime")
    private String datetime;
    @SerializedName(value = "data_available")
    private boolean data_available;
    @SerializedName(value = "indexes")
    Indexes indexes;


    // Getter Methods

    public String getDatetime() {
        return datetime;
    }

    public boolean getData_available() {
        return data_available;
    }

    public Indexes getIndexes() {
        return indexes;
    }

}
