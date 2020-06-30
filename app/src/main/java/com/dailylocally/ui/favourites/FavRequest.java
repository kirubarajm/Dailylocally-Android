package com.dailylocally.ui.favourites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavRequest {

    @SerializedName("userid")
    @Expose
    private String userid;

    public FavRequest(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}