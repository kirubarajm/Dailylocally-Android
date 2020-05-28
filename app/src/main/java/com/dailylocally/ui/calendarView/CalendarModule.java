package com.dailylocally.ui.calendarView;


import android.content.Context;

import com.dailylocally.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class CalendarModule {

    Context context;

    @Provides
    CalendarViewModel provideCalendarViewModel(DataManager dataManager) {
        return new CalendarViewModel(dataManager);
    }

    @Provides
    CalendarDayWiseAdapter provideCalendarAdapter() {
        return new CalendarDayWiseAdapter(new ArrayList<>());
    }

}
