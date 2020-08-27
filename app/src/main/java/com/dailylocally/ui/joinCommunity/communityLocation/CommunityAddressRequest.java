package com.dailylocally.ui.joinCommunity.communityLocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommunityAddressRequest {


    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;

    public CommunityAddressRequest(String userid, String lat, String lon) {
        this.userid = userid;
        this.lat = lat;
        this.lon = lon;
    }
}
