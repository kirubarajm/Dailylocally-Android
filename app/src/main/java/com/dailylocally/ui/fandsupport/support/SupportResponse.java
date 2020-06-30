package com.dailylocally.ui.fandsupport.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupportResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("serviceablestatus")
    @Expose
    public Boolean serviceablestatus;
    @SerializedName("unserviceable_title")
    @Expose
    public String unserviceableTitle;
    @SerializedName("unserviceable_subtitle")
    @Expose
    public String unserviceableSubtitle;
    @SerializedName("empty_url")
    @Expose
    public String emptyUrl;
    @SerializedName("empty_content")
    @Expose
    public String emptyContent;
    @SerializedName("empty_subconent")
    @Expose
    public String emptySubconent;
    @SerializedName("header_content")
    @Expose
    public String headerContent;
    @SerializedName("header_subconent")
    @Expose
    public String headerSubconent;
    @SerializedName("category_title")
    @Expose
    public String categoryTitle;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;


    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public Boolean getServiceablestatus() {
        return serviceablestatus;
    }

    public String getUnserviceableTitle() {
        return unserviceableTitle;
    }

    public String getUnserviceableSubtitle() {
        return unserviceableSubtitle;
    }

    public String getEmptyUrl() {
        return emptyUrl;
    }

    public String getEmptyContent() {
        return emptyContent;
    }

    public String getEmptySubconent() {
        return emptySubconent;
    }

    public String getHeaderContent() {
        return headerContent;
    }

    public String getHeaderSubconent() {
        return headerSubconent;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public List<Result> getResult() {
        return result;
    }

    public class Result {

        @SerializedName("pid")
        @Expose
        public Integer pid;
        @SerializedName("hsn_code")
        @Expose
        public Object hsnCode;
        @SerializedName("Productname")
        @Expose
        public String productname;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("brand")
        @Expose
        public Integer brand;
        @SerializedName("mrp")
        @Expose
        public Integer mrp;
        @SerializedName("basiccost")
        @Expose
        public Object basiccost;
        @SerializedName("targetedbaseprice")
        @Expose
        public Object targetedbaseprice;
        @SerializedName("discount_cost")
        @Expose
        public Integer discountCost;
        @SerializedName("gst")
        @Expose
        public Integer gst;
        @SerializedName("scl1_id")
        @Expose
        public Integer scl1Id;
        @SerializedName("scl2_id")
        @Expose
        public Integer scl2Id;
        @SerializedName("subscription")
        @Expose
        public Integer subscription;
        @SerializedName("weight")
        @Expose
        public Integer weight;
        @SerializedName("uom")
        @Expose
        public Integer uom;
        @SerializedName("packetsize")
        @Expose
        public Object packetsize;
        @SerializedName("vegtype")
        @Expose
        public Integer vegtype;
        @SerializedName("tag")
        @Expose
        public Integer tag;
        @SerializedName("short_desc")
        @Expose
        public String shortDesc;
        @SerializedName("productdetails")
        @Expose
        public Object productdetails;
        @SerializedName("Perishable")
        @Expose
        public Object perishable;
        @SerializedName("shelf_life")
        @Expose
        public Object shelfLife;
        @SerializedName("active_status")
        @Expose
        public Integer activeStatus;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public Object updatedAt;
        @SerializedName("vpid")
        @Expose
        public String vpid;
        @SerializedName("zoneid")
        @Expose
        public String zoneid;
        @SerializedName("live_status")
        @Expose
        public String liveStatus;
        @SerializedName("favid")
        @Expose
        public Object favid;
        @SerializedName("isfav")
        @Expose
        public String isfav;
        @SerializedName("unit")
        @Expose
        public String unit;
        @SerializedName("brandname")
        @Expose
        public String brandname;
        @SerializedName("servicable_status")
        @Expose
        public Boolean servicableStatus;
        @SerializedName("offer")
        @Expose
        public String offer;
        @SerializedName("discount_cost_status")
        @Expose
        public Boolean discountCostStatus;


        public Integer getPid() {
            return pid;
        }

        public Object getHsnCode() {
            return hsnCode;
        }

        public String getProductname() {
            return productname;
        }

        public String getImage() {
            return image;
        }

        public Integer getBrand() {
            return brand;
        }

        public Integer getMrp() {
            return mrp;
        }

        public Object getBasiccost() {
            return basiccost;
        }

        public Object getTargetedbaseprice() {
            return targetedbaseprice;
        }

        public Integer getDiscountCost() {
            return discountCost;
        }

        public Integer getGst() {
            return gst;
        }

        public Integer getScl1Id() {
            return scl1Id;
        }

        public Integer getScl2Id() {
            return scl2Id;
        }

        public Integer getSubscription() {
            return subscription;
        }

        public Integer getWeight() {
            return weight;
        }

        public Integer getUom() {
            return uom;
        }

        public Object getPacketsize() {
            return packetsize;
        }

        public Integer getVegtype() {
            return vegtype;
        }

        public Integer getTag() {
            return tag;
        }

        public String getShortDesc() {
            return shortDesc;
        }

        public Object getProductdetails() {
            return productdetails;
        }

        public Object getPerishable() {
            return perishable;
        }

        public Object getShelfLife() {
            return shelfLife;
        }

        public Integer getActiveStatus() {
            return activeStatus;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public String getVpid() {
            return vpid;
        }

        public String getZoneid() {
            return zoneid;
        }

        public String getLiveStatus() {
            return liveStatus;
        }

        public Object getFavid() {
            return favid;
        }

        public String getIsfav() {
            return isfav;
        }

        public String getUnit() {
            return unit;
        }

        public String getBrandname() {
            return brandname;
        }

        public Boolean getServicableStatus() {
            return servicableStatus;
        }

        public String getOffer() {
            return offer;
        }

        public Boolean getDiscountCostStatus() {
            return discountCostStatus;
        }
    }
}
