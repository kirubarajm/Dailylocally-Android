package com.dailylocally.ui.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefaultAddressRequest {

    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("aid")
    @Expose
    private String aid;

    public DefaultAddressRequest(String userid, String aid) {
        this.userid = userid;
        this.aid = aid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

}