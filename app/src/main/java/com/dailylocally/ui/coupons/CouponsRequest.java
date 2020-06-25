package com.dailylocally.ui.coupons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponsRequest {

    @SerializedName("coupon_name")
    @Expose
    public String couponName;
    @SerializedName("userid")
    @Expose
    public String userid;


    public CouponsRequest(String userid) {
        this.userid = userid;
    }

    public CouponsRequest(String couponName, String userid) {
        this.couponName = couponName;
        this.userid = userid;
    }
}
