package com.dailylocally.ui.joinCommunity.communityLocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleAddressResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("aid")
    @Expose
    public String aid;


    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getAid() {
        return aid;
    }
}