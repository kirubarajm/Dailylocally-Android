package com.dailylocally.ui.address.googleAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleAddressResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("status")
    @Expose
    private Boolean status;


    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("aid")
    @Expose
    private String aid;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}