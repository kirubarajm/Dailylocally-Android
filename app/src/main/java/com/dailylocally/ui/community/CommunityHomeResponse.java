package com.dailylocally.ui.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommunityHomeResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;

    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public List<Result> getResult() {
        return result;
    }

    public class About {

        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("des")
        @Expose
        public String des;

        public String getTitle() {
            return title;
        }

        public String getDes() {
            return des;
        }
    }


    public class CatList {

        @SerializedName("title ")
        @Expose
        public String title;
        @SerializedName("des")
        @Expose
        public String des;
        @SerializedName("image_url ")
        @Expose
        public String imageUrl;

        public String getTitle() {
            return title;
        }

        public String getDes() {
            return des;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }

    public class Event {

        @SerializedName("image_url")
        @Expose
        public String imageUrl;
        @SerializedName("topic")
        @Expose
        public String topic;

        public String getImageUrl() {
            return imageUrl;
        }

        public String getTopic() {
            return topic;
        }
    }

    public class Result {

        @SerializedName("event")
        @Expose
        public Event event;
        @SerializedName("whatsapp ")
        @Expose
        public Whatsapp whatsapp;
        @SerializedName("sneak_peak")
        @Expose
        public SneakPeak sneakPeak;
        @SerializedName("cat_list")
        @Expose
        public CatList catList ;
        @SerializedName("about")
        @Expose
        public About about ;


        public Event getEvent() {
            return event;
        }

        public Whatsapp getWhatsapp() {
            return whatsapp;
        }

        public SneakPeak getSneakPeak() {
            return sneakPeak;
        }

        public CatList getCatList() {
            return catList;
        }

        public About getAbout() {
            return about;
        }
    }


    public class SneakPeak {

        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("des")
        @Expose
        public String des;
        @SerializedName("video_url ")
        @Expose
        public String videoUrl;


        public String getTitle() {
            return title;
        }

        public String getDes() {
            return des;
        }

        public String getVideoUrl() {
            return videoUrl;
        }
    }


    public class Whatsapp {

        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("des")
        @Expose
        public String des;
        @SerializedName("group_url ")
        @Expose
        public String groupUrl;


        public String getTitle() {
            return title;
        }

        public String getDes() {
            return des;
        }

        public String getGroupUrl() {
            return groupUrl;
        }
    }


}