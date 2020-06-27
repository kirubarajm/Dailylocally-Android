package com.dailylocally.ui.productDetail.productCancel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCancelRequest {

    @SerializedName("doid")
    @Expose
    public Integer doid;
    @SerializedName("vpid")
    @Expose
    public Integer vpid;
    @SerializedName("userid")
    @Expose
    public String userid;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lon")
    @Expose
    public String lon;


    public ProductCancelRequest(Integer doid, Integer vpid, String userid, String lat, String lon) {
        this.doid = doid;
        this.vpid = vpid;
        this.userid = userid;
        this.lat = lat;
        this.lon = lon;
    }
}
