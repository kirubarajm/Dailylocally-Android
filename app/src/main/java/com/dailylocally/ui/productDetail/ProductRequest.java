package com.dailylocally.ui.productDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductRequest {

    @SerializedName("userid")
    @Expose
    public String userid;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lon")
    @Expose
    public String lon;
    @SerializedName("vpid")
    @Expose
    public String vpid;


    public ProductRequest(String userid, String lat, String lon, String vpid) {
        this.userid = userid;
        this.lat = lat;
        this.lon = lon;
        this.vpid = vpid;
    }
}
