package com.dailylocally.ui.coupons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CouponValidateResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("message")
    @Expose
    public Boolean message;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;


    public Boolean getMessage() {
        return message;
    }

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

        @SerializedName("cid")
        @Expose
        public Integer cid;
        @SerializedName("coupon_name")
        @Expose
        public String couponName;
        @SerializedName("active_status")
        @Expose
        public Integer activeStatus;
        @SerializedName("numberoftimes")
        @Expose
        public Integer numberoftimes;
        @SerializedName("maxdiscount")
        @Expose
        public Integer maxdiscount;
        @SerializedName("discount_percent")
        @Expose
        public Integer discountPercent;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("img_url")
        @Expose
        public String imgUrl;
        @SerializedName("expiry_date")
        @Expose
        public String expiryDate;
        @SerializedName("minprice_limit")
        @Expose
        public Integer minpriceLimit;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public Object updatedAt;


        public Integer getCid() {
            return cid;
        }

        public String getCouponName() {
            return couponName;
        }

        public Integer getActiveStatus() {
            return activeStatus;
        }

        public Integer getNumberoftimes() {
            return numberoftimes;
        }

        public Integer getMaxdiscount() {
            return maxdiscount;
        }

        public Integer getDiscountPercent() {
            return discountPercent;
        }

        public String getDescription() {
            return description;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public Integer getMinpriceLimit() {
            return minpriceLimit;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }
    }
}
