package com.dailylocally.utilities.datepicker;


import android.content.Context;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.calendarView.CalendarDayWiseAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class DatePickerModule {

    Context context;

    @Provides
    DatePickerViewModel provideCalendarViewModel(DataManager dataManager) {
        return new DatePickerViewModel(dataManager);
    }



}
