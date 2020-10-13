package com.yoyo.airquality.models;

import com.google.gson.annotations.SerializedName;

public class Baqi {
    @SerializedName(value = "display_name")
    private String display_name;
    @SerializedName(value = "aqi")
    private float aqi;
    @SerializedName(value = "aqi_display")
    private String aqi_display;
    @SerializedName(value = "color")
    private String color;
    @SerializedName(value = "category")
    private String category;
    @SerializedName(value = "dominant_pollutant")
    private String dominant_pollutant;


    // Getter Methods

    public String getDisplay_name() {
        return display_name;
    }

    public float getAqi() {
        return aqi;
    }

    public String getAqi_display() {
        return aqi_display;
    }

    public String getColor() {
        return color;
    }

    public String getCategory() {
        return category;
    }

    public String getDominant_pollutant() {
        return dominant_pollutant;
    }

}
