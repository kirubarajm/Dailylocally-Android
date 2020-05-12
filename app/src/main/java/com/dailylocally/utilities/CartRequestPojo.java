package com.dailylocally.utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartRequestPojo {

    @SerializedName("makeit_user_id")
    @Expose
    private Long makeitUserid;
    @SerializedName("userid")
    @Expose
    private Long userid;
    @SerializedName("rcid")
    @Expose
    private Integer rcid;
    @SerializedName("cid")
    @Expose
    private Integer cid;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("orderitems")
    @Expose
    private List<Cartitem> cartitems = null;

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

    public Integer getRcid() {
        return rcid;
    }

    public void setRcid(Integer rcid) {
        this.rcid = rcid;
    }


    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getMakeitUserid() {
        return makeitUserid;
    }

    public void setMakeitUserid(Long makeitUserid) {
        this.makeitUserid = makeitUserid;
    }

    public List<Cartitem> getCartitems() {
        return cartitems;
    }

    public void setCartitems(List<Cartitem> cartitems) {
        this.cartitems = cartitems;
    }


    public static class Cartitem {

        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("quantity")
        @Expose
        private Integer quantity=0;

        @SerializedName("price")
        @Expose
        private Integer price;


        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getProductid() {
            return productid;
        }

        public void setProductid(Integer productid) {
            this.productid = productid;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

    }

}