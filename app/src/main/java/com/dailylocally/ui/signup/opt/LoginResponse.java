package com.dailylocally.ui.signup.opt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("userid")
        @Expose
        public String userid;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("phoneno")
        @Expose
        public String phoneno;
        @SerializedName("referalcode")
        @Expose
        public String referalcode;
        @SerializedName("locality")
        @Expose
        public String locality;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("gender")
        @Expose
        public Integer gender;

        @SerializedName("aid")
        @Expose
        private String aid;
        @SerializedName("address_title")
        @Expose
        private String addressTitle;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lon")
        @Expose
        private String lon;
 @SerializedName("city")
        @Expose
        private String city;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getAddressTitle() {
            return addressTitle;
        }

        public void setAddressTitle(String addressTitle) {
            this.addressTitle = addressTitle;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneno() {
            return phoneno;
        }

        public void setPhoneno(String phoneno) {
            this.phoneno = phoneno;
        }

        public String getReferalcode() {
            return referalcode;
        }

        public void setReferalcode(String referalcode) {
            this.referalcode = referalcode;
        }

        public String getLocality() {
            return locality;
        }

        public void setLocality(String locality) {
            this.locality = locality;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }
    }


}
