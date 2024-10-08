package com.dailylocally.ui.calendarView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalendarDayWiseResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;

    @SerializedName("empty_title")
    @Expose
    public String emptyTitle;

    @SerializedName("empty_content")
    @Expose
    public String emptyContent;

    @SerializedName("result")
    @Expose
    public List<Result> result = null;


    public String getEmptyTitle() {
        return emptyTitle;
    }

    public String getEmptyContent() {
        return emptyContent;
    }

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

        @SerializedName("userid")
        @Expose
        public Integer userid;
        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("dayorderstatus")
        @Expose
        public Integer dayorderstatus;
        @SerializedName("rating_status")
        @Expose
        public Boolean ratingStatus;
        @SerializedName("itemscount")
        @Expose
        public Integer itemsCount;

        @SerializedName("items")
        @Expose
        public List<Item> items = null;


        public Integer getItemsCount() {
            return itemsCount;
        }

        public Boolean getRatingStatus() {
            return ratingStatus;
        }

        public Integer getUserid() {
            return userid;
        }

        public String getDate() {
            return date;
        }

        public Integer getDayorderstatus() {
            return dayorderstatus;
        }

        public List<Item> getItems() {
            return items;
        }

        public class Item {

            @SerializedName("unit")
            @Expose
            public String unit;
            @SerializedName("pkts")
            @Expose
            public String pkts;
            @SerializedName("doid")
            @Expose
            public String doid;
            @SerializedName("vpid")
            @Expose
            public Integer vpid;
            @SerializedName("price")
            @Expose
            public Integer price;
            @SerializedName("weight")
            @Expose
            public String weight;
            @SerializedName("packet_size")
            @Expose
            public String packetSize;
            @SerializedName("quantity")
            @Expose
            public Integer quantity;
            @SerializedName("brandname")
            @Expose
            public String brandname;
            @SerializedName("dayorderpid")
            @Expose
            public String dayorderpid;
            @SerializedName("product_name")
            @Expose
            public String productName;
            @SerializedName("quantity_info")
            @Expose
            public Integer quantityInfo;
            @SerializedName("trueOrFalse")
            @Expose
            public Boolean trueOrFalse;

            public String getPacketSize() {
                if (packetSize == null) {
                    return weight;
                } else
                    return packetSize;
            }

            public String getPkts() {
                return pkts;
            }

            public Boolean getTrueOrFalse() {
                return trueOrFalse;
            }

            public void setTrueOrFalse(Boolean trueOrFalse) {
                this.trueOrFalse = trueOrFalse;
            }

            public String getDoid() {
                return doid;
            }

            public String getUnit() {
                return unit;
            }

            public Integer getVpid() {
                return vpid;
            }

            public Integer getPrice() {
                return price;
            }

            public String getWeight() {
                return weight;
            }

            public Integer getQuantity() {
                return quantity;
            }

            public String getBrandname() {
                return brandname;
            }

            public String getDayorderpid() {
                return dayorderpid;
            }

            public String getProductName() {
                return productName;
            }

            public Integer getQuantityInfo() {
                return quantityInfo;
            }
        }
    }

}
