package com.dailylocally.ui.category.viewall.products;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CatProductFragProvider {

    @ContributesAndroidInjector(modules = CatProductFragModule.class)
    abstract CatProductFragment provideHomeFragmentFactory();
}
