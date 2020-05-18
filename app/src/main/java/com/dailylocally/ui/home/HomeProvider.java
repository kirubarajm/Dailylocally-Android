package com.dailylocally.ui.home;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeProvider {

    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeFragment provideHomeFragmentFactory();
}
