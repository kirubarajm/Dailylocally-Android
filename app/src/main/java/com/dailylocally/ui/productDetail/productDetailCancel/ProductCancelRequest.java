package com.dailylocally.ui.productDetail.productDetailCancel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCancelRequest {
    @SerializedName("doid")
    @Expose
    public Integer doid;
    @SerializedName("dayorderpid")
    @Expose
    public Integer dayorderpid;


    public ProductCancelRequest(Integer doid, Integer dayorderpid) {
        this.doid = doid;
        this.dayorderpid = dayorderpid;
    }
}
