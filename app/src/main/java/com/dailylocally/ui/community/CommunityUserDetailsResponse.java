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

    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public List<Result> getResult() {
        return result;
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
        @SerializedName("community_name")
        @Expose
        public String communityName;
        @SerializedName("area")
        @Expose
        public String communityArea;


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
}