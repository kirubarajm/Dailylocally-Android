package com.dailylocally.ui.transactionHistory.view;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionViewRequest {

    @SerializedName("orderid")
    @Expose
    public String orderid;


    public TransactionViewRequest(String orderid) {
        this.orderid = orderid;
    }
}
