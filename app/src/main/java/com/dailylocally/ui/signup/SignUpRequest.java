package com.dailylocally.ui.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpRequest {

    @SerializedName("phoneno")
    @Expose
    public String phoneno;
    @SerializedName("otpcode")
    @Expose
    public String otpCode;


    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public SignUpRequest(String phoneno) {
        this.phoneno = phoneno;
    }


    public SignUpRequest(String phoneno, String otpCode) {
        this.phoneno = phoneno;
        this.otpCode = otpCode;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
