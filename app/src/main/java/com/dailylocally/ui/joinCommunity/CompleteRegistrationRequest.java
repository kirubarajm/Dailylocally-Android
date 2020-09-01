package com.dailylocally.ui.joinCommunity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompleteRegistrationRequest {

    @SerializedName("communityname")
    @Expose
    public String communityname;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("long")
    @Expose
    public String _long;
    @SerializedName("apartmentname")
    @Expose
    public String apartmentname;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("requested_userid")
    @Expose
    public String requestedUserid;
    @SerializedName("no_of_apartments")
    @Expose
    public String noOfApartments;
    @SerializedName("flat_no")
    @Expose
    public String flatNo;
    @SerializedName("floor_no")
    @Expose
    public String floorNo;
    @SerializedName("community_address")
    @Expose
    public String communityAddress;
    @SerializedName("area")
    @Expose
    public String area;


    public CompleteRegistrationRequest(String communityname, String lat, String _long, String apartmentname, String image, String requestedUserid,
                                       String noOfApartments, String flatNo, String floorNo, String communityAddress,String area) {
        this.communityname = communityname;
        this.lat = lat;
        this._long = _long;
        this.apartmentname = apartmentname;
        this.image = image;
        this.requestedUserid = requestedUserid;
        this.noOfApartments = noOfApartments;
        this.flatNo = flatNo;
        this.floorNo = floorNo;
        this.communityAddress = communityAddress;
        this.area = area;
    }
}
