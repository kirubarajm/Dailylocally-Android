package com.dailylocally.ui.joinCommunity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommunityRequest {

    @SerializedName("userid")
    @Expose
    public String userid;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("long")
    @Expose
    public String _long;


    public CommunityRequest(String userid, String lat, String _long) {
        this.userid = userid;
        this.lat = lat;
        this._long = _long;
    }
}
