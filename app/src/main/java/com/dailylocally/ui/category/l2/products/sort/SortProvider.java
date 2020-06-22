package com.dailylocally.ui.category.l2.products.sort;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SortProvider {

    @ContributesAndroidInjector(modules = SortModule.class)
    abstract SortFragment provideFilterFragmentFactory();

}
