package com.dailylocally.ui.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchProductRequest {
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lon")
    @Expose
    public String lon;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("userid")
    @Expose
    public String userid;


    public SearchProductRequest(String lat, String lon, String type, String id, String userid) {
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.id = id;
        this.userid = userid;
    }
}
