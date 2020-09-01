package com.dailylocally.ui.joinCommunity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommunityResponse {

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

    public Boolean getStatus() {
        return status;
    }

    public List<Result> getResult() {
        return result;
    }

    public class Result {

        @SerializedName("comid")
        @Expose
        public Integer comid;
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
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("requested_userid")
        @Expose
        public Object requestedUserid;
        @SerializedName("zoneid")
        @Expose
        public Integer zoneid;
        @SerializedName("no_of_apartments")
        @Expose
        public String noOfApartments;
        @SerializedName("flat_no")
        @Expose
        public Object flatNo;
        @SerializedName("floor_no")
        @Expose
        public Object floorNo;
        @SerializedName("community_address")
        @Expose
        public String communityAddress;


        public Integer getComid() {
            return comid;
        }

        public String getCommunityname() {
            return communityname;
        }

        public String getLat() {
            return lat;
        }

        public String get_long() {
            return _long;
        }

        public String getApartmentname() {
            return apartmentname;
        }

        public String getImage() {
            return image;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public Integer getStatus() {
            return status;
        }

        public Object getRequestedUserid() {
            return requestedUserid;
        }

        public Integer getZoneid() {
            return zoneid;
        }

        public String getNoOfApartments() {
            return noOfApartments;
        }

        public Object getFlatNo() {
            return flatNo;
        }

        public Object getFloorNo() {
            return floorNo;
        }

        public String getCommunityAddress() {
            return communityAddress;
        }
    }
}
