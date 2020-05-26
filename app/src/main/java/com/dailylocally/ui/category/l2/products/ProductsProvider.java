package com.dailylocally.ui.category.l2.products;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProductsProvider {

    @ContributesAndroidInjector(modules = ProductsModule.class)
    abstract ProductsFragment provideHomeFragmentFactory();
}
