package com.dailylocally.ui.joinCommunity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchCommunityRequest {

    @SerializedName("search")
    @Expose
    public String search;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lon")
    @Expose
    public String lon;


    public SearchCommunityRequest(String search, String lat, String lon) {
        this.search = search;
        this.lat = lat;
        this.lon = lon;
    }
}
