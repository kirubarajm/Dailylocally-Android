package com.dailylocally.ui.productDetail.productCancel;

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
    @SerializedName("unserviceable_title")
    @Expose
    public String unserviceableTitle;
    @SerializedName("unserviceable_subtitle")
    @Expose
    public String unserviceableSubtitle;
    @SerializedName("empty_url")
    @Expose
    public String emptyUrl;
    @SerializedName("empty_content")
    @Expose
    public String emptyContent;
    @SerializedName("empty_subconent")
    @Expose
    public String emptySubconent;
    @SerializedName("header_content")
    @Expose
    public String headerContent;
    @SerializedName("header_subconent")
    @Expose
    public String headerSubconent;
    @SerializedName("category_title")
    @Expose
    public String categoryTitle;
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

    public String getUnserviceableTitle() {
        return unserviceableTitle;
    }

    public String getUnserviceableSubtitle() {
        return unserviceableSubtitle;
    }

    public String getEmptyUrl() {
        return emptyUrl;
    }

    public String getEmptyContent() {
        return emptyContent;
    }

    public String getEmptySubconent() {
        return emptySubconent;
    }

    public String getHeaderContent() {
        return headerContent;
    }

    public String getHeaderSubconent() {
        return headerSubconent;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public List<Result> getResult() {
        return result;
    }

    public class Result {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("doid")
        @Expose
        public Integer doid;
        @SerializedName("vpid")
        @Expose
        public Integer vpid;
        @SerializedName("orderid")
        @Expose
        public Integer orderid;
        @SerializedName("scm_status")
        @Expose
        public Integer scmStatus;
        @SerializedName("productname")
        @Expose
        public String productname;
        @SerializedName("quantity")
        @Expose
        public Integer quantity;
        @SerializedName("price")
        @Expose
        public Integer price;
        @SerializedName("received_quantity")
        @Expose
        public Integer receivedQuantity;
        @SerializedName("sorting_status")
        @Expose
        public Integer sortingStatus;
        @SerializedName("popid")
        @Expose
        public Object popid;
        @SerializedName("product_hsn_code")
        @Expose
        public Object productHsnCode;
        @SerializedName("product_image")
        @Expose
        public String productImage;
        @SerializedName("product_brand")
        @Expose
        public Integer productBrand;
        @SerializedName("product_mrp")
        @Expose
        public Integer productMrp;
        @SerializedName("product_basiccost")
        @Expose
        public Object productBasiccost;
        @SerializedName("product_targetedbaseprice")
        @Expose
        public Object productTargetedbaseprice;
        @SerializedName("product_discount_cost")
        @Expose
        public Integer productDiscountCost;
        @SerializedName("product_gst")
        @Expose
        public Integer productGst;
        @SerializedName("product_scl1_id")
        @Expose
        public Integer productScl1Id;
        @SerializedName("product_scl2_id")
        @Expose
        public Integer productScl2Id;
        @SerializedName("product_subscription")
        @Expose
        public Integer productSubscription;
        @SerializedName("product_weight")
        @Expose
        public Double productWeight;
        @SerializedName("product_uom")
        @Expose
        public Integer productUom;
        @SerializedName("product_packetsize")
        @Expose
        public Object productPacketsize;
        @SerializedName("product_vegtype")
        @Expose
        public Integer productVegtype;
        @SerializedName("product_tag")
        @Expose
        public Integer productTag;
        @SerializedName("product_short_desc")
        @Expose
        public String productShortDesc;
        @SerializedName("product_productdetails")
        @Expose
        public Object productProductdetails;
        @SerializedName("product_Perishable")
        @Expose
        public Object productPerishable;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public Object updatedAt;
        @SerializedName("prid")
        @Expose
        public Object prid;
        @SerializedName("product_cancel_time")
        @Expose
        public Object productCancelTime;
        @SerializedName("servicable_status")
        @Expose
        public Boolean servicableStatus;


        public Integer getId() {
            return id;
        }

        public Integer getDoid() {
            return doid;
        }

        public Integer getVpid() {
            return vpid;
        }

        public Integer getOrderid() {
            return orderid;
        }

        public Integer getScmStatus() {
            return scmStatus;
        }

        public String getProductname() {
            return productname;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public Integer getPrice() {
            return price;
        }

        public Integer getReceivedQuantity() {
            return receivedQuantity;
        }

        public Integer getSortingStatus() {
            return sortingStatus;
        }

        public Object getPopid() {
            return popid;
        }

        public Object getProductHsnCode() {
            return productHsnCode;
        }

        public String getProductImage() {
            return productImage;
        }

        public Integer getProductBrand() {
            return productBrand;
        }

        public Integer getProductMrp() {
            return productMrp;
        }

        public Object getProductBasiccost() {
            return productBasiccost;
        }

        public Object getProductTargetedbaseprice() {
            return productTargetedbaseprice;
        }

        public Integer getProductDiscountCost() {
            return productDiscountCost;
        }

        public Integer getProductGst() {
            return productGst;
        }

        public Integer getProductScl1Id() {
            return productScl1Id;
        }

        public Integer getProductScl2Id() {
            return productScl2Id;
        }

        public Integer getProductSubscription() {
            return productSubscription;
        }

        public Double getProductWeight() {
            return productWeight;
        }

        public Integer getProductUom() {
            return productUom;
        }

        public Object getProductPacketsize() {
            return productPacketsize;
        }

        public Integer getProductVegtype() {
            return productVegtype;
        }

        public Integer getProductTag() {
            return productTag;
        }

        public String getProductShortDesc() {
            return productShortDesc;
        }

        public Object getProductProductdetails() {
            return productProductdetails;
        }

        public Object getProductPerishable() {
            return productPerishable;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public Object getPrid() {
            return prid;
        }

        public Object getProductCancelTime() {
            return productCancelTime;
        }

        public Boolean getServicableStatus() {
            return servicableStatus;
        }
    }
}
