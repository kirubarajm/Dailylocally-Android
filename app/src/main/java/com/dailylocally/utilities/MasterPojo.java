package com.dailylocally.utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MasterPojo {


    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Addresstype {

        @SerializedName("addresstypeid")
        @Expose
        private Integer addresstypeid;
        @SerializedName("description")
        @Expose
        private String description;

        public Integer getAddresstypeid() {
            return addresstypeid;
        }

        public void setAddresstypeid(Integer addresstypeid) {
            this.addresstypeid = addresstypeid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

    public class Appuserstype {

        @SerializedName("addresstypeid")
        @Expose
        private Integer addresstypeid;
        @SerializedName("description")
        @Expose
        private String description;

        public Integer getAddresstypeid() {
            return addresstypeid;
        }

        public void setAddresstypeid(Integer addresstypeid) {
            this.addresstypeid = addresstypeid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

    public class Cuisinelist {

        @SerializedName("cuisineid")
        @Expose
        private Integer cusineid;
        @SerializedName("cuisinename")
        @Expose
        private String cusinename;

        public Integer getCusineid() {
            return cusineid;
        }

        public void setCusineid(Integer cusineid) {
            this.cusineid = cusineid;
        }

        public String getCusinename() {
            return cusinename;
        }

        public void setCusinename(String cusinename) {
            this.cusinename = cusinename;
        }

    }

    public class Documenttypemakeit {

        @SerializedName("documenttypeid")
        @Expose
        private Integer documenttypeid;
        @SerializedName("documenttype")
        @Expose
        private String documenttype;

        public Integer getDocumenttypeid() {
            return documenttypeid;
        }

        public void setDocumenttypeid(Integer documenttypeid) {
            this.documenttypeid = documenttypeid;
        }

        public String getDocumenttype() {
            return documenttype;
        }

        public void setDocumenttype(String documenttype) {
            this.documenttype = documenttype;
        }

    }

    public class Foodcycle {

        @SerializedName("foodcycleStatusid")
        @Expose
        private Integer foodcycleStatusid;
        @SerializedName("foodcycle")
        @Expose
        private String foodcycle;

        public Integer getFoodcycleStatusid() {
            return foodcycleStatusid;
        }

        public void setFoodcycleStatusid(Integer foodcycleStatusid) {
            this.foodcycleStatusid = foodcycleStatusid;
        }

        public String getFoodcycle() {
            return foodcycle;
        }

        public void setFoodcycle(String foodcycle) {
            this.foodcycle = foodcycle;
        }

    }

    public class Foodtype {

        @SerializedName("vegtypeid")
        @Expose
        private Integer vegtypeid;
        @SerializedName("vegtype")
        @Expose
        private String vegtype;

        public Integer getVegtypeid() {
            return vegtypeid;
        }

        public void setVegtypeid(Integer vegtypeid) {
            this.vegtypeid = vegtypeid;
        }

        public String getVegtype() {
            return vegtype;
        }

        public void setVegtype(String vegtype) {
            this.vegtype = vegtype;
        }

    }

    public class Liveproductstatus {

        @SerializedName("active_statusid")
        @Expose
        private Integer activeStatusid;
        @SerializedName("active_status")
        @Expose
        private String activeStatus;

        public Integer getActiveStatusid() {
            return activeStatusid;
        }

        public void setActiveStatusid(Integer activeStatusid) {
            this.activeStatusid = activeStatusid;
        }

        public String getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(String activeStatus) {
            this.activeStatus = activeStatus;
        }

    }

    public class Moveonlinestatus {

        @SerializedName("moveonlinestatusid")
        @Expose
        private Integer moveonlinestatusid;
        @SerializedName("moveonlinestatus")
        @Expose
        private String moveonlinestatus;

        public Integer getMoveonlinestatusid() {
            return moveonlinestatusid;
        }

        public void setMoveonlinestatusid(Integer moveonlinestatusid) {
            this.moveonlinestatusid = moveonlinestatusid;
        }

        public String getMoveonlinestatus() {
            return moveonlinestatus;
        }

        public void setMoveonlinestatus(String moveonlinestatus) {
            this.moveonlinestatus = moveonlinestatus;
        }

    }


    public class Orderstatus {

        @SerializedName("OrderStatusid")
        @Expose
        private Integer orderStatusid;
        @SerializedName("OrderStatus")
        @Expose
        private String orderStatus;

        public Integer getOrderStatusid() {
            return orderStatusid;
        }

        public void setOrderStatusid(Integer orderStatusid) {
            this.orderStatusid = orderStatusid;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

    }


    public class Ordertype {

        @SerializedName("ordertypeid")
        @Expose
        private Integer ordertypeid;
        @SerializedName("ordertype")
        @Expose
        private String ordertype;

        public Integer getOrdertypeid() {
            return ordertypeid;
        }

        public void setOrdertypeid(Integer ordertypeid) {
            this.ordertypeid = ordertypeid;
        }

        public String getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(String ordertype) {
            this.ordertype = ordertype;
        }

    }


    public class Paymentstatus {

        @SerializedName("paymentstatusid")
        @Expose
        private Integer paymentstatusid;
        @SerializedName("paymentstatus")
        @Expose
        private String paymentstatus;

        public Integer getPaymentstatusid() {
            return paymentstatusid;
        }

        public void setPaymentstatusid(Integer paymentstatusid) {
            this.paymentstatusid = paymentstatusid;
        }

        public String getPaymentstatus() {
            return paymentstatus;
        }

        public void setPaymentstatus(String paymentstatus) {
            this.paymentstatus = paymentstatus;
        }

    }

    public class Paymenttype {

        @SerializedName("paymenttypeid")
        @Expose
        private Integer paymenttypeid;
        @SerializedName("paymenttype")
        @Expose
        private String paymenttype;

        public Integer getPaymenttypeid() {
            return paymenttypeid;
        }

        public void setPaymenttypeid(Integer paymenttypeid) {
            this.paymenttypeid = paymenttypeid;
        }

        public String getPaymenttype() {
            return paymenttype;
        }

        public void setPaymenttype(String paymenttype) {
            this.paymenttype = paymenttype;
        }

    }


    public class Qualitycheck {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("description")
        @Expose
        private String description;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

    public class Qualitycheckenabletype {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("description")
        @Expose
        private String description;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }


    public class Regionlist {

        @SerializedName("regionid")
        @Expose
        private Integer regionid;
        @SerializedName("regionname")
        @Expose
        private String regionname;

        public Integer getRegionid() {
            return regionid;
        }

        public void setRegionid(Integer regionid) {
            this.regionid = regionid;
        }

        public String getRegionname() {
            return regionname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
        }

    }


    public class Registrationstatus {

        @SerializedName("registrationStatusid")
        @Expose
        private Integer registrationStatusid;
        @SerializedName("registrationStatus")
        @Expose
        private String registrationStatus;

        public Integer getRegistrationStatusid() {
            return registrationStatusid;
        }

        public void setRegistrationStatusid(Integer registrationStatusid) {
            this.registrationStatusid = registrationStatusid;
        }

        public String getRegistrationStatus() {
            return registrationStatus;
        }

        public void setRegistrationStatus(String registrationStatus) {
            this.registrationStatus = registrationStatus;
        }

    }

    public class Result {

        @SerializedName("regionlist")
        @Expose
        private List<Regionlist> regionlist = null;
        @SerializedName("cuisinelist")
        @Expose
        private List<Cuisinelist> cuisinelist = null;
        @SerializedName("sort")
        @Expose
        private List<Sort> sort = null;
        @SerializedName("registrationStatus")
        @Expose
        private List<Registrationstatus> registrationStatus = null;
        @SerializedName("foodcycle")
        @Expose
        private List<Foodcycle> foodcycle = null;
        @SerializedName("salesfollowupstatus")
        @Expose
        private List<Salesfollowupstatus> salesfollowupstatus = null;
        @SerializedName("OrderStatus")
        @Expose
        private List<Orderstatus> orderStatus = null;
        @SerializedName("paymenttype")
        @Expose
        private List<Paymenttype> paymenttype = null;
        @SerializedName("paymentstatus")
        @Expose
        private List<Paymentstatus> paymentstatus = null;
        @SerializedName("ordertype")
        @Expose
        private List<Ordertype> ordertype = null;
        @SerializedName("liveproductstatus")
        @Expose
        private List<Liveproductstatus> liveproductstatus = null;
        @SerializedName("foodtype")
        @Expose
        private List<Foodtype> foodtype = null;
        @SerializedName("usertype")
        @Expose
        private List<Usertype> usertype = null;
        @SerializedName("documenttypemakeit")
        @Expose
        private List<Documenttypemakeit> documenttypemakeit = null;
        @SerializedName("moveonlinestatus")
        @Expose
        private List<Moveonlinestatus> moveonlinestatus = null;
        @SerializedName("qualitycheck")
        @Expose
        private List<Qualitycheck> qualitycheck = null;
        @SerializedName("qualitycheckenabletype")
        @Expose
        private List<Qualitycheckenabletype> qualitycheckenabletype = null;
        @SerializedName("addresstype")
        @Expose
        private List<Addresstype> addresstype = null;
        @SerializedName("appuserstype")
        @Expose
        private List<Appuserstype> appuserstype = null;

        public List<Regionlist> getRegionlist() {
            return regionlist;
        }

        public void setRegionlist(List<Regionlist> regionlist) {
            this.regionlist = regionlist;
        }

        public List<Cuisinelist> getCuisinelist() {
            return cuisinelist;
        }

        public void setCuisinelist(List<Cuisinelist> cuisinelist) {
            this.cuisinelist = cuisinelist;
        }

        public List<Sort> getSort() {
            return sort;
        }

        public void setSort(List<Sort> sort) {
            this.sort = sort;
        }

        public List<Registrationstatus> getRegistrationStatus() {
            return registrationStatus;
        }

        public void setRegistrationStatus(List<Registrationstatus> registrationStatus) {
            this.registrationStatus = registrationStatus;
        }

        public List<Foodcycle> getFoodcycle() {
            return foodcycle;
        }

        public void setFoodcycle(List<Foodcycle> foodcycle) {
            this.foodcycle = foodcycle;
        }

        public List<Salesfollowupstatus> getSalesfollowupstatus() {
            return salesfollowupstatus;
        }

        public void setSalesfollowupstatus(List<Salesfollowupstatus> salesfollowupstatus) {
            this.salesfollowupstatus = salesfollowupstatus;
        }

        public List<Orderstatus> getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(List<Orderstatus> orderStatus) {
            this.orderStatus = orderStatus;
        }

        public List<Paymenttype> getPaymenttype() {
            return paymenttype;
        }

        public void setPaymenttype(List<Paymenttype> paymenttype) {
            this.paymenttype = paymenttype;
        }

        public List<Paymentstatus> getPaymentstatus() {
            return paymentstatus;
        }

        public void setPaymentstatus(List<Paymentstatus> paymentstatus) {
            this.paymentstatus = paymentstatus;
        }

        public List<Ordertype> getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(List<Ordertype> ordertype) {
            this.ordertype = ordertype;
        }

        public List<Liveproductstatus> getLiveproductstatus() {
            return liveproductstatus;
        }

        public void setLiveproductstatus(List<Liveproductstatus> liveproductstatus) {
            this.liveproductstatus = liveproductstatus;
        }

        public List<Foodtype> getFoodtype() {
            return foodtype;
        }

        public void setFoodtype(List<Foodtype> foodtype) {
            this.foodtype = foodtype;
        }

        public List<Usertype> getUsertype() {
            return usertype;
        }

        public void setUsertype(List<Usertype> usertype) {
            this.usertype = usertype;
        }

        public List<Documenttypemakeit> getDocumenttypemakeit() {
            return documenttypemakeit;
        }

        public void setDocumenttypemakeit(List<Documenttypemakeit> documenttypemakeit) {
            this.documenttypemakeit = documenttypemakeit;
        }

        public List<Moveonlinestatus> getMoveonlinestatus() {
            return moveonlinestatus;
        }

        public void setMoveonlinestatus(List<Moveonlinestatus> moveonlinestatus) {
            this.moveonlinestatus = moveonlinestatus;
        }

        public List<Qualitycheck> getQualitycheck() {
            return qualitycheck;
        }

        public void setQualitycheck(List<Qualitycheck> qualitycheck) {
            this.qualitycheck = qualitycheck;
        }

        public List<Qualitycheckenabletype> getQualitycheckenabletype() {
            return qualitycheckenabletype;
        }

        public void setQualitycheckenabletype(List<Qualitycheckenabletype> qualitycheckenabletype) {
            this.qualitycheckenabletype = qualitycheckenabletype;
        }

        public List<Addresstype> getAddresstype() {
            return addresstype;
        }

        public void setAddresstype(List<Addresstype> addresstype) {
            this.addresstype = addresstype;
        }

        public List<Appuserstype> getAppuserstype() {
            return appuserstype;
        }

        public void setAppuserstype(List<Appuserstype> appuserstype) {
            this.appuserstype = appuserstype;
        }

    }


    public class Salesfollowupstatus {

        @SerializedName("followupstatussid")
        @Expose
        private Integer followupstatussid;
        @SerializedName("followupstatus")
        @Expose
        private String followupstatus;

        public Integer getFollowupstatussid() {
            return followupstatussid;
        }

        public void setFollowupstatussid(Integer followupstatussid) {
            this.followupstatussid = followupstatussid;
        }

        public String getFollowupstatus() {
            return followupstatus;
        }

        public void setFollowupstatus(String followupstatus) {
            this.followupstatus = followupstatus;
        }

    }


    public class Sort {

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


    public class Usertype {

        @SerializedName("usertypeid")
        @Expose
        private Integer usertypeid;
        @SerializedName("usertype")
        @Expose
        private String usertype;

        public Integer getUsertypeid() {
            return usertypeid;
        }

        public void setUsertypeid(Integer usertypeid) {
            this.usertypeid = usertypeid;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

    }
}
