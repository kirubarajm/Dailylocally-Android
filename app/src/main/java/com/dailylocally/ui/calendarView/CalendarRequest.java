package com.dailylocally.ui.calendarView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalendarRequest {
    @SerializedName("userid")
    @Expose
    public String userid;
    @SerializedName("year")
    @Expose
    public String year;
    @SerializedName("month")
    @Expose
    public String month;


    public CalendarRequest(String userid, String year, String month) {
        this.userid = userid;
        this.year = year;
        this.month = month;
    }
}
