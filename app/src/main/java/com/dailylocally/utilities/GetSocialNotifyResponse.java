package com.dailylocally.utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSocialNotifyResponse {

    @SerializedName("a")
    @Expose
    private List<A> a = null;
    @SerializedName("ab")
    @Expose
    private List<Object> ab = null;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("i_url")
    @Expose
    private String iUrl;
    @SerializedName("ap")
    @Expose
    private Ap ap;
    @SerializedName("ts")
    @Expose
    private Integer ts;

    public List<A> getA() {
        return a;
    }

    public void setA(List<A> a) {
        this.a = a;
    }

    public List<Object> getAb() {
        return ab;
    }

    public void setAb(List<Object> ab) {
        this.ab = ab;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIUrl() {
        return iUrl;
    }

    public void setIUrl(String iUrl) {
        this.iUrl = iUrl;
    }

    public Ap getAp() {
        return ap;
    }

    public void setAp(Ap ap) {
        this.ap = ap;
    }

    public Integer getTs() {
        return ts;
    }

    public void setTs(Integer ts) {
        this.ts = ts;
    }


    public class A {

        @SerializedName("t")
        @Expose
        private Integer t;
        @SerializedName("d")
        @Expose
        private D d;
        @SerializedName("n")
        @Expose
        private String n;

        public Integer getT() {
            return t;
        }

        public void setT(Integer t) {
            this.t = t;
        }

        public D getD() {
            return d;
        }

        public void setD(D d) {
            this.d = d;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

    }


    public class Ap {

        @SerializedName("targeted_notification_id")
        @Expose
        private String targetedNotificationId;
        @SerializedName("g_nid")
        @Expose
        private String gNid;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("type")
        @Expose
        private String type;

        public String getTargetedNotificationId() {
            return targetedNotificationId;
        }

        public void setTargetedNotificationId(String targetedNotificationId) {
            this.targetedNotificationId = targetedNotificationId;
        }

        public String getGNid() {
            return gNid;
        }

        public void setGNid(String gNid) {
            this.gNid = gNid;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }


    public class D {

        @SerializedName("catid")
        @Expose
        private String catid;
        @SerializedName("pageid")
        @Expose
        private String pageid;
        @SerializedName("scl1id")
        @Expose
        private String scl1id;
        @SerializedName("orderid")
        @Expose
        private String orderid;
        @SerializedName("vpid")
        @Expose
        private String vpid;
        @SerializedName("cid")
        @Expose
        private String cid;
        @SerializedName("topic")
        @Expose
        private String topic;

        @SerializedName("title")
        @Expose
        private String title;

        public String getScl1id() {
            return scl1id;
        }

        public String getOrderid() {
            return orderid;
        }

        public String getVpid() {
            return vpid;
        }

        public String getCid() {
            return cid;
        }

        public String getTopic() {
            return topic;
        }

        public String getTitle() {
            return title;
        }

        public String getCatid() {
            return catid;
        }

        public void setCatid(String catid) {
            this.catid = catid;
        }

        public String getPageid() {
            return pageid;
        }

        public void setPageid(String pageid) {
            this.pageid = pageid;
        }

    }


}