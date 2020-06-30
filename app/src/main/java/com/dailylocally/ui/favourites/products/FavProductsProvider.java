package com.dailylocally.ui.favourites.products;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FavProductsProvider {

    @ContributesAndroidInjector(modules = FavProductsModule.class)
    abstract FavProductFragment provideHomeFragmentFactory();
}
