package com.dailylocally.ui.collection.l2.products.filter;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FilterProvider {

    @ContributesAndroidInjector(modules = FilterModule.class)
    abstract FilterFragment provideFilterFragmentFactory();

}
