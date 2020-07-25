package com.dailylocally.ui.address.googleAddress;

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
        public Object updatedAt;
        @SerializedName("city")
        @Expose
        public String city;
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
        @SerializedName("floor")
        @Expose
        public String floor;
        @SerializedName("block_name")
        @Expose
        public String blockName;
        @SerializedName("apartment_name")
        @Expose
        public String apartmentName;
 @SerializedName("note")
        @Expose
        public String note;

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public Integer getUserid() {
            return userid;
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

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public String getCity() {
            return city;
        }

        public String getGoogleAddress() {
            return googleAddress;
        }

        public String getCompleteAddress() {
            return completeAddress;
        }

        public String getFlatHouseNo() {
            return flatHouseNo;
        }

        public String getPlotHouseNo() {
            return plotHouseNo;
        }

        public String getFloor() {
            return floor;
        }

        public String getBlockName() {
            return blockName;
        }

        public String getApartmentName() {
            return apartmentName;
        }
    }
}
