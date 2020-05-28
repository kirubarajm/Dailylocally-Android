package com.dailylocally.ui.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderCreateResponse {

@SerializedName("success")
@Expose
private Boolean success;
@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("orderid")
@Expose
private String orderid;
@SerializedName("price")
@Expose
private String price;
@SerializedName("razer_customerid")
@Expose
private String razerCustomerid;


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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRazerCustomerid() {
        return razerCustomerid;
    }

    public void setRazerCustomerid(String razerCustomerid) {
        this.razerCustomerid = razerCustomerid;
    }
}