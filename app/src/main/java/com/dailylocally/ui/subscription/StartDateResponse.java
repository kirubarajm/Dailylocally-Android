package com.dailylocally.ui.subscription;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartDateResponse {

@SerializedName("success")
@Expose
private Boolean success;
@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("order_delivery_day")
@Expose
private String orderDeliveryDay;

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

public String getOrderDeliveryDay() {
return orderDeliveryDay;
}

public void setOrderDeliveryDay(String orderDeliveryDay) {
this.orderDeliveryDay = orderDeliveryDay;
}

}