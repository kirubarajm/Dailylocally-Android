package com.dailylocally.ui.joinCommunity.contactWhatsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactWhatsAppResponse {

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

        @SerializedName("imageUrl")
        @Expose
        public String imageUrl;
        @SerializedName("phoneno")
        @Expose
        public String phoneno;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("subtitle1")
        @Expose
        public String subtitle1;
        @SerializedName("subtitle2")
        @Expose
        public String subtitle2;
        @SerializedName("whats_up_link")
        @Expose
        public String whatsUpLink;


        public String getImageUrl() {
            return imageUrl;
        }

        public String getPhoneno() {
            return phoneno;
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle1() {
            return subtitle1;
        }

        public String getSubtitle2() {
            return subtitle2;
        }

        public String getWhatsUpLink() {
            return whatsUpLink;
        }
    }
}
