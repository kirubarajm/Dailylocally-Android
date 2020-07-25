package com.dailylocally.ui.address.googleAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckAddressRequest {


    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;

    public CheckAddressRequest(String userid, String lat, String lon) {
        this.userid = userid;
        this.lat = lat;
        this.lon = lon;
    }
}
