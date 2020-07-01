package com.dailylocally.ui.transactionHistory.view;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionViewResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;


    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public List<Result> getResult() {
        return result;
    }

    public class Result {

        @SerializedName("orderid")
        @Expose
        public Integer orderid;
        @SerializedName("tsid")
        @Expose
        public Integer tsid;
        @SerializedName("price")
        @Expose
        public Integer price;
        @SerializedName("gst")
        @Expose
        public Integer gst;
        @SerializedName("payment_status")
        @Expose
        public Integer paymentStatus;
        @SerializedName("userid")
        @Expose
        public Integer userid;
        @SerializedName("aid")
        @Expose
        public Integer aid;
        @SerializedName("cus_lat")
        @Expose
        public Double cusLat;
        @SerializedName("cus_lon")
        @Expose
        public String cusLon;
        @SerializedName("cus_pincode")
        @Expose
        public String cusPincode;
        @SerializedName("landmark")
        @Expose
        public String landmark;
        @SerializedName("app_type")
        @Expose
        public String appType;
        @SerializedName("payment_type")
        @Expose
        public Integer paymentType;
        @SerializedName("transaction_status")
        @Expose
        public String transactionStatus;
        @SerializedName("transaction_time")
        @Expose
        public String transactionTime;
        @SerializedName("zoneid")
        @Expose
        public Integer zoneid;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("apartment_name")
        @Expose
        public String apartmentName;
        @SerializedName("google_address")
        @Expose
        public String googleAddress;
        @SerializedName("complete_address")
        @Expose
        public String completeAddress;
        @SerializedName("flat_house_no")
        @Expose
        public String flatHouseNo;
        @SerializedName("plot_house_no")
        @Expose
        public String plotHouseNo;
        @SerializedName("floor")
        @Expose
        public String floor;
        @SerializedName("block_name")
        @Expose
        public String blockName;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("address_type")
        @Expose
        public String addressType;
        @SerializedName("delivery_charge")
        @Expose
        public String deliveryCharge;
        @SerializedName("coupon")
        @Expose
        public Object coupon;
        @SerializedName("discount_amount")
        @Expose
        public Integer discountAmount;
        @SerializedName("itemscount")
        @Expose
        public Integer itemscount;
        @SerializedName("items")
        @Expose
        public List<Item> items = null;
        @SerializedName("cartdetails")
        @Expose
        public List<Cartdetail> cartdetails = null;


        public Integer getItemscount() {
            return itemscount;
        }

        public Integer getOrderid() {
            return orderid;
        }

        public Integer getTsid() {
            return tsid;
        }

        public Integer getPrice() {
            return price;
        }

        public Integer getGst() {
            return gst;
        }

        public Integer getPaymentStatus() {
            return paymentStatus;
        }

        public Integer getUserid() {
            return userid;
        }

        public Integer getAid() {
            return aid;
        }

        public Double getCusLat() {
            return cusLat;
        }

        public String getCusLon() {
            return cusLon;
        }

        public String getCusPincode() {
            return cusPincode;
        }

        public String getLandmark() {
            return landmark;
        }

        public String getAppType() {
            return appType;
        }

        public Integer getPaymentType() {
            return paymentType;
        }

        public String getTransactionStatus() {
            return transactionStatus;
        }

        public String getTransactionTime() {
            return transactionTime;
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

        public String getApartmentName() {
            return apartmentName;
        }

        public String getGoogleAddress() {
            return googleAddress;
        }

        public String getCompleteAddress() {
            return completeAddress;
        }

        public String getFlatHouseNo() {
            return flatHouseNo;
        }

        public String getPlotHouseNo() {
            return plotHouseNo;
        }

        public String getFloor() {
            return floor;
        }

        public String getBlockName() {
            return blockName;
        }

        public String getCity() {
            return city;
        }

        public String getAddressType() {
            return addressType;
        }

        public String getDeliveryCharge() {
            return deliveryCharge;
        }

        public Object getCoupon() {
            return coupon;
        }

        public Integer getDiscountAmount() {
            return discountAmount;
        }

        public List<Item> getItems() {
            return items;
        }

        public List<Cartdetail> getCartdetails() {
            return cartdetails;
        }

        public class Item {

            @SerializedName("unit")
            @Expose
            public String unit;
            @SerializedName("vpid")
            @Expose
            public Integer vpid;
            @SerializedName("price")
            @Expose
            public Integer price;
            @SerializedName("weight")
            @Expose
            public Integer weight;
            @SerializedName("quantity")
            @Expose
            public Integer quantity;
            @SerializedName("brandname")
            @Expose
            public String brandname;
            @SerializedName("scm_status")
            @Expose
            public Integer scmStatus;
            @SerializedName("product_date")
            @Expose
            public String productDate;
            @SerializedName("product_name")
            @Expose
            public String productName;
            @SerializedName("quantity_info")
            @Expose
            public Integer quantityInfo;
            @SerializedName("dayorderstatus")
            @Expose
            public Integer dayorderstatus;
            @SerializedName("scm_status_name")
            @Expose
            public String scmStatusName;
            @SerializedName("Cancel_available")
            @Expose
            public Integer cancelAvailable;


            public String getUnit() {
                return unit;
            }

            public Integer getVpid() {
                return vpid;
            }

            public Integer getPrice() {
                return price;
            }

            public Integer getWeight() {
                return weight;
            }

            public Integer getQuantity() {
                return quantity;
            }

            public String getBrandname() {
                return brandname;
            }

            public Integer getScmStatus() {
                return scmStatus;
            }

            public String getProductDate() {
                return productDate;
            }

            public String getProductName() {
                return productName;
            }

            public Integer getQuantityInfo() {
                return quantityInfo;
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
        }



        public class Cartdetail {

            @SerializedName("title")
            @Expose
            public String title;
            @SerializedName("charges")
            @Expose
            public String charges;
            @SerializedName("status")
            @Expose
            public Boolean status;
            @SerializedName("infostatus")
            @Expose
            public Boolean infostatus;
            @SerializedName("color_code")
            @Expose
            public String colorCode;
            @SerializedName("low_cost_status")
            @Expose
            public Boolean lowCostStatus;
            @SerializedName("low_cost_note")
            @Expose
            public String lowCostNote;
            @SerializedName("default_cost")
            @Expose
            public Integer defaultCost;
            @SerializedName("default_cost_status")
            @Expose
            public Boolean defaultCostStatus;
            @SerializedName("infodetails")
            @Expose
            public List<Object> infodetails = null;


            public String getTitle() {
                return title;
            }

            public String getCharges() {
                return charges;
            }

            public Boolean getStatus() {
                return status;
            }

            public Boolean getInfostatus() {
                return infostatus;
            }

            public String getColorCode() {
                return colorCode;
            }

            public Boolean getLowCostStatus() {
                return lowCostStatus;
            }

            public String getLowCostNote() {
                return lowCostNote;
            }

            public Integer getDefaultCost() {
                return defaultCost;
            }

            public Boolean getDefaultCostStatus() {
                return defaultCostStatus;
            }

            public List<Object> getInfodetails() {
                return infodetails;
            }
        }
    }
}
