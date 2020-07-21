package com.dailylocally.ui.subscription;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubscriptionTotalAmountResponse {
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

        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("pkts")
        @Expose
        private String pkts;
        @SerializedName("packet_info")
        @Expose
        private String packetInfo;
        @SerializedName("packet_total_info")
        @Expose
        private String packetTotalInfo;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPkts() {
            return pkts;
        }

        public void setPkts(String pkts) {
            this.pkts = pkts;
        }

        public String getPacketInfo() {
            return packetInfo;
        }

        public void setPacketInfo(String packetInfo) {
            this.packetInfo = packetInfo;
        }

        public String getPacketTotalInfo() {
            return packetTotalInfo;
        }

        public void setPacketTotalInfo(String packetTotalInfo) {
            this.packetTotalInfo = packetTotalInfo;
        }
    }
}