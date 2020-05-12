package com.dailylocally.utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("customer_support")
    @Expose
    private Long customerSupport;

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

    public Long getCustomerSupport() {
        return customerSupport;
    }

    public void setCustomerSupport(Long customerSupport) {
        this.customerSupport = customerSupport;
    }
}
