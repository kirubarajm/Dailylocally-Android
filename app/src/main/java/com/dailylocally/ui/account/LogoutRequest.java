package com.dailylocally.ui.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogoutRequest {

    @SerializedName("userid")
    @Expose
    public String userid;


    public LogoutRequest(String userid) {
        this.userid = userid;
    }
}
