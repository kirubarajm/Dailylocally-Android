package com.dailylocally.ui.productDetail.productDetailCancel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductCancelResponse {


    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("serviceablestatus")
    @Expose
    public Boolean serviceablestatus;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;


    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public Boolean getServiceablestatus() {
        return serviceablestatus;
    }

    public List<Result> getResult() {
        return result;
    }

    public class Result {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("userid")
        @Expose
        public Integer userid;
        @SerializedName("dayorderstatus")
        @Expose
        public Integer dayorderstatus;
        @SerializedName("rating_skip")
        @Expose
        public Object ratingSkip;
        @SerializedName("zoneid")
        @Expose
        public Integer zoneid;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("items")
        @Expose
        public List<Item> items = null;


        public Integer getId() {
            return id;
        }

        public String getDate() {
            return date;
        }

        public Integer getUserid() {
            return userid;
        }

        public Integer getDayorderstatus() {
            return dayorderstatus;
        }

        public Object getRatingSkip() {
            return ratingSkip;
        }

        public Integer getZoneid() {
            return zoneid;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public List<Item> getItems() {
            return items;
        }

        public class Item {

            @SerializedName("doid")
            @Expose
            public Integer doid;
            @SerializedName("pkts")
            @Expose
            public String pkts;
            @SerializedName("unit")
            @Expose
            public String unit;
            @SerializedName("vpid")
            @Expose
            public Integer vpid;
            @SerializedName("price")
            @Expose
            public String price;
            @SerializedName("packetsize")
            @Expose
            public String packetsize;
            @SerializedName("quantity")
            @Expose
            public String quantity;
            @SerializedName("brandname")
            @Expose
            public String brandname;

            @SerializedName("product_date")
            @Expose
            public String productDate;
            @SerializedName("product_name")
            @Expose
            public String productName;
            @SerializedName("product_image")
            @Expose
            public String productImage;
            @SerializedName("quantity_info")
            @Expose
            public String quantityInfo;
            @SerializedName("dayorderstatus")
            @Expose
            public Integer dayorderstatus;
            @SerializedName("scm_status")
            @Expose
            public Integer scmstatus;
            @SerializedName("scm_status_name")
            @Expose
            public String scmStatusName;
            @SerializedName("Cancel_available")
            @Expose
            public Integer cancelAvailable;
            @SerializedName("product_short_desc")
            @Expose
            public String productShortDesc;
            @SerializedName("dayorderpid")
            @Expose
            public Integer dayorderpid;

            public Integer getScmstatus() {
                return scmstatus;
            }

            public String getPrice() {
                return price;
            }

            public String getPacketsize() {
                return packetsize;
            }

            public String getQuantity() {
                return quantity;
            }

            public String getQuantityInfo() {
                return quantityInfo;
            }

            public Integer getDayorderpid() {
                return dayorderpid;
            }

            public String getPkts() {
                return pkts;
            }

            public Integer getDoid() {
                return doid;
            }

            public String getUnit() {
                return unit;
            }

            public Integer getVpid() {
                return vpid;
            }




            public String getBrandname() {
                return brandname;
            }


            public String getProductDate() {
                return productDate;
            }

            public String getProductName() {
                return productName;
            }

            public String getProductImage() {
                return productImage;
            }


            public Integer getDayorderstatus() {
                return dayorderstatus;
            }

            public String getScmStatusName() {
                return scmStatusName;
            }

            public Integer getCancelAvailable() {
                return cancelAvailable;
            }

            public String getProductShortDesc() {
                return productShortDesc;
            }
        }
    }
}
