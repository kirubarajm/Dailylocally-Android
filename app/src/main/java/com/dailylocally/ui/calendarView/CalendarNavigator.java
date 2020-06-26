package com.dailylocally.ui.calendarView;


import java.util.List;

public interface CalendarNavigator {

    void handleError(Throwable throwable);

    void success(List<CalendarMonthResponse.Result> results);

    void failure(String message);

    void ratingClick();

}
