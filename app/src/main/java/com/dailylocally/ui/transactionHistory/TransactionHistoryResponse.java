package com.dailylocally.ui.transactionHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionHistoryResponse {

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

        @SerializedName("orderid")
        @Expose
        public Integer orderid;
        @SerializedName("tsid")
        @Expose
        public String tsid;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("gst")
        @Expose
        public Integer gst;
        @SerializedName("payment_status")
        @Expose
        public Integer paymentStatus;
        @SerializedName("userid")
        @Expose
        public Integer userid;
        @SerializedName("aid")
        @Expose
        public Integer aid;
        @SerializedName("cus_lat")
        @Expose
        public Double cusLat;
        @SerializedName("cus_lon")
        @Expose
        public String cusLon;
        @SerializedName("cus_pincode")
        @Expose
        public String cusPincode;
        @SerializedName("landmark")
        @Expose
        public String landmark;
        @SerializedName("app_type")
        @Expose
        public String appType;
        @SerializedName("payment_type")
        @Expose
        public Integer paymentType;
        @SerializedName("transaction_status")
        @Expose
        public String transactionStatus;
        @SerializedName("transaction_time")
        @Expose
        public String transactionTime;
        @SerializedName("zoneid")
        @Expose
        public Integer zoneid;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("apartment_name")
        @Expose
        public String apartmentName;
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
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("address_type")
        @Expose
        public String addressType;
        @SerializedName("items")
        @Expose
        public String items;

        public String getItems() {
            return items;
        }

        public Integer getOrderid() {
            return orderid;
        }

        public String getTsid() {
            return tsid;
        }

        public String getPrice() {
            return price;
        }

        public Integer getGst() {
            return gst;
        }

        public Integer getPaymentStatus() {
            return paymentStatus;
        }

        public Integer getUserid() {
            return userid;
        }

        public Integer getAid() {
            return aid;
        }

        public Double getCusLat() {
            return cusLat;
        }

        public String getCusLon() {
            return cusLon;
        }

        public String getCusPincode() {
            return cusPincode;
        }

        public String getLandmark() {
            return landmark;
        }

        public String getAppType() {
            return appType;
        }

        public Integer getPaymentType() {
            return paymentType;
        }

        public String getTransactionStatus() {
            return transactionStatus;
        }

        public String getTransactionTime() {
            return transactionTime;
        }

        public Integer getZoneid() {
            return zoneid;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
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

        public String getCity() {
            return city;
        }

        public String getAddressType() {
            return addressType;
        }
    }
}
