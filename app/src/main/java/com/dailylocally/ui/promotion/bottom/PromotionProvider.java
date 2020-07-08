package com.dailylocally.ui.promotion.bottom;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class PromotionProvider {

    @ContributesAndroidInjector(modules = PromotionModule.class)
    abstract PromotionFragment providePromotionFragmentFactory();

}
