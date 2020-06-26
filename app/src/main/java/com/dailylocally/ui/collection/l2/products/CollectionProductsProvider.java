package com.dailylocally.ui.collection.l2.products;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CollectionProductsProvider {

    @ContributesAndroidInjector(modules = CollectionProductsModule.class)
    abstract CollectionProductFragment provideHomeFragmentFactory();
}
