package com.dailylocally.ui.account.referrals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferralsResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("referalcode")
        @Expose
        public String referalcode;
        @SerializedName("applink")
        @Expose
        public String applink;


        public String getReferalcode() {
            return referalcode;
        }

        public void setReferalcode(String referalcode) {
            this.referalcode = referalcode;
        }

        public String getApplink() {
            return applink;
        }

        public void setApplink(String applink) {
            this.applink = applink;
        }
    }


}
