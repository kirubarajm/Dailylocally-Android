package com.dailylocally.ui.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingSkipRequest {

    @SerializedName("doid")
    @Expose
    private String doid;


    public RatingSkipRequest(String doid) {
        this.doid = doid;
    }
}