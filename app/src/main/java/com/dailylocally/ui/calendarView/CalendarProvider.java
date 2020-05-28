package com.dailylocally.ui.calendarView;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CalendarProvider {

    @ContributesAndroidInjector(modules = CalendarModule.class)
    abstract CalendarFragment provideCalendarFragmentFactory();
}
