package com.dailylocally.ui.joinCommunity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchCommunityRequest {

    @SerializedName("search")
    @Expose
    public String search;

    public SearchCommunityRequest(String search) {
        this.search = search;
    }
}
