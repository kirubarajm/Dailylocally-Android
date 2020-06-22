package com.dailylocally.ui.category.l2.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsRequest {

    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("scl1_id")
    @Expose
    private Integer scl1Id;
    @SerializedName("scl2_id")
    @Expose
    private Integer scl2Id;
    @SerializedName("sortid")
    @Expose
    private Integer sortid;
    @SerializedName("brandlist")
    @Expose
    private List<Brandlist> brandlist = null;

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





    public class Brandlist {

        @SerializedName("brand")
        @Expose
        private Integer brand;

        public Integer getBrand() {
            return brand;
        }

        public void setBrand(Integer brand) {
            this.brand = brand;
        }

    }
}
