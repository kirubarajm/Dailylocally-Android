package com.dailylocally.ui.subscription;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubscriptionResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("subscription_plan")
    @Expose
    private List<SubscriptionPlan> subscriptionPlan = null;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<SubscriptionPlan> getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(List<SubscriptionPlan> subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }


    public class Result {

        @SerializedName("vpid")
        @Expose
        private String pid;
        @SerializedName("hsn_code")
        @Expose
        private Object hsnCode;
        @SerializedName("Productname")
        @Expose
        private String productname;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("brand")
        @Expose
        private Integer brand;
        @SerializedName("mrp")
        @Expose
        private String mrp;
        @SerializedName("basiccost")
        @Expose
        private Object basiccost;
        @SerializedName("discount_cost")
        @Expose
        private Integer discountCost;
        @SerializedName("gst")
        @Expose
        private Integer gst;
        @SerializedName("scl1_id")
        @Expose
        private Integer scl1Id;
        @SerializedName("scl2_id")
        @Expose
        private Integer scl2Id;
        @SerializedName("subscription")
        @Expose
        private Integer subscription;
        @SerializedName("weight")
        @Expose
        private String weight;
        @SerializedName("unit")
        @Expose
        private String unit;
        @SerializedName("packettype")
        @Expose
        private Object packettype;
        @SerializedName("packetsize")
        @Expose
        private Object packetsize;
        @SerializedName("vegtype")
        @Expose
        private Integer vegtype;
        @SerializedName("tag")
        @Expose
        private Integer tag;
        @SerializedName("short_desc")
        @Expose
        private Object shortDesc;
        @SerializedName("shelf_life")
        @Expose
        private Object shelfLife;
        @SerializedName("active_status")
        @Expose
        private Integer activeStatus;
        @SerializedName("live_status")
        @Expose
        private String liveStatus;
        @SerializedName("availablity")
        @Expose
        private Boolean availablity;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public Object getHsnCode() {
            return hsnCode;
        }

        public void setHsnCode(Object hsnCode) {
            this.hsnCode = hsnCode;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getBrand() {
            return brand;
        }

        public void setBrand(Integer brand) {
            this.brand = brand;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public Object getBasiccost() {
            return basiccost;
        }

        public void setBasiccost(Object basiccost) {
            this.basiccost = basiccost;
        }

        public Integer getDiscountCost() {
            return discountCost;
        }

        public void setDiscountCost(Integer discountCost) {
            this.discountCost = discountCost;
        }

        public Integer getGst() {
            return gst;
        }

        public void setGst(Integer gst) {
            this.gst = gst;
        }

        public Integer getScl1Id() {
            return scl1Id;
        }

        public void setScl1Id(Integer scl1Id) {
            this.scl1Id = scl1Id;
        }

        public Integer getScl2Id() {
            return scl2Id;
        }

        public void setScl2Id(Integer scl2Id) {
            this.scl2Id = scl2Id;
        }

        public Integer getSubscription() {
            return subscription;
        }

        public void setSubscription(Integer subscription) {
            this.subscription = subscription;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public Object getPackettype() {
            return packettype;
        }

        public void setPackettype(Object packettype) {
            this.packettype = packettype;
        }

        public Object getPacketsize() {
            return packetsize;
        }

        public void setPacketsize(Object packetsize) {
            this.packetsize = packetsize;
        }

        public Integer getVegtype() {
            return vegtype;
        }

        public void setVegtype(Integer vegtype) {
            this.vegtype = vegtype;
        }

        public Integer getTag() {
            return tag;
        }

        public void setTag(Integer tag) {
            this.tag = tag;
        }

        public Object getShortDesc() {
            return shortDesc;
        }

        public void setShortDesc(Object shortDesc) {
            this.shortDesc = shortDesc;
        }

        public Object getShelfLife() {
            return shelfLife;
        }

        public void setShelfLife(Object shelfLife) {
            this.shelfLife = shelfLife;
        }

        public Integer getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(Integer activeStatus) {
            this.activeStatus = activeStatus;
        }

        public String getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(String liveStatus) {
            this.liveStatus = liveStatus;
        }

        public Boolean getAvailablity() {
            return availablity;
        }

        public void setAvailablity(Boolean availablity) {
            this.availablity = availablity;
        }

    }

    public class SubscriptionPlan {

        @SerializedName("spid")
        @Expose
        private Integer spid;
        @SerializedName("plan_name")
        @Expose
        private String planName;
        @SerializedName("active_status")
        @Expose
        private Integer activeStatus;
        @SerializedName("numberofdays")
        @Expose
        private Integer numberofdays;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public Integer getSpid() {
            return spid;
        }

        public void setSpid(Integer spid) {
            this.spid = spid;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public Integer getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(Integer activeStatus) {
            this.activeStatus = activeStatus;
        }

        public Integer getNumberofdays() {
            return numberofdays;
        }

        public void setNumberofdays(Integer numberofdays) {
            this.numberofdays = numberofdays;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }
}