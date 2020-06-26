package com.dailylocally.ui.transactionHistory.view;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionViewResponse {

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

        @SerializedName("userid")
        @Expose
        public Integer userid;
        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("dayorderstatus")
        @Expose
        public Integer dayorderstatus;
        @SerializedName("items")
        @Expose
        public List<Item> items = null;


        public Integer getUserid() {
            return userid;
        }

        public String getDate() {
            return date;
        }

        public Integer getDayorderstatus() {
            return dayorderstatus;
        }

        public List<Item> getItems() {
            return items;
        }

        public class Item {

            @SerializedName("vpid")
            @Expose
            public Integer vpid;
            @SerializedName("price")
            @Expose
            public Integer price;
            @SerializedName("quantity")
            @Expose
            public Integer quantity;
            @SerializedName("product_name")
            @Expose
            public String productName;


            public Integer getVpid() {
                return vpid;
            }

            public Integer getPrice() {
                return price;
            }

            public Integer getQuantity() {
                return quantity;
            }

            public String getProductName() {
                return productName;
            }
        }
    }
}
