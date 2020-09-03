package com.dailylocally.ui.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("product_cost_limit_status")
    @Expose
    private Boolean productCostLimitStatus;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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


    public Boolean getProductCostLimitStatus() {
        return productCostLimitStatus;
    }

    public void setProductCostLimitStatus(Boolean productCostLimitStatus) {
        this.productCostLimitStatus = productCostLimitStatus;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Amountdetails {

        @SerializedName("grandtotaltitle")
        @Expose
        private String grandtotaltitle;
        @SerializedName("grandtotal")
        @Expose
        private Integer grandtotal;
        @SerializedName("gstcharge")
        @Expose
        private Integer gstcharge;
        @SerializedName("product_total_weight")
        @Expose
        private Object productTotalWeight;
        @SerializedName("delivery_charge")
        @Expose
        private String deliveryCharge;
        @SerializedName("refund_coupon_adjustment")
        @Expose
        private Integer refundCouponAdjustment;
        @SerializedName("product_orginal_price")
        @Expose
        private Integer productOrginalPrice;
        @SerializedName("totalamount")
        @Expose
        private Integer totalamount;
        @SerializedName("coupon_discount_amount")
        @Expose
        private Integer couponDiscountAmount;
        @SerializedName("couponstatus")
        @Expose
        private Boolean couponstatus;
        @SerializedName("product_cost_limit_status")
        @Expose
        private Boolean productCostLimitStatus;
        @SerializedName("show_delivery_text")
        @Expose
        private Boolean showDeliverText;
        @SerializedName("delivery_text")
        @Expose
        private String deliveryText;
         @SerializedName("exclusive_tag")
        @Expose
        private String exclusiveTag;

        @SerializedName("product_cost_limit_message")
        @Expose
        private String productCostLimitMessage;
        @SerializedName("product_cost_limit_short_message")
        @Expose
        private String productCostLimitShortMessage;
        @SerializedName("order_delivery_day_message")
        @Expose
        private String orderDeliveryDayMessage;
        @SerializedName("order_delivery_day")
        @Expose
        private String orderDeliveryDay;
        @SerializedName("minimum_cart_value")
        @Expose
        private Integer minimumCartValue;


        public Boolean getShowDeliverText() {
            return showDeliverText;
        }

        public String getDeliveryText() {
            return deliveryText;
        }

        public String getExclusiveTag() {
            return exclusiveTag;
        }

        public String getGrandtotaltitle() {
            return grandtotaltitle;
        }

        public void setGrandtotaltitle(String grandtotaltitle) {
            this.grandtotaltitle = grandtotaltitle;
        }

        public Integer getGrandtotal() {
            return grandtotal;
        }

        public void setGrandtotal(Integer grandtotal) {
            this.grandtotal = grandtotal;
        }

        public Integer getGstcharge() {
            return gstcharge;
        }

        public void setGstcharge(Integer gstcharge) {
            this.gstcharge = gstcharge;
        }

        public Object getProductTotalWeight() {
            return productTotalWeight;
        }

        public void setProductTotalWeight(Object productTotalWeight) {
            this.productTotalWeight = productTotalWeight;
        }

        public String getDeliveryCharge() {
            return deliveryCharge;
        }

        public void setDeliveryCharge(String deliveryCharge) {
            this.deliveryCharge = deliveryCharge;
        }

        public Integer getRefundCouponAdjustment() {
            return refundCouponAdjustment;
        }

        public void setRefundCouponAdjustment(Integer refundCouponAdjustment) {
            this.refundCouponAdjustment = refundCouponAdjustment;
        }

        public Integer getProductOrginalPrice() {
            return productOrginalPrice;
        }

        public void setProductOrginalPrice(Integer productOrginalPrice) {
            this.productOrginalPrice = productOrginalPrice;
        }

        public Integer getTotalamount() {
            return totalamount;
        }

        public void setTotalamount(Integer totalamount) {
            this.totalamount = totalamount;
        }

        public Integer getCouponDiscountAmount() {
            return couponDiscountAmount;
        }

        public void setCouponDiscountAmount(Integer couponDiscountAmount) {
            this.couponDiscountAmount = couponDiscountAmount;
        }

        public Boolean getCouponstatus() {
            return couponstatus;
        }

        public void setCouponstatus(Boolean couponstatus) {
            this.couponstatus = couponstatus;
        }

        public Boolean getProductCostLimitStatus() {
            return productCostLimitStatus;
        }

        public void setProductCostLimitStatus(Boolean productCostLimitStatus) {
            this.productCostLimitStatus = productCostLimitStatus;
        }

        public String getProductCostLimitMessage() {
            return productCostLimitMessage;
        }

        public void setProductCostLimitMessage(String productCostLimitMessage) {
            this.productCostLimitMessage = productCostLimitMessage;
        }

        public String getProductCostLimitShortMessage() {
            return productCostLimitShortMessage;
        }

        public void setProductCostLimitShortMessage(String productCostLimitShortMessage) {
            this.productCostLimitShortMessage = productCostLimitShortMessage;
        }

        public String getOrderDeliveryDayMessage() {
            return orderDeliveryDayMessage;
        }

        public void setOrderDeliveryDayMessage(String orderDeliveryDayMessage) {
            this.orderDeliveryDayMessage = orderDeliveryDayMessage;
        }

        public String getOrderDeliveryDay() {
            return orderDeliveryDay;
        }

        public void setOrderDeliveryDay(String orderDeliveryDay) {
            this.orderDeliveryDay = orderDeliveryDay;
        }

        public Integer getMinimumCartValue() {
            return minimumCartValue;
        }

        public void setMinimumCartValue(Integer minimumCartValue) {
            this.minimumCartValue = minimumCartValue;
        }

    }

    public class Cartdetail {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("charges")
        @Expose
        private Integer charges;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("infostatus")
        @Expose
        private Boolean infostatus;
        @SerializedName("color_code")
        @Expose
        private String colorCode;
        @SerializedName("low_cost_status")
        @Expose
        private Boolean lowCostStatus;
        @SerializedName("low_cost_note")
        @Expose
        private String lowCostNote;
        @SerializedName("default_cost")
        @Expose
        private Integer defaultCost;
        @SerializedName("default_cost_status")
        @Expose
        private Boolean defaultCostStatus;
        @SerializedName("infodetails")
        @Expose
        private List<Infodetail> infodetails = null;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getCharges() {
            return charges;
        }

        public void setCharges(Integer charges) {
            this.charges = charges;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public Boolean getInfostatus() {
            return infostatus;
        }

        public void setInfostatus(Boolean infostatus) {
            this.infostatus = infostatus;
        }

        public String getColorCode() {
            return colorCode;
        }

        public void setColorCode(String colorCode) {
            this.colorCode = colorCode;
        }

        public Boolean getLowCostStatus() {
            return lowCostStatus;
        }

        public void setLowCostStatus(Boolean lowCostStatus) {
            this.lowCostStatus = lowCostStatus;
        }

        public String getLowCostNote() {
            return lowCostNote;
        }

        public void setLowCostNote(String lowCostNote) {
            this.lowCostNote = lowCostNote;
        }

        public Integer getDefaultCost() {
            return defaultCost;
        }

        public void setDefaultCost(Integer defaultCost) {
            this.defaultCost = defaultCost;
        }

        public Boolean getDefaultCostStatus() {
            return defaultCostStatus;
        }

        public void setDefaultCostStatus(Boolean defaultCostStatus) {
            this.defaultCostStatus = defaultCostStatus;
        }

        public List<Infodetail> getInfodetails() {
            return infodetails;
        }

        public void setInfodetails(List<Infodetail> infodetails) {
            this.infodetails = infodetails;
        }

    }

    public class Infodetail {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("price")
        @Expose
        private Integer price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

    }

    public class Item {

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
        private String discountCost;
        @SerializedName("gst")
        @Expose
        private String gst;
        @SerializedName("scl1_id")
        @Expose
        private String scl1Id;
        @SerializedName("scl2_id")
        @Expose
        private String scl2Id;
        @SerializedName("subscription")
        @Expose
        private Integer subscription;
        @SerializedName("weight")
        @Expose
        private String weight;
        @SerializedName("packettype")
        @Expose
        private String packettype;
        @SerializedName("packetsize")
        @Expose
        private String packetsize;
        @SerializedName("vegtype")
        @Expose
        private String vegtype;
        @SerializedName("tag")
        @Expose
        private String tag;
        @SerializedName("short_desc")
        @Expose
        private String shortDesc;
        @SerializedName("shelf_life")
        @Expose
        private String shelfLife;
        @SerializedName("active_status")
        @Expose
        private String activeStatus;
        @SerializedName("live_status")
        @Expose
        private String liveStatus;
        @SerializedName("availablity")
        @Expose
        private Boolean availablity;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("unit")
        @Expose
        private String unit;

        @SerializedName("cartquantity")
        @Expose
        private Integer cartquantity;
        @SerializedName("product_weight")
        @Expose
        private Object productWeight;
        @SerializedName("product_discount_price")
        @Expose
        private String productDiscountPrice;
        @SerializedName("no_of_deliveries")
        @Expose
        private String noOfDeliveries;
        @SerializedName("deliverydate")
        @Expose
        private String deliverydate;
        @SerializedName("starting_date")
        @Expose
        private String starting_date;


        public String getStarting_date() {
            return starting_date;
        }

        public void setStarting_date(String starting_date) {
            this.starting_date = starting_date;
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

        public String getDiscountCost() {
            return discountCost;
        }

        public void setDiscountCost(String discountCost) {
            this.discountCost = discountCost;
        }

        public String getGst() {
            return gst;
        }

        public void setGst(String gst) {
            this.gst = gst;
        }

        public String getScl1Id() {
            return scl1Id;
        }

        public void setScl1Id(String scl1Id) {
            this.scl1Id = scl1Id;
        }

        public String getScl2Id() {
            return scl2Id;
        }

        public void setScl2Id(String scl2Id) {
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

        public String getPackettype() {
            return packettype;
        }

        public void setPackettype(String packettype) {
            this.packettype = packettype;
        }

        public String getPacketsize() {
            return packetsize;
        }

        public void setPacketsize(String packetsize) {
            this.packetsize = packetsize;
        }

        public String getVegtype() {
            return vegtype;
        }

        public void setVegtype(String vegtype) {
            this.vegtype = vegtype;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getShortDesc() {
            return shortDesc;
        }

        public void setShortDesc(String shortDesc) {
            this.shortDesc = shortDesc;
        }

        public String getShelfLife() {
            return shelfLife;
        }

        public void setShelfLife(String shelfLife) {
            this.shelfLife = shelfLife;
        }

        public String getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(String activeStatus) {
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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public Integer getCartquantity() {
            return cartquantity;
        }

        public void setCartquantity(Integer cartquantity) {
            this.cartquantity = cartquantity;
        }

        public Object getProductWeight() {
            return productWeight;
        }

        public void setProductWeight(Object productWeight) {
            this.productWeight = productWeight;
        }

        public String getProductDiscountPrice() {
            return productDiscountPrice;
        }

        public void setProductDiscountPrice(String productDiscountPrice) {
            this.productDiscountPrice = productDiscountPrice;
        }

        public String getNoOfDeliveries() {
            return noOfDeliveries;
        }

        public void setNoOfDeliveries(String noOfDeliveries) {
            this.noOfDeliveries = noOfDeliveries;
        }

        public String getDeliverydate() {
            return deliverydate;
        }

        public void setDeliverydate(String deliverydate) {
            this.deliverydate = deliverydate;
        }
    }

    public class Result {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("Zonename")
        @Expose
        private String zonename;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("boundaries")
        @Expose
        private String boundaries;
        @SerializedName("zone_status")
        @Expose
        private Integer zoneStatus;
        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lon")
        @Expose
        private Double lon;
        @SerializedName("distance")
        @Expose
        private Double distance;
        @SerializedName("isAvaliablekitchen")
        @Expose
        private Boolean isAvaliablekitchen;
        @SerializedName("isAvaliablezone")
        @Expose
        private Boolean isAvaliablezone;
        @SerializedName("community_user_status")
        @Expose
        private Boolean communityUser;
        @SerializedName("amountdetails")
        @Expose
        private Amountdetails amountdetails;
        @SerializedName("item")
        @Expose
        private List<Item> item = null;
        @SerializedName("subscription_item")
        @Expose
        private List<SubscriptionItem> subscriptionItem = null;
        @SerializedName("ordercount")
        @Expose
        private Integer ordercount;
        @SerializedName("cartdetails")
        @Expose
        private List<Cartdetail> cartdetails = null;
        @SerializedName("minimum_cart_value")
        @Expose
        private Integer minimumCartValue;

        public Boolean getAvaliablekitchen() {
            return isAvaliablekitchen;
        }

        public Boolean getAvaliablezone() {
            return isAvaliablezone;
        }

        public Boolean getCommunityUser() {
            return communityUser;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getZonename() {
            return zonename;
        }

        public void setZonename(String zonename) {
            this.zonename = zonename;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getBoundaries() {
            return boundaries;
        }

        public void setBoundaries(String boundaries) {
            this.boundaries = boundaries;
        }

        public Integer getZoneStatus() {
            return zoneStatus;
        }

        public void setZoneStatus(Integer zoneStatus) {
            this.zoneStatus = zoneStatus;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public Boolean getIsAvaliablekitchen() {
            return isAvaliablekitchen;
        }

        public void setIsAvaliablekitchen(Boolean isAvaliablekitchen) {
            this.isAvaliablekitchen = isAvaliablekitchen;
        }

        public Boolean getIsAvaliablezone() {
            return isAvaliablezone;
        }

        public void setIsAvaliablezone(Boolean isAvaliablezone) {
            this.isAvaliablezone = isAvaliablezone;
        }

        public Amountdetails getAmountdetails() {
            return amountdetails;
        }

        public void setAmountdetails(Amountdetails amountdetails) {
            this.amountdetails = amountdetails;
        }

        public List<Item> getItem() {
            return item;
        }

        public void setItem(List<Item> item) {
            this.item = item;
        }

        public List<SubscriptionItem> getSubscriptionItem() {
            return subscriptionItem;
        }

        public void setSubscriptionItem(List<SubscriptionItem> subscriptionItem) {
            this.subscriptionItem = subscriptionItem;
        }

        public Integer getOrdercount() {
            return ordercount;
        }

        public void setOrdercount(Integer ordercount) {
            this.ordercount = ordercount;
        }

        public List<Cartdetail> getCartdetails() {
            return cartdetails;
        }

        public void setCartdetails(List<Cartdetail> cartdetails) {
            this.cartdetails = cartdetails;
        }

        public Integer getMinimumCartValue() {
            return minimumCartValue;
        }

        public void setMinimumCartValue(Integer minimumCartValue) {
            this.minimumCartValue = minimumCartValue;
        }

    }


    public class SubscriptionItem {

        @SerializedName("vpid")
        @Expose
        private String pid;
        @SerializedName("hsn_code")
        @Expose
        private String hsnCode;
        @SerializedName("Productname")
        @Expose
        private String productname;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("mrp")
        @Expose
        private String mrp;
        @SerializedName("unit")
        @Expose
        private String unit;
        @SerializedName("basiccost")
        @Expose
        private String basiccost;
        @SerializedName("discount_cost")
        @Expose
        private String discountCost;
        @SerializedName("gst")
        @Expose
        private String gst;
        @SerializedName("scl1_id")
        @Expose
        private Integer scl1Id;
        @SerializedName("scl2_id")
        @Expose
        private String scl2Id;
        @SerializedName("subscription")
        @Expose
        private Integer subscription;
        @SerializedName("weight")
        @Expose
        private String weight;
        @SerializedName("packettype")
        @Expose
        private String packettype;
        @SerializedName("packetsize")
        @Expose
        private String packetsize;
        @SerializedName("vegtype")
        @Expose
        private String vegtype;
        @SerializedName("tag")
        @Expose
        private String tag;
        @SerializedName("short_desc")
        @Expose
        private String shortDesc;
        @SerializedName("shelf_life")
        @Expose
        private String shelfLife;
        @SerializedName("active_status")
        @Expose
        private String activeStatus;
        @SerializedName("live_status")
        @Expose
        private String liveStatus;
        @SerializedName("availablity")
        @Expose
        private Boolean availablity;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("cartquantity")
        @Expose
        private String cartquantity;
        @SerializedName("product_weight")
        @Expose
        private String productWeight;
        @SerializedName("product_discount_price")
        @Expose
        private String productDiscountPrice;
        @SerializedName("no_of_deliveries")
        @Expose
        private String noOfDeliveries;
        @SerializedName("deliverydate")
        @Expose
        private String deliverydate; @SerializedName("starting_date")
        @Expose
        private String startingDate;
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

        @SerializedName("pkts")
        @Expose
        private String pkts;
        @SerializedName("packet_info")
        @Expose
        private String packetInfo;

        @SerializedName("packet_total_info")
        @Expose
        private String packetTotalInfo;

        public String getStartingDate() {
            return startingDate;
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

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getHsnCode() {
            return hsnCode;
        }

        public void setHsnCode(String hsnCode) {
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

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getBasiccost() {
            return basiccost;
        }

        public void setBasiccost(String basiccost) {
            this.basiccost = basiccost;
        }

        public String getDiscountCost() {
            return discountCost;
        }

        public void setDiscountCost(String discountCost) {
            this.discountCost = discountCost;
        }

        public String getGst() {
            return gst;
        }

        public void setGst(String gst) {
            this.gst = gst;
        }

        public Integer getScl1Id() {
            return scl1Id;
        }

        public void setScl1Id(Integer scl1Id) {
            this.scl1Id = scl1Id;
        }

        public String getScl2Id() {
            return scl2Id;
        }

        public void setScl2Id(String scl2Id) {
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

        public String getPackettype() {
            return packettype;
        }

        public void setPackettype(String packettype) {
            this.packettype = packettype;
        }

        public String getPacketsize() {
            return packetsize;
        }

        public void setPacketsize(String packetsize) {
            this.packetsize = packetsize;
        }

        public String getVegtype() {
            return vegtype;
        }

        public void setVegtype(String vegtype) {
            this.vegtype = vegtype;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getShortDesc() {
            return shortDesc;
        }

        public void setShortDesc(String shortDesc) {
            this.shortDesc = shortDesc;
        }

        public String getShelfLife() {
            return shelfLife;
        }

        public void setShelfLife(String shelfLife) {
            this.shelfLife = shelfLife;
        }

        public String getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(String activeStatus) {
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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCartquantity() {
            return cartquantity;
        }

        public void setCartquantity(String cartquantity) {
            this.cartquantity = cartquantity;
        }

        public String getProductWeight() {
            return productWeight;
        }

        public void setProductWeight(String productWeight) {
            this.productWeight = productWeight;
        }

        public String getProductDiscountPrice() {
            return productDiscountPrice;
        }

        public void setProductDiscountPrice(String productDiscountPrice) {
            this.productDiscountPrice = productDiscountPrice;
        }

        public String getNoOfDeliveries() {
            return noOfDeliveries;
        }

        public void setNoOfDeliveries(String noOfDeliveries) {
            this.noOfDeliveries = noOfDeliveries;
        }

        public String getDeliverydate() {
            return deliverydate;
        }

        public void setDeliverydate(String deliverydate) {
            this.deliverydate = deliverydate;
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
    }
}
