package com.dailylocally.ui.category.l2.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("serviceablestatus")
    @Expose
    private Boolean serviceablestatus;
    @SerializedName("unserviceable_title")
    @Expose
    private String unserviceableTitle;
    @SerializedName("unserviceable_subtitle")
    @Expose
    private String unserviceableSubtitle;
    @SerializedName("empty_url")
    @Expose
    private String emptyUrl;
    @SerializedName("empty_content")
    @Expose
    private String emptyContent;
    @SerializedName("empty_subconent")
    @Expose
    private String emptySubconent;
    @SerializedName("header_content")
    @Expose
    private String headerContent;
    @SerializedName("header_subconent")
    @Expose
    private String headerSubconent;
    @SerializedName("category_title")
    @Expose
    private String categoryTitle;
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

    public Boolean getServiceablestatus() {
        return serviceablestatus;
    }

    public void setServiceablestatus(Boolean serviceablestatus) {
        this.serviceablestatus = serviceablestatus;
    }

    public String getUnserviceableTitle() {
        return unserviceableTitle;
    }

    public void setUnserviceableTitle(String unserviceableTitle) {
        this.unserviceableTitle = unserviceableTitle;
    }

    public String getUnserviceableSubtitle() {
        return unserviceableSubtitle;
    }

    public void setUnserviceableSubtitle(String unserviceableSubtitle) {
        this.unserviceableSubtitle = unserviceableSubtitle;
    }

    public String getEmptyUrl() {
        return emptyUrl;
    }

    public void setEmptyUrl(String emptyUrl) {
        this.emptyUrl = emptyUrl;
    }

    public String getEmptyContent() {
        return emptyContent;
    }

    public void setEmptyContent(String emptyContent) {
        this.emptyContent = emptyContent;
    }

    public String getEmptySubconent() {
        return emptySubconent;
    }

    public void setEmptySubconent(String emptySubconent) {
        this.emptySubconent = emptySubconent;
    }

    public String getHeaderContent() {
        return headerContent;
    }

    public void setHeaderContent(String headerContent) {
        this.headerContent = headerContent;
    }

    public String getHeaderSubconent() {
        return headerSubconent;
    }

    public void setHeaderSubconent(String headerSubconent) {
        this.headerSubconent = headerSubconent;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
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
        private Integer pid;

        @SerializedName("Productname")
        @Expose
        private String productname;

        @SerializedName("image")
        @Expose
        private String image;

        @SerializedName("mrp")
        @Expose
        private String mrp;

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

        @SerializedName("tag")
        @Expose
        private Integer tag;

        @SerializedName("short_desc")
        @Expose
        private String shortDesc;

        @SerializedName("servicable_status")
        @Expose
        private Boolean servicableStatus;

        public Integer getPid() {
            return pid;
        }

        public void setPid(Integer pid) {
            this.pid = pid;
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

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
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

        public Integer getTag() {
            return tag;
        }

        public void setTag(Integer tag) {
            this.tag = tag;
        }

        public String getShortDesc() {
            return shortDesc;
        }

        public void setShortDesc(String shortDesc) {
            this.shortDesc = shortDesc;
        }

        public Boolean getServicableStatus() {
            return servicableStatus;
        }

        public void setServicableStatus(Boolean servicableStatus) {
            this.servicableStatus = servicableStatus;
        }
    }
}
