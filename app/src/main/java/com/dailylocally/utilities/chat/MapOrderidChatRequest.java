package com.dailylocally.utilities.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SusiAravind on 02,March,2020
 */
public class MapOrderidChatRequest {

    @SerializedName("userid")
    @Expose
    private Long userid;
    @SerializedName("orderid")
    @Expose
    private Long orderid;
    @SerializedName("issueid")
    @Expose
    private Integer issueid;
 @SerializedName("type")
    @Expose
    private Integer type;


    public MapOrderidChatRequest(Long userid, Long orderid, Integer issueid, Integer type) {
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

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Integer getIssueid() {
        return issueid;
    }

    public void setIssueid(Integer issueid) {
        this.issueid = issueid;
    }
}
