package com.dailylocally.ui.calendarView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalendarDayWiseRequest {

    @SerializedName("userid")
    @Expose
    public String userid;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("month")
    @Expose
    public String month;


    public CalendarDayWiseRequest(String userid, String date, String month) {
        this.userid = userid;
        this.date = date;
        this.month = month;
    }
}
