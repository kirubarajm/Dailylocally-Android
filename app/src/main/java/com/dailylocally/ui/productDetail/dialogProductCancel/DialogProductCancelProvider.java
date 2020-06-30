package com.dailylocally.ui.productDetail.dialogProductCancel;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DialogProductCancelProvider {

    @ContributesAndroidInjector(modules = DialogProductCancelModule.class)
    abstract DialogProductCancel provideRateKitchenFactory();
}
