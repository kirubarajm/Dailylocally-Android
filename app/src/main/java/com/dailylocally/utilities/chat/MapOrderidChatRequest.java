package com.dailylocally.utilities.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SusiAravind on 02,March,2020
 */
public class MapOrderidChatRequest {

    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("orderid")
    @Expose
    private String orderid;
    @SerializedName("issueid")
    @Expose
    private Integer issueid;
 @SerializedName("type")
    @Expose
    private Integer type;


    public MapOrderidChatRequest(String userid, String orderid, Integer issueid, Integer type) {
        this.userid = userid;
        this.orderid = orderid;
        this.issueid = issueid;
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Integer getIssueid() {
        return issueid;
    }

    public void setIssueid(Integer issueid) {
        this.issueid = issueid;
    }
}
