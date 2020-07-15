package com.dailylocally.ui.productDetail.dialogProductCancel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCancelRequest {

    @SerializedName("doid")
    @Expose
    public String doid;
    @SerializedName("id")
    @Expose
    public String id;


    public ProductCancelRequest(String doid, String vpid) {
        this.doid = doid;
        this.id = vpid;
    }
}
