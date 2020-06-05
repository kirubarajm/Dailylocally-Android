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
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;

    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public Boolean getServicableStatus() {
        return servicableStatus;
    }

    public List<Datum> getData() {
        return data;
    }

    public class Datum {

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("id")
        @Expose
        public Integer id;


        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public Integer getId() {
            return id;
        }
    }
}
