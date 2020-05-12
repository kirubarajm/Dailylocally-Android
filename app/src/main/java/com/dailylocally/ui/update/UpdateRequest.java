package com.dailylocally.ui.update;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateRequest {

    @SerializedName("eatversioncode")
    @Expose
    private Integer eatversioncode;


    public UpdateRequest(Integer eatversioncode) {
        this.eatversioncode = eatversioncode;
    }

    public Integer getEatversioncode() {
        return eatversioncode;
    }

    public void setEatversioncode(Integer eatversioncode) {
        this.eatversioncode = eatversioncode;
    }
}