package com.dailylocally.ui.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartRequest {

    @SerializedName("orderitems")
    @Expose
    private List<Orderitem> orderitems = null;
    @SerializedName("subscription")
    @Expose
    private List<Subscription> subscription = null;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("userid")
    @Expose
    private String userid;

    public List<Orderitem> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<Orderitem> orderitems) {
        this.orderitems = orderitems;
    }

    public List<Subscription> getSubscription() {
        return subscription;
    }

    public void setSubscription(List<Subscription> subscription) {
        this.subscription = subscription;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public static class Orderitem {

        @SerializedName("pid")
        @Expose
        private Integer pid;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;

        @SerializedName("price")
        @Expose
        private String price;

        @SerializedName("dayorderdate")
        @Expose
        private String dayorderdate;

        public Integer getPid() {
            return pid;
        }

        public void setPid(Integer pid) {
            this.pid = pid;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDayorderdate() {
            return dayorderdate;
        }

        public void setDayorderdate(String dayorderdate) {
            this.dayorderdate = dayorderdate;
        }

    }


    public static class Subscription {

        @SerializedName("pid")
        @Expose
        private Integer pid;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;

        @SerializedName("price")
        @Expose
        private String price;

        @SerializedName("start_date")
        @Expose
        private String startDate;

        @SerializedName("planid")
        @Expose
        private Integer planid;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public Integer getPid() {
            return pid;
        }

        public void setPid(Integer pid) {
            this.pid = pid;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getPlanid() {
            return planid;
        }

        public void setPlanid(Integer planid) {
            this.planid = planid;
        }

    }
}
