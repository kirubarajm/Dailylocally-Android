package com.dailylocally.ui.calendarView;


import android.content.Context;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.home.CategoriesAdapter;
import com.dailylocally.ui.home.HomeViewModel;

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
    CalendarAdapter provideCalendarAdapter() {
        return new CalendarAdapter(new ArrayList<>());
    }

}
