package com.dailylocally.ui.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuickSearchResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("servicable_status")
    @Expose
    public Boolean servicableStatus;
    @SerializedName("result")
    @Expose
    public Result result;


    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public Boolean getServicableStatus() {
        return servicableStatus;
    }

    public Result getResult() {
        return result;
    }

    public class Result {

        @SerializedName("products_title")
        @Expose
        public String productsTitle;
        @SerializedName("products_list")
        @Expose
        public List<ProductsList> productsList = null;
        @SerializedName("subcategory_title")
        @Expose
        public String subcategoryTitle;
        @SerializedName("subcategory_list")
        @Expose
        public List<SubcategoryList> subcategoryList = null;


        public String getProductsTitle() {
            return productsTitle;
        }

        public List<ProductsList> getProductsList() {
            return productsList;
        }

        public String getSubcategoryTitle() {
            return subcategoryTitle;
        }

        public List<SubcategoryList> getSubcategoryList() {
            return subcategoryList;
        }

        public class ProductsList {

            @SerializedName("Productname")
            @Expose
            public String productname;
            @SerializedName("type")
            @Expose
            public String type;
            @SerializedName("vpid")
            @Expose
            public Integer vpid;
            @SerializedName("scl1_id")
            @Expose
            public Integer scl1Id;
            @SerializedName("scl2_id")
            @Expose
            public Integer scl2Id;
            @SerializedName("name")
            @Expose
            public String name;
            @SerializedName("favid")
            @Expose
            public Object favid;
            @SerializedName("isfav")
            @Expose
            public String isfav;
            @SerializedName("unit")
            @Expose
            public String unit;
            @SerializedName("brandname")
            @Expose
            public String brandname;
            @SerializedName("packetsize")
            @Expose
            public String packetsize;

            public String getProductname() {
                return productname;
            }

            public String getType() {
                return type;
            }

            public Integer getVpid() {
                return vpid;
            }

            public Integer getScl1Id() {
                return scl1Id;
            }

            public Integer getScl2Id() {
                return scl2Id;
            }

            public String getName() {
                return name;
            }

            public Object getFavid() {
                return favid;
            }

            public String getIsfav() {
                return isfav;
            }

            public String getUnit() {
                return unit;
            }

            public String getBrandname() {
                return brandname;
            }

            public String getPacketsize() {
                return packetsize;
            }
        }


        public class SubcategoryList {

            @SerializedName("sub_category")
            @Expose
            public String subCategory;
            @SerializedName("scl1_id")
            @Expose
            public Integer scl1Id;
            @SerializedName("catid")
            @Expose
            public Integer catid;
            @SerializedName("category_name")
            @Expose
            public String categoryName;


            public String getSubCategory() {
                return subCategory;
            }

            public Integer getScl1Id() {
                return scl1Id;
            }

            public Integer getCatid() {
                return catid;
            }

            public String getCategoryName() {
                return categoryName;
            }
        }
    }
}
