package com.dailylocally.ui.category.l2.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavRequest {

    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("vpid")
    @Expose
    private String vpid;

    public FavRequest(String userid, String vpid) {
        this.userid = userid;
        this.vpid = vpid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getVpid() {
        return vpid;
    }

    public void setVpid(String vpid) {
        this.vpid = vpid;
    }



}
