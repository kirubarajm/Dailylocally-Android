package com.dailylocally.ui.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommunityUserDetailsResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;
    @SerializedName("userdetails")
    @Expose
    public List<Userdetail> userdetails = null;

    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public List<Result> getResult() {
        return result;
    }

    public List<Userdetail> getuserdetails() {
        return userdetails;
    }

    public static class Result {

        @SerializedName("userid")
        @Expose
        public String userid;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("phoneno")
        @Expose
        public String phoneno;

        @SerializedName("premium_user")
        @Expose
        public Integer premiumUser;
        @SerializedName("comid")
        @Expose
        public Integer comid;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("flat_no")
        @Expose
        public String flatNo;
        @SerializedName("profile_image")
        @Expose
        public String profileImage;
        @SerializedName("floor_no")
        @Expose
        public String floorNo;
        @SerializedName("welcome_text")
        @Expose
        public String welcomeText;
        @SerializedName("join_status")
        @Expose
        public Integer joinStatus;
        @SerializedName("members_count")
        @Expose
        public String membersCount;
        @SerializedName("members")
        @Expose
        public String members;
        @SerializedName("total_credits")
        @Expose
        public String totalCredits;
        @SerializedName("credits_text")
        @Expose
        public String creditsText;
        @SerializedName("credits_info")
        @Expose
        public String creditsInfo;
        @SerializedName("show_credits_info")
        @Expose
        public Boolean showCreditsInfo;

        @SerializedName("community_status")
        @Expose
        public Boolean communityStatus;


        @SerializedName("welcome_name_title")
        @Expose
        public String welcomeNameTitle;
        @SerializedName("welcome_name_content")
        @Expose
        public String welcomeNameContent;
        @SerializedName("min_cart_text")
        @Expose
        public String minCartText;
        @SerializedName("min_cart_value")
        @Expose
        public String minCartValue;
        @SerializedName("free_delivery_text")
        @Expose
        public String freeDeliveryText;
        @SerializedName("free_delivery_value")
        @Expose
        public String freeDeliveryValue;
        @SerializedName("cod_text")
        @Expose
        public String codText;
        @SerializedName("cod_value")
        @Expose
        public String codValue;
        @SerializedName("communityname")
        @Expose
        public String communityName;
        @SerializedName("area")
        @Expose
        public String communityArea;

        @SerializedName("cat_page_subcontent")
        @Expose
        public String catPageSubcontent;

        @SerializedName("cat_page_content")
        @Expose
        public String catPageContent;

        @SerializedName("home_page_content")
        @Expose
        public String homePageContent;

        @SerializedName("home_page_subcontent")
        @Expose
        public String homePageSubcontent;


        public String getCatPageSubcontent() {
            return catPageSubcontent;
        }

        public String getCatPageContent() {
            return catPageContent;
        }

        public String getHomePageContent() {
            return homePageContent;
        }

        public String getHomePageSubcontent() {
            return homePageSubcontent;
        }

        public Boolean getCommunityStatus() {
            return communityStatus;
        }

        public String getCreditsInfo() {
            return creditsInfo;
        }

        public Boolean getShowCreditsInfo() {
            return showCreditsInfo;
        }

        public String getCommunityName() {
            return communityName;
        }

        public String getCommunityArea() {
            return communityArea;
        }

        public String getUserid() {
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

        public Integer getPremiumUser() {
            return premiumUser;
        }

        public Integer getComid() {
            return comid;
        }

        public Integer getStatus() {
            return status;
        }

        public String getFlatNo() {
            return flatNo;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public String getFloorNo() {
            return floorNo;
        }

        public String getWelcomeText() {
            return welcomeText;
        }

        public Integer getJoinStatus() {
            return joinStatus;
        }

        public String getMembersCount() {
            return membersCount;
        }

        public String getMembers() {
            return members;
        }

        public String getTotalCredits() {
            return totalCredits;
        }

        public String getCreditsText() {
            return creditsText;
        }

        public String getWelcomeNameTitle() {
            return welcomeNameTitle;
        }

        public String getWelcomeNameContent() {
            return welcomeNameContent;
        }

        public String getMinCartText() {
            return minCartText;
        }

        public String getMinCartValue() {
            return minCartValue;
        }

        public String getFreeDeliveryText() {
            return freeDeliveryText;
        }

        public String getFreeDeliveryValue() {
            return freeDeliveryValue;
        }

        public String getCodText() {
            return codText;
        }

        public String getCodValue() {
            return codValue;
        }
    }

    public class Userdetail {

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
        public String zendeskuserid;
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

        public String getZendeskuserid() {
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