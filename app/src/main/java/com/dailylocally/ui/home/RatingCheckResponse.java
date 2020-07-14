package com.dailylocally.ui.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RatingCheckResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("rating_status")
    @Expose
    private Boolean ratingStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getRatingStatus() {
        return ratingStatus;
    }

    public void setRatingStatus(Boolean ratingStatus) {
        this.ratingStatus = ratingStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }


    public class Result {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("rating_skip")
        @Expose
        private String ratingSkip;

        @SerializedName("deliver_date")
        @Expose
        private String deliverDate;

        @SerializedName("order_place_time")
        @Expose
        private String orderPlaceTime;
        @SerializedName("rating_skip_available")
        @Expose
        private Integer ratingSkipAvailable;

        public String getId() {
            return id;
        }

        public String getDate() {
            return date;
        }

        public String getRatingSkip() {
            return ratingSkip;
        }

        public String getDeliverDate() {
            return deliverDate;
        }

        public String getOrderPlaceTime() {
            return orderPlaceTime;
        }

        public Integer getRatingSkipAvailable() {
            return ratingSkipAvailable;
        }
    }

}
