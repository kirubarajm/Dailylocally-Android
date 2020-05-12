package com.dailylocally.ui.signup.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenRequest {

@SerializedName("userid")
@Expose
private Long userid;
@SerializedName("pushid_android")
@Expose
private String pushidAndroid;


    public TokenRequest(Long userid, String pushidAndroid) {
        this.userid = userid;
        this.pushidAndroid = pushidAndroid;
    }

    public Long getUserid() {
return userid;
}

public void setUserid(Long userid) {
this.userid = userid;
}

public String getPushidAndroid() {
return pushidAndroid;
}

public void setPushidAndroid(String pushidAndroid) {
this.pushidAndroid = pushidAndroid;
}

}