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
    private Long userid;
    @SerializedName("orderid")
    @Expose
    private Long orderid;


    public IssuesRequest(Integer type, Long userid) {
        this.type = type;
        this.userid = userid;
    }

    public IssuesRequest(Long userid,Integer issueid) {
        this.id = issueid;
        this.userid = userid;
    }

    public IssuesRequest(Integer id, Long userid, Long orderid) {
        this.id = id;
        this.userid = userid;
        this.orderid = orderid;
    }
 public IssuesRequest(Integer type,Integer id, Long userid, Long orderid) {
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
}
