package com.dailylocally.ui.promotion.bottom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromotionRequest {


    @SerializedName("userid")
    @Expose
    public String userId;

    public PromotionRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
