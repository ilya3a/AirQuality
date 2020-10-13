package com.yoyo.airquality.models;

import com.google.gson.annotations.SerializedName;

public class Indexes {
    @SerializedName(value = "baqi")
    Baqi baqi;


    // Getter Methods

    public Baqi getBaqi() {
        return baqi;
    }

}
