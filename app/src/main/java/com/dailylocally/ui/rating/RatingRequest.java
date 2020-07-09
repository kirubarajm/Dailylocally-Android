package com.dailylocally.ui.rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RatingRequest {

    /*@SerializedName("rating_product")
    @Expose
    public Integer ratingProduct;
    @SerializedName("rating_delivery")
    @Expose
    public Integer ratingDelivery;
    @SerializedName("product_received")
    @Expose
    public Integer productReceived;
    @SerializedName("doid")
    @Expose
    public Integer doid;
    @SerializedName("vpid")
    @Expose
    public List<Integer> vpid;
    @SerializedName("comments")
    @Expose
    public String comments;
    @SerializedName("package_sealed")
    @Expose
    public Integer package_sealed;

    public RatingRequest(Integer ratingProduct, Integer ratingDelivery, Integer productReceived, Integer doid,
                         List<Integer> vpid, String comments,Integer package_sealed) {
        this.ratingProduct = ratingProduct;
        this.ratingDelivery = ratingDelivery;
        this.productReceived = productReceived;
        this.doid = doid;
        this.vpid = vpid;
        this.comments = comments;
        this.package_sealed = package_sealed;
    }*/

    @SerializedName("rating_product")
    @Expose
    public Integer ratingProduct;
    @SerializedName("rating_delivery")
    @Expose
    public Integer ratingDelivery;
    @SerializedName("product_received")
    @Expose
    public Integer productReceived;
    @SerializedName("doid")
    @Expose
    public Integer doid;
    @SerializedName("vpid")
    @Expose
    public List<Integer> vpid = null;
    @SerializedName("comments")
    @Expose
    public String comments;
    @SerializedName("package_sealed")
    @Expose
    public Integer packageSealed;


    public RatingRequest(Integer ratingProduct, Integer ratingDelivery, Integer productReceived, Integer doid, List<Integer> vpid, String comments, Integer packageSealed) {
        this.ratingProduct = ratingProduct;
        this.ratingDelivery = ratingDelivery;
        this.productReceived = productReceived;
        this.doid = doid;
        this.vpid = vpid;
        this.comments = comments;
        this.packageSealed = packageSealed;
    }
}
