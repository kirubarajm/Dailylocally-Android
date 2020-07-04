package com.dailylocally.ui.pronotion.bottom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SusiAravind on 04,February,2020
 */
public class PromotionResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
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

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }


    public class Result {

        @SerializedName("pid")
        @Expose
        private Integer pid;
        @SerializedName("promotion_name")
        @Expose
        private String promotionName;
        @SerializedName("promotion_type")
        @Expose
        private int promotionType;
        @SerializedName("promotion_url")
        @Expose
        private String promotionUrl;
        @SerializedName("active_status")
        @Expose
        private Integer activeStatus;
        @SerializedName("numberoftimes")
        @Expose
        private Integer numberoftimes;
        @SerializedName("expiry_date")
        @Expose
        private String expiryDate;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;
        @SerializedName("show_status")
        @Expose
        private Boolean showStatus;
        @SerializedName("full_screen")
        @Expose
        private Boolean fullScreen;

        public Integer getPid() {
            return pid;
        }

        public void setPid(Integer pid) {
            this.pid = pid;
        }

        public String getPromotionName() {
            return promotionName;
        }

        public void setPromotionName(String promotionName) {
            this.promotionName = promotionName;
        }

        public int getPromotionType() {
            return promotionType;
        }

        public void setPromotionType(int promotionType) {
            this.promotionType = promotionType;
        }

        public String getPromotionUrl() {
            return promotionUrl;
        }

        public void setPromotionUrl(String promotionUrl) {
            this.promotionUrl = promotionUrl;
        }

        public Integer getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(Integer activeStatus) {
            this.activeStatus = activeStatus;
        }

        public Integer getNumberoftimes() {
            return numberoftimes;
        }

        public void setNumberoftimes(Integer numberoftimes) {
            this.numberoftimes = numberoftimes;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Boolean getShowStatus() {
            return showStatus;
        }

        public void setShowStatus(Boolean showStatus) {
            this.showStatus = showStatus;
        }

        public Boolean getFullScreen() {
            return fullScreen;
        }

        public void setFullScreen(Boolean fullScreen) {
            this.fullScreen = fullScreen;
        }

    }

}
