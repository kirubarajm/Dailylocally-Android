package com.dailylocally.ui.collection.l2.products.sort;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SortProvider {

    @ContributesAndroidInjector(modules = SortModule.class)
    abstract SortFragment provideFilterFragmentFactory();

}
