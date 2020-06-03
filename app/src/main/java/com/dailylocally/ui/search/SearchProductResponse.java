package com.dailylocally.ui.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchProductResponse {
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("product")
    @Expose
    public List<Product> product = null;

    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public List<Product> getProduct() {
        return product;
    }

    public class Product {

        @SerializedName("pid")
        @Expose
        public Integer pid;
        @SerializedName("Productname")
        @Expose
        public String productname;
        @SerializedName("active_status")
        @Expose
        public Integer activeStatus;
        @SerializedName("scl1_id")
        @Expose
        public Integer scl1Id;
        @SerializedName("scl2_id")
        @Expose
        public Integer scl2Id;


        public Integer getPid() {
            return pid;
        }

        public String getProductname() {
            return productname;
        }

        public Integer getActiveStatus() {
            return activeStatus;
        }

        public Integer getScl1Id() {
            return scl1Id;
        }

        public Integer getScl2Id() {
            return scl2Id;
        }
    }
}
