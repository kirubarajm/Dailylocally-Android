package com.dailylocally.ui.productDetail.dialogProductCancel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCancelRequest {

    @SerializedName("doid")
    @Expose
    public String doid;
    @SerializedName("vpid")
    @Expose
    public String vpid;


    public ProductCancelRequest(String doid, String vpid) {
        this.doid = doid;
        this.vpid = vpid;
    }
}
