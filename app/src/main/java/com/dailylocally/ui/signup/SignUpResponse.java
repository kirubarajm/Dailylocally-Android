package com.dailylocally.ui.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("passwordstatus")
    @Expose
    public Boolean passwordstatus;
    @SerializedName("otpstatus")
    @Expose
    public Boolean otpstatus;
    @SerializedName("genderstatus")
    @Expose
    public Boolean genderstatus;
    @SerializedName("userid")
    @Expose
    public Long userid;
    @SerializedName("oid")
    @Expose
    public Long oid;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getPasswordstatus() {
        return passwordstatus;
    }

    public void setPasswordstatus(Boolean passwordstatus) {
        this.passwordstatus = passwordstatus;
    }

    public Boolean getOtpstatus() {
        return otpstatus;
    }

    public void setOtpstatus(Boolean otpstatus) {
        this.otpstatus = otpstatus;
    }

    public Boolean getGenderstatus() {
        return genderstatus;
    }

    public void setGenderstatus(Boolean genderstatus) {
        this.genderstatus = genderstatus;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }
}
