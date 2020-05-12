package com.dailylocally.ui.signup.opt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {


    @SerializedName("phoneno")
    @Expose
    public String phoneno;
    @SerializedName("password")
    @Expose
    public String password;


    public LoginRequest(String phoneno, String password) {
        this.phoneno = phoneno;
        this.password = password;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
