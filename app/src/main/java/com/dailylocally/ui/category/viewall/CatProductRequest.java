package com.dailylocally.ui.category.viewall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CatProductRequest {

    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("catid")
    @Expose
    private String catid;

 @SerializedName("scl1_id")
    @Expose
    private String scl1Id;

    public String getScl1Id() {
        return scl1Id;
    }

    public void setScl1Id(String scl1Id) {
        this.scl1Id = scl1Id;
    }

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

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

}