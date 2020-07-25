package com.dailylocally.ui.address.googleAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckAddressResponse {
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("servicable_status")
    @Expose
    public Boolean servicableStatus;

     @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("message")
    @Expose
    public String message;

    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public Boolean getServicableStatus() {
        return servicableStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
