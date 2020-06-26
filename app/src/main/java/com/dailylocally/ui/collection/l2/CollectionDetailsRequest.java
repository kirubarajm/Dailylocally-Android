package com.dailylocally.ui.collection.l2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CollectionDetailsRequest {

    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("cid")
    @Expose
    private String cid;


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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }


}