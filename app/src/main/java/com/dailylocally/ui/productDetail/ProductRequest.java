package com.dailylocally.ui.productDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductRequest {

    @SerializedName("userid")
    @Expose
    public Integer userid;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lon")
    @Expose
    public String lon;
    @SerializedName("vpid")
    @Expose
    public Integer vpid;


    public ProductRequest(Integer userid, String lat, String lon, Integer vpid) {
        this.userid = userid;
        this.lat = lat;
        this.lon = lon;
        this.vpid = vpid;
    }
}
