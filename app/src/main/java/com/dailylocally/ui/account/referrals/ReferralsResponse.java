package com.dailylocally.ui.account.referrals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferralsResponse {

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

        @SerializedName("referalcode")
        @Expose
        public String referalcode;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("sub_title")
        @Expose
        public String subTitle;


        public String getReferalcode() {
            return referalcode;
        }

        public String getMessage() {
            return message;
        }

        public String getTitle() {
            return title;
        }

        public String getSubTitle() {
            return subTitle;
        }
    }
}
