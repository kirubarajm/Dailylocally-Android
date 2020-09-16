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
        public String orderid;
        @SerializedName("tsid")
        @Expose
        public String tsid;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("gst")
        @Expose
        public String gst;
        @SerializedName("payment_status")
        @Expose
        public Integer paymentStatus;

        @SerializedName("payment_type")
        @Expose
        public String paymentType;
        @SerializedName("transaction_status")
        @Expose
        public String transactionStatus;
        @SerializedName("transaction_time")
        @Expose
        public String transactionTime;


        @SerializedName("discount_amount")
        @Expose
        public String discountAmount;
        @SerializedName("itemscount")
        @Expose
        public Integer itemscount;
        @SerializedName("items")
        @Expose
        public List<Item> items = null;
        @SerializedName("cartdetails")
        @Expose
        public List<Cartdetail> cartdetails = null;
        @SerializedName("online_order")
        @Expose
        public Integer onlineOrder;
        @SerializedName("created_at")
        @Expose
        public String createdAt;


        public String getCreatedAt() {
            return createdAt;
        }

        public Boolean getOnlineOrder() {

            if (onlineOrder != null && onlineOrder == 1) {
                return true;
            } else {
                return false;
            }
        }
        public String getOrderid() {
            return orderid;
        }

        public String getTsid() {
            return tsid;
        }

        public String getPrice() {
            return price;
        }

        public String getGst() {
            return gst;
        }

        public Integer getPaymentStatus() {
            return paymentStatus;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public String getDiscountAmount() {
            return discountAmount;
        }

        public Integer getItemscount() {
            return itemscount;
        }

        public String getTransactionStatus() {
            return transactionStatus;
        }

        public String getTransactionTime() {
            return transactionTime;
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
            public String vpid;
            @SerializedName("price")
            @Expose
            public String price;
            @SerializedName("weight")
            @Expose
            public String weight;
            @SerializedName("quantity")
            @Expose
            public Integer quantity;
            @SerializedName("brandname")
            @Expose
            public String brandname;
            @SerializedName("packet_size")
            @Expose
            public String packetSize;

            @SerializedName("pkts")
            @Expose
            public String pkts;

            @SerializedName("product_date")
            @Expose
            public String productDate;
            @SerializedName("product_name")
            @Expose
            public String productName;

            @SerializedName("dayorderpid")
            @Expose
            public String dayorderpid;
            @SerializedName("doid")
            @Expose
            public String doid;

            @SerializedName("quantity_info")
            @Expose
            public String quantityInfo;
            @SerializedName("dayorderstatus")
            @Expose
            public Integer dayorderstatus;
            @SerializedName("scm_status_name")
            @Expose
            public String scmStatusName;
            @SerializedName("Cancel_available")
            @Expose
            public Integer cancelAvailable;

            public String getDoid() {
                return doid;
            }

            public String getDayorderpid() {
                return dayorderpid;
            }

            public void setDayorderpid(String dayorderpid) {
                this.dayorderpid = dayorderpid;
            }

            public String getPkts() {
                return pkts;
            }

            public String getPacketSize() {
                return packetSize;
            }

            public String getUnit() {
                return unit;
            }


            public Integer getQuantity() {
                return quantity;
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

            public String getQuantityInfo() {
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

            public String getVpid() {
                return vpid;
            }

            public String getPrice() {
                return price;
            }

            public String getWeight() {
                return weight;
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
