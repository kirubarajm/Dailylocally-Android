package com.dailylocally.ui.signup.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserDetailsResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("userid")
        @Expose
        private String userid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phoneno")
        @Expose
        private String phoneno;
        @SerializedName("Locality")
        @Expose
        private Object locality;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("virtualkey")
        @Expose
        private Integer virtualkey;
        @SerializedName("gender")
        @Expose
        private Integer gender;
        @SerializedName("regionid")
        @Expose
        private Integer regionid;
        @SerializedName("regionname")
        @Expose
        private String regionname;

  @SerializedName("other_region")
        @Expose
        private String otherRegion;


        @SerializedName("hometownid")
        @Expose
        public Integer hometownId=0;

        @SerializedName("other_hometown")
        @Expose
        public String otherHometown;

   @SerializedName("hometownname")
        @Expose
        public String hometownName;


        public Integer getHometownId() {
            return hometownId;
        }

        public void setHometownId(Integer hometownId) {
            this.hometownId = hometownId;
        }

        public String getHometownName() {
            return hometownName;
        }

        public void setHometownName(String hometownName) {
            this.hometownName = hometownName;
        }

        public String getOtherHometown() {
            return otherHometown;
        }

        public void setOtherHometown(String otherHometown) {
            this.otherHometown = otherHometown;
        }

        public String getOtherRegion() {
            return otherRegion;
        }

        public void setOtherRegion(String otherRegion) {
            this.otherRegion = otherRegion;
        }

        public Integer getRegionid() {
            return regionid;
        }

        public void setRegionid(Integer regionid) {
            this.regionid = regionid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneno() {
            return phoneno;
        }

        public void setPhoneno(String phoneno) {
            this.phoneno = phoneno;
        }

        public Object getLocality() {
            return locality;
        }

        public void setLocality(Object locality) {
            this.locality = locality;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getVirtualkey() {
            return virtualkey;
        }

        public void setVirtualkey(Integer virtualkey) {
            this.virtualkey = virtualkey;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public String getRegionname() {
            return regionname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
        }
    }
}
