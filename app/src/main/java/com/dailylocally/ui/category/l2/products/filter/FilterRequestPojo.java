package com.dailylocally.ui.category.l2.products.filter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterRequestPojo {
    @SerializedName("cid")
    @Expose
    private String cid;

    @SerializedName("catid")
    @Expose
    private String catid;
    @SerializedName("scl1_id")
    @Expose
    private String scl1id;

    @SerializedName("scl2_id")
    @Expose
    private String scl2id;


    public FilterRequestPojo(String cid, String scl1id,String type) {
        this.cid = cid;
        this.scl1id = scl1id;
    }

     public FilterRequestPojo(String scl1id, String scl2id) {
        this.scl1id = scl1id;
        this.scl2id = scl2id;
    }



}