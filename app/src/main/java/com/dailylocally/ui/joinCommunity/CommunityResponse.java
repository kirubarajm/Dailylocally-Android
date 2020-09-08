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
        @SerializedName("lon")
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
        public String requestedUserid;
        @SerializedName("zoneid")
        @Expose
        public Integer zoneid;
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
        @SerializedName("whatsapp_group_link")
        @Expose
        public String whatsapp_group_link;
        @SerializedName("updated_at")
        @Expose
        public String updated_at;
        @SerializedName("status_msg")
        @Expose
        public String status_msg;
        @SerializedName("distance")
        @Expose
        public String distance;
        @SerializedName("distance_text")
        @Expose
        public String distance_text;


        public String getDistance() {
            return distance;
        }

        public String getDistance_text() {
            return distance_text;
        }

        public String getArea() {
            return area;
        }

        public String getWhatsapp_group_link() {
            return whatsapp_group_link;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getStatus_msg() {
            return status_msg;
        }

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

        public String getRequestedUserid() {
            return requestedUserid;
        }

        public Integer getZoneid() {
            return zoneid;
        }

        public String getNoOfApartments() {
            return noOfApartments;
        }

        public String getFlatNo() {
            return flatNo;
        }

        public String getFloorNo() {
            return floorNo;
        }

        public String getCommunityAddress() {
            return communityAddress;
        }
    }
}
