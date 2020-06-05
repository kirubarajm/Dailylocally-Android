package com.dailylocally.ui.category.l2;

import com.dailylocally.ui.category.l1.L1CategoryResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class L2CategoryResponse {
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
    @SerializedName("product_list")
    @Expose
    private List<Object> productList = null;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;


    @SerializedName("get_sub_cat2_images")
    @Expose
    private List<GetSubCatImage> getSubCatImages = null;

    public List<GetSubCatImage> getGetSubCatImages() {
        return getSubCatImages;
    }

    public void setGetSubCatImages(List<GetSubCatImage> getSubCatImages) {
        this.getSubCatImages = getSubCatImages;
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

    public List<Object> getProductList() {
        return productList;
    }

    public void setProductList(List<Object> productList) {
        this.productList = productList;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class GetSubCatImage {

        @SerializedName("scid")
        @Expose
        private Integer scid;
        @SerializedName("image_url")
        @Expose
        private String imageUrl;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public Integer getScid() {
            return scid;
        }

        public void setScid(Integer scid) {
            this.scid = scid;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }
    public class Result {

        @SerializedName("scl2_id")
        @Expose
        private Integer scl2Id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private Object image;
        @SerializedName("scl1_id")
        @Expose
        private Integer scl1Id;
        @SerializedName("active_status")
        @Expose
        private Integer activeStatus;
        @SerializedName("servicable_status")
        @Expose
        private Boolean servicableStatus;

        public Integer getScl2Id() {
            return scl2Id;
        }

        public void setScl2Id(Integer scl2Id) {
            this.scl2Id = scl2Id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public Integer getScl1Id() {
            return scl1Id;
        }

        public void setScl1Id(Integer scl1Id) {
            this.scl1Id = scl1Id;
        }

        public Integer getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(Integer activeStatus) {
            this.activeStatus = activeStatus;
        }

        public Boolean getServicableStatus() {
            return servicableStatus;
        }

        public void setServicableStatus(Boolean servicableStatus) {
            this.servicableStatus = servicableStatus;
        }

    }
}
