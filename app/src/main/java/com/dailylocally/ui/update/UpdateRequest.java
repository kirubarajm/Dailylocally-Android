package com.dailylocally.ui.update;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateRequest {

    @SerializedName("eatversioncode")
    @Expose
    private Integer eatversioncode;
  @SerializedName("userid")
    @Expose
    private String userid;


    public UpdateRequest(Integer eatversioncode,String userid) {
        this.eatversioncode = eatversioncode;
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getEatversioncode() {
        return eatversioncode;
    }

    public void setEatversioncode(Integer eatversioncode) {
        this.eatversioncode = eatversioncode;
    }
}