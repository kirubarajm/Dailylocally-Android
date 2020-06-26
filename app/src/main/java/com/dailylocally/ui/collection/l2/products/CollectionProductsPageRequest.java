package com.dailylocally.ui.collection.l2.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CollectionProductsPageRequest {
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }



}
