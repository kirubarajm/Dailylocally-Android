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

@SerializedName("cid")
    @Expose
    private int cid;

@SerializedName("aid")
    @Expose
    private String aid;

@SerializedName("payment_type")
    @Expose
    private int payment_type;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public int getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

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

        @SerializedName("vpid")
        @Expose
        private String pid;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;


        @SerializedName("price")
        @Expose
        private String price;

        @SerializedName("dayorderdate")
        @Expose
        private String dayorderdate;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
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

        @SerializedName("vpid")
        @Expose
        private String pid;

        @SerializedName("pkt_size")
        @Expose
        private String pktSize;

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
        @SerializedName("mon")
        @Expose
        private Integer mon;

        @SerializedName("tue")
        @Expose
        private Integer tue;

        @SerializedName("wed")
        @Expose
        private Integer wed;

        @SerializedName("thur")
        @Expose
        private Integer thur;

        @SerializedName("fri")
        @Expose
        private Integer fri;

        @SerializedName("sat")
        @Expose
        private Integer sat;

        @SerializedName("sun")
        @Expose
        private Integer sun;

        public String getPktSize() {
            return pktSize;
        }

        public void setPktSize(String pktSize) {
            this.pktSize = pktSize;
        }

        public Integer getMon() {
            return mon;
        }

        public void setMon(Integer mon) {
            this.mon = mon;
        }

        public Integer getTue() {
            return tue;
        }

        public void setTue(Integer tue) {
            this.tue = tue;
        }

        public Integer getWed() {
            return wed;
        }

        public void setWed(Integer wed) {
            this.wed = wed;
        }

        public Integer getThur() {
            return thur;
        }

        public void setThur(Integer thur) {
            this.thur = thur;
        }

        public Integer getFri() {
            return fri;
        }

        public void setFri(Integer fri) {
            this.fri = fri;
        }

        public Integer getSat() {
            return sat;
        }

        public void setSat(Integer sat) {
            this.sat = sat;
        }

        public Integer getSun() {
            return sun;
        }

        public void setSun(Integer sun) {
            this.sun = sun;
        }

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

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
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
