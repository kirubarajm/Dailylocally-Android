package com.dailylocally.ui.cart;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CartProvider {

    @ContributesAndroidInjector(modules = CartModule.class)
    abstract CartFragment provideCartActivityFactory();

}
