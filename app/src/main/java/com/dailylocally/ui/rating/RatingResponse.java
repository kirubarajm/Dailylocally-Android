package com.dailylocally.ui.rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("message")
    @Expose
    public String message;


    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
