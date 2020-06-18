package com.dailylocally.ui.category.l2.products.filter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterRequestPojo {
    @SerializedName("search")
    @Expose
    private String search;


    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;


    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("locality")
    @Expose
    private String locality;

    @SerializedName("city")
    @Expose
    private String city;


    @SerializedName("eatuserid")
    @Expose
    private Long eatuserid;

    @SerializedName("sortid")
    @Expose
    private Integer sortid = 0;

    @SerializedName("page")
    @Expose
    private Integer page = 0;

    @SerializedName("cid")
    @Expose
    private Integer cid;

    @SerializedName("regionlist")
    @Expose
    private List<Regionlist> regionlist = null;
    @SerializedName("cuisinelist")
    @Expose
    private List<Cusinelist> cusinelist = null;
    @SerializedName("sortlist")
    @Expose
    private List<Sortlist> sortlist = null;
    @SerializedName("vegtype")
    @Expose
    private Integer vegtype;

    public Integer getPageid() {
        return page;
    }

    public void setPageid(Integer pageid) {
        this.page = pageid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getVegtype() {
        return vegtype;
    }

    public void setVegtype(Integer vegtype) {
        this.vegtype = vegtype;
    }

    public Integer getSortid() {
        return sortid;
    }

    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    public Long getEatuserid() {
        return eatuserid;
    }

    public void setEatuserid(Long eatuserid) {
        this.eatuserid = eatuserid;
    }

    public List<Regionlist> getRegionlist() {
        return regionlist;
    }

    public void setRegionlist(List<Regionlist> regionlist) {
        this.regionlist = regionlist;
    }

    public List<Cusinelist> getCusinelist() {
        return cusinelist;
    }

    public void setCusinelist(List<Cusinelist> cusinelist) {
        this.cusinelist = cusinelist;
    }

    public List<Sortlist> getSortlist() {
        return sortlist;
    }

    public void setSortlist(List<Sortlist> sortlist) {
        this.sortlist = sortlist;
    }

    public static class Cusinelist {
        @SerializedName("cuisine")
        @Expose
        private Integer cusine;

        public Cusinelist(Integer cusine) {
            this.cusine = cusine;
        }

        public Integer getCusine() {
            return cusine;
        }

        public void setCusine(Integer cusine) {
            this.cusine = cusine;
        }

    }

    public static class Regionlist {

        @SerializedName("region")
        @Expose
        private Integer region;

        public Regionlist(Integer region) {
            this.region = region;
        }

        public Integer getRegion() {
            return region;
        }

        public void setRegion(Integer region) {
            this.region = region;
        }

    }

    public static class Sortlist {


        @SerializedName("sort")
        @Expose
        private Integer sort;

        public Sortlist(Integer sort) {
            this.sort = sort;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

    }
}