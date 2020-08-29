package com.dailylocally.utilities.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssuesRequest {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("orderid")
    @Expose
    private String orderid;


    public IssuesRequest(Integer type, String userid) {
        this.type = type;
        this.userid = userid;
    }

    public IssuesRequest(String userid, Integer issueid) {
        this.id = issueid;
        this.userid = userid;
    }

    public IssuesRequest(Integer id, String userid, String orderid) {
        this.id = id;
        this.userid = userid;
        this.orderid = orderid;
    }

    public IssuesRequest(Integer type, Integer id, String userid, String orderid) {
        this.id = id;
        this.userid = userid;
        this.orderid = orderid;
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
