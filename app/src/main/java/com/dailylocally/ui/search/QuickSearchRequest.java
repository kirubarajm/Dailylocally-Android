package com.dailylocally.ui.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuickSearchRequest {

    @SerializedName("search")
    @Expose
    public String search;
    @SerializedName("userid")
    @Expose
    public String userid;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lon")
    @Expose
    public String lon;


    public QuickSearchRequest(String search, String userid, String lat, String lon) {
        this.search = search;
        this.userid = userid;
        this.lat = lat;
        this.lon = lon;
    }
}
