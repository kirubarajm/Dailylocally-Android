package com.dailylocally.ui.signup.opt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OtpResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("passwordstatus")
    @Expose
    private Boolean passwordstatus;
    @SerializedName("emailstatus")
    @Expose
    private Boolean emailstatus;

    @SerializedName("otpstatus")
    @Expose
    private Boolean otpstatus;
    @SerializedName("genderstatus")
    @Expose
    private Boolean genderstatus;
    @SerializedName("registrationstatus")
    @Expose
    private Boolean registrationstatus;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("regionid")
    @Expose
    private Integer regionid;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("razer_customerid")
    @Expose
    private String razerCustomerid;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;


    public Boolean getRegistrationstatus() {
        return registrationstatus;
    }

    public String getRazerCustomerid() {
        return razerCustomerid;
    }

    public void setRazerCustomerid(String razerCustomerid) {
        this.razerCustomerid = razerCustomerid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getEmailstatus() {
        return emailstatus;
    }

    public void setEmailstatus(Boolean emailstatus) {
        this.emailstatus = emailstatus;
    }

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getRegionid() {
        return regionid;
    }

    public void setRegionid(Integer regionid) {
        this.regionid = regionid;
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
        private String userid;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("flatno")
        @Expose
        private String flatno;
        @SerializedName("locality")
        @Expose
        private String locality;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("aid")
        @Expose
        private String aid;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lon")
        @Expose
        private String lon;
        @SerializedName("landmark")
        @Expose
        private String landmark;
        @SerializedName("address_type")
        @Expose
        private Integer addressType;
        @SerializedName("city")
        @Expose
        private String city;

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phoneno")
        @Expose
        private String phoneno;
        @SerializedName("referalcode")
        @Expose
        private String referalcode;
        @SerializedName("gender")
        @Expose
        private Integer gender;


        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
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

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }



        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }



        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getFlatno() {
            return flatno;
        }

        public void setFlatno(String flatno) {
            this.flatno = flatno;
        }

        public String getLocality() {
            return locality;
        }

        public void setLocality(String locality) {
            this.locality = locality;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
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

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public Integer getAddressType() {
            return addressType;
        }

        public void setAddressType(Integer addressType) {
            this.addressType = addressType;
        }




    }

}
