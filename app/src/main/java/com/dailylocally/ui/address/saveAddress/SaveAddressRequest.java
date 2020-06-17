package com.dailylocally.ui.address.saveAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveAddressRequest {

    @SerializedName("userid")
    @Expose
    public Integer userid;
    @SerializedName("address_type")
    @Expose
    public Integer addressType;
    @SerializedName("google_address")
    @Expose
    public String googleAddress;
    @SerializedName("complete_address")
    @Expose
    public String completeAddress;
    @SerializedName("flat_house_no")
    @Expose
    public String flatHouseNo;
    @SerializedName("plot_house_no")
    @Expose
    public String plotHouseNo;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("pincode")
    @Expose
    public String pincode;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lon")
    @Expose
    public String lon;
    @SerializedName("landmark")
    @Expose
    public String landmark;
    @SerializedName("floor")
    @Expose
    public String floor;
    @SerializedName("block_name")
    @Expose
    public String blockName;
    @SerializedName("apartment_name")
    @Expose
    public String apartmentName;


    public SaveAddressRequest(Integer userid, Integer addressType, String googleAddress, String completeAddress, String flatHouseNo, String plotHouseNo, String city, String pincode, String lat, String lon, String landmark, String floor, String blockName, String apartmentName) {
        this.userid = userid;
        this.addressType = addressType;
        this.googleAddress = googleAddress;
        this.completeAddress = completeAddress;
        this.flatHouseNo = flatHouseNo;
        this.plotHouseNo = plotHouseNo;
        this.city = city;
        this.pincode = pincode;
        this.lat = lat;
        this.lon = lon;
        this.landmark = landmark;
        this.floor = floor;
        this.blockName = blockName;
        this.apartmentName = apartmentName;
    }
}
