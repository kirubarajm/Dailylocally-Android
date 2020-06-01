package com.dailylocally.ui.address.add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserAddressResponse {
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("location_radius")
    @Expose
    public Integer locationRadius;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;

    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public Integer getLocationRadius() {
        return locationRadius;
    }

    public List<Result> getResult() {
        return result;
    }

    public class Result {

        @SerializedName("userid")
        @Expose
        public Integer userid;
        @SerializedName("address_title")
        @Expose
        public String addressTitle;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("flatno")
        @Expose
        public String flatno;
        @SerializedName("locality")
        @Expose
        public String locality;
        @SerializedName("pincode")
        @Expose
        public String pincode;
        @SerializedName("aid")
        @Expose
        public Integer aid;
        @SerializedName("lat")
        @Expose
        public Double lat;
        @SerializedName("lon")
        @Expose
        public Double lon;
        @SerializedName("landmark")
        @Expose
        public String landmark;
        @SerializedName("address_type")
        @Expose
        public Integer addressType;
        @SerializedName("delete_status")
        @Expose
        public Integer deleteStatus;
        @SerializedName("address_default")
        @Expose
        public Integer addressDefault;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;


        public Integer getUserid() {
            return userid;
        }

        public String getAddressTitle() {
            return addressTitle;
        }

        public String getAddress() {
            return address;
        }

        public String getFlatno() {
            return flatno;
        }

        public String getLocality() {
            return locality;
        }

        public String getPincode() {
            return pincode;
        }

        public Integer getAid() {
            return aid;
        }

        public Double getLat() {
            return lat;
        }

        public Double getLon() {
            return lon;
        }

        public String getLandmark() {
            return landmark;
        }

        public Integer getAddressType() {
            return addressType;
        }

        public Integer getDeleteStatus() {
            return deleteStatus;
        }

        public Integer getAddressDefault() {
            return addressDefault;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}
