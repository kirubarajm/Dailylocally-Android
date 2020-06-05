package com.dailylocally.ui.category.l1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class L1CategoryResponse {
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
    @SerializedName("category_title")
    @Expose
    private String categoryTitle;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("get_sub_cat_images")
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

        @SerializedName("scl1_id")
        @Expose
        private Integer scl1Id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private Object image;
        @SerializedName("catid")
        @Expose
        private Integer catid;
        @SerializedName("servicable_status")
        @Expose
        private Boolean servicableStatus;

        public Integer getScl1Id() {
            return scl1Id;
        }

        public void setScl1Id(Integer scl1Id) {
            this.scl1Id = scl1Id;
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

        public Integer getCatid() {
            return catid;
        }

        public void setCatid(Integer catid) {
            this.catid = catid;
        }

        public Boolean getServicableStatus() {
            return servicableStatus;
        }

        public void setServicableStatus(Boolean servicableStatus) {
            this.servicableStatus = servicableStatus;
        }

    }


}
