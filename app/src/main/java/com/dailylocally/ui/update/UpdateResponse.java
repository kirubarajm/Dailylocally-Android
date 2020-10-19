package com.dailylocally.ui.update;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("address_created")
    @Expose
    private String addressCreated;
    @SerializedName("result")
    @Expose
    private Result result;

    public String getAddressCreated() {
        return addressCreated;
    }

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


    public class Result {

        @SerializedName("versionstatus")
        @Expose
        private Boolean versionstatus=false;

         @SerializedName("logout")
        @Expose
        private Boolean logout=false;


        @SerializedName("dluserforceupdatestatus")
        @Expose
        private Boolean dluserforceupdatestatus=false;
        @SerializedName("support")
        @Expose
        private String supportNumber="9790876528";


        public Boolean getLogout() {
            return logout;
        }

        public String getSupportNumber() {
            return supportNumber;
        }

        public void setSupportNumber(String supportNumber) {
            this.supportNumber = supportNumber;
        }

        public Boolean getVersionstatus() {
            return versionstatus;
        }

        public void setVersionstatus(Boolean versionstatus) {
            this.versionstatus = versionstatus;
        }

        public Boolean getDluserforceupdatestatus() {
            return dluserforceupdatestatus;
        }

        public void setDluserforceupdatestatus(Boolean dluserforceupdatestatus) {
            this.dluserforceupdatestatus = dluserforceupdatestatus;
        }
    }
}