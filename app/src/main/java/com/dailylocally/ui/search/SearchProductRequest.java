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
    public Integer type;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("userid")
    @Expose
    public Integer userid;


    public SearchProductRequest(String lat, String lon, Integer type, Integer id, Integer userid) {
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.id = id;
        this.userid = userid;
    }
}
