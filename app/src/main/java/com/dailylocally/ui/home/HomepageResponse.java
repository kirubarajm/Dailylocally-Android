package com.dailylocally.ui.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomepageResponse {
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

        @SerializedName("catid")
        @Expose
        private Integer catid;

        @SerializedName("cid")
        @Expose
        private String cid;

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("servicable_status")
        @Expose
        private Boolean servicableStatus;
        @SerializedName("clickable")
        @Expose
        private Boolean clickable;
        @SerializedName("approval_status")
        @Expose
        private Boolean approvalStatus;

        @SerializedName("join_status")
        @Expose
        private Boolean joinStatus;


        @SerializedName("collection_status")
        @Expose
        private Boolean collectionStatus;

        @SerializedName("tile_type")
        @Expose
        private String tileType;

        @SerializedName("type")
        @Expose
        private Integer type;

        @SerializedName("show_video")
        @Expose
        private Boolean showVideo;


        public void setClickable(Boolean clickable) {
            this.clickable = clickable;
        }

        public void setApprovalStatus(Boolean approvalStatus) {
            this.approvalStatus = approvalStatus;
        }

        public void setJoinStatus(Boolean joinStatus) {
            this.joinStatus = joinStatus;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Boolean getShowVideo() {
            return showVideo;
        }

        public void setShowVideo(Boolean showVideo) {
            this.showVideo = showVideo;
        }

        public Boolean getApprovalStatus() {
            return approvalStatus;
        }

        public Boolean getJoinStatus() {
            return joinStatus;
        }

        public Integer getType() {
            return type;
        }

        public Boolean getClickable() {
            return clickable;
        }

        public Integer getCatid() {
            return catid;
        }

        public void setCatid(Integer catid) {
            this.catid = catid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Boolean getServicableStatus() {
            return servicableStatus;
        }

        public void setServicableStatus(Boolean servicableStatus) {
            this.servicableStatus = servicableStatus;
        }

        public Boolean getCollectionStatus() {
            return collectionStatus;
        }

        public void setCollectionStatus(Boolean collectionStatus) {
            this.collectionStatus = collectionStatus;
        }

        public String getTileType() {
            return tileType;
        }

        public void setTileType(String tileType) {
            this.tileType = tileType;
        }
    }
}
