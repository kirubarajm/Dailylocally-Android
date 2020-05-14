package com.dailylocally.ui.signup.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {

    @SerializedName("userid")
    @Expose
    public String userid;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("gender")
    @Expose
    public Integer gender;

    @SerializedName("regionid")
    @Expose
    public Integer regionId;

    @SerializedName("referredby")
    @Expose
    public String referredby;
    @SerializedName("other_region")
    @Expose
    public String otherRegion;

    @SerializedName("hometownid")
    @Expose
    public Integer hometownId;

    @SerializedName("other_hometown")
    @Expose
    public String otherHometown;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReferredby() {
        return referredby;
    }

    public void setReferredby(String referredby) {
        this.referredby = referredby;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public RegistrationRequest(String userid, String name, String email, Integer gender) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.gender = gender;
    }


    public RegistrationRequest(String userid, String name, String email, Integer gender, String referredby) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.referredby = referredby;
    }

    public Integer getHometownId() {
        return hometownId;
    }

    public void setHometownId(Integer hometownId) {
        this.hometownId = hometownId;
    }

    public String getOtherHometown() {
        return otherHometown;
    }

    public void setOtherHometown(String otherHometown) {
        this.otherHometown = otherHometown;
    }

    public String getOtherRegion() {
        return otherRegion;
    }

    public void setOtherRegion(String otherRegion) {
        this.otherRegion = otherRegion;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
