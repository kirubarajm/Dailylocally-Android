package com.dailylocally.ui.subscription;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscriptionTotalAmountRequest {
    @SerializedName("vpid")
    @Expose
    private String vpid;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("planid")
    @Expose
    private Integer planid;


    public SubscriptionTotalAmountRequest(String vpid, String lat, String lon, String userid, Integer quantity, Integer planid) {
        this.vpid = vpid;
        this.lat = lat;
        this.lon = lon;
        this.userid = userid;
        this.quantity = quantity;
        this.planid = planid;
    }


    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getUserid() {
        return userid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPlanid() {
        return planid;
    }
}