package com.dailylocally.utilities.datepicker;


import com.dailylocally.ui.calendarView.CalendarMonthResponse;

import java.util.List;

public interface DatePickerNavigator {

    void handleError(Throwable throwable);

    void goBack();

}
