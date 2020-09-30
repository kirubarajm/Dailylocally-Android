package com.dailylocally.ui.joinCommunity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JoinCommunityResponse {


    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;
    @SerializedName("message")
    @Expose
    public String message;

    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public List<Result> getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public class Result {

        @SerializedName("userid")
        @Expose
        public Integer userid;
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
        @SerializedName("pushid_android")
        @Expose
        public String pushidAndroid;
        @SerializedName("otp_status")
        @Expose
        public Integer otpStatus;
        @SerializedName("gender")
        @Expose
        public Integer gender;
        @SerializedName("pushid_ios")
        @Expose
        public Object pushidIos;
        @SerializedName("referredby")
        @Expose
        public Object referredby;
        @SerializedName("zendeskuserid")
        @Expose
        public Object zendeskuserid;
        @SerializedName("zoho_book_customer_id")
        @Expose
        public Object zohoBookCustomerId;
        @SerializedName("zoho_book_address_id")
        @Expose
        public Object zohoBookAddressId;
        @SerializedName("razer_customerid")
        @Expose
        public String razerCustomerid;
        @SerializedName("premium_user")
        @Expose
        public Object premiumUser;
        @SerializedName("userclusterid")
        @Expose
        public Integer userclusterid;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("virtualkey")
        @Expose
        public Integer virtualkey;
        @SerializedName("profile_image")
        @Expose
        public String profileImage;
        @SerializedName("aid")
        @Expose
        public Integer aid;
        @SerializedName("lat")
        @Expose
        public Double lat;
        @SerializedName("lon")
        @Expose
        public Double lon;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("address_type")
        @Expose
        public Integer addressType;
        @SerializedName("delete_status")
        @Expose
        public Integer deleteStatus;
        @SerializedName("address_default")
        @Expose
        public Integer addressDefault;
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
        @SerializedName("google_address")
        @Expose
        public String googleAddress;
        @SerializedName("complete_address")
        @Expose
        public String completeAddress;


        public Integer getUserid() {
            return userid;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoneno() {
            return phoneno;
        }

        public String getReferalcode() {
            return referalcode;
        }

        public String getPushidAndroid() {
            return pushidAndroid;
        }

        public Integer getOtpStatus() {
            return otpStatus;
        }

        public Integer getGender() {
            return gender;
        }

        public Object getPushidIos() {
            return pushidIos;
        }

        public Object getReferredby() {
            return referredby;
        }

        public Object getZendeskuserid() {
            return zendeskuserid;
        }

        public Object getZohoBookCustomerId() {
            return zohoBookCustomerId;
        }

        public Object getZohoBookAddressId() {
            return zohoBookAddressId;
        }

        public String getRazerCustomerid() {
            return razerCustomerid;
        }

        public Object getPremiumUser() {
            return premiumUser;
        }

        public Integer getUserclusterid() {
            return userclusterid;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public Integer getVirtualkey() {
            return virtualkey;
        }

        public String getProfileImage() {
            return profileImage;
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

        public String getCity() {
            return city;
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

        public String getGoogleAddress() {
            return googleAddress;
        }

        public String getCompleteAddress() {
            return completeAddress;
        }
    }
}
