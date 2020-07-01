package com.dailylocally.ui.category.l2.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsRequest {

    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("scl1_id")
    @Expose
    private String scl1Id;

    @SerializedName("catid")
    @Expose
    private String catid;
    @SerializedName("scl2_id")
    @Expose
    private String scl2Id;
    @SerializedName("cid")
    @Expose
    private String cid;
    @SerializedName("sortid")
    @Expose
    private Integer sortid;
    @SerializedName("brandlist")
    @Expose
    private List<Brandlist> brandlist = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public Integer getSortid() {
        return sortid;
    }

    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }

    public List<Brandlist> getBrandlist() {
        return brandlist;
    }

    public void setBrandlist(List<Brandlist> brandlist) {
        this.brandlist = brandlist;
    }

    public static class Brandlist {

        @SerializedName("brand")
        @Expose
        private String brand;

        public Brandlist(String brand) {
            this.brand=brand;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

    }
}
