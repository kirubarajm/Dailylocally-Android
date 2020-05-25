package com.dailylocally.ui.address.add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressRequestPojo {

    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("address_title")
    @Expose
    private String addressTitle;
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


    @SerializedName("aid")
    @Expose
    private Long aid;


    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
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
