package com.dailylocally.ui.collection.l2.products.sort;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SortItems {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }


    public class Result {

        @SerializedName("sortid")
        @Expose
        private Integer sortid;
        @SerializedName("sortname")
        @Expose
        private String sortname;

        public Integer getSortid() {
            return sortid;
        }

        public void setSortid(Integer sortid) {
            this.sortid = sortid;
        }

        public String getSortname() {
            return sortname;
        }

        public void setSortname(String sortname) {
            this.sortname = sortname;
        }
    }
}