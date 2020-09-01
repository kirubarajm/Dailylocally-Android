package com.dailylocally.ui.joinCommunity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentUploadResponse {
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;

    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public class Data {

        @SerializedName("ETag")
        @Expose
        public String eTag;
        @SerializedName("Location")
        @Expose
        public String location;
        @SerializedName("key")
        @Expose
        public String key;
        @SerializedName("Bucket")
        @Expose
        public String bucket;

        public String geteTag() {
            return eTag;
        }

        public String getLocation() {
            return location;
        }

        public String getKey() {
            return key;
        }

        public String getBucket() {
            return bucket;
        }
    }
}
