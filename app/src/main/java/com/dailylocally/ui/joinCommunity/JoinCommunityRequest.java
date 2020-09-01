package com.dailylocally.ui.joinCommunity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinCommunityRequest {

    @SerializedName("userid")
    @Expose
    public String userid;
    @SerializedName("comid")
    @Expose
    public String comid;
    @SerializedName("profile_image")
    @Expose
    public String profileImage;
    @SerializedName("flat_no")
    @Expose
    public String flatNo;
    @SerializedName("floor_no")
    @Expose
    public String floorNo;


    public JoinCommunityRequest(String userid, String comid, String profileImage, String flatNo, String floorNo) {
        this.userid = userid;
        this.comid = comid;
        this.profileImage = profileImage;
        this.flatNo = flatNo;
        this.floorNo = floorNo;
    }
}
