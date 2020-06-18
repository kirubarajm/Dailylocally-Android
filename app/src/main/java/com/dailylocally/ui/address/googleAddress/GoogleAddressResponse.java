package com.dailylocally.ui.address.googleAddress;

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
    public Integer aid;


    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }

    public Integer getAid() {
        return aid;
    }
}