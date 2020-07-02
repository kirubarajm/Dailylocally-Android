package com.dailylocally.ui.calendarView;

import com.dailylocally.ui.cart.CartFragment;
import com.dailylocally.ui.cart.CartModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CalendarProvider {

    @ContributesAndroidInjector(modules = CalendarModule.class)
    abstract CalendarFragment provideCalendarFactory();

}
