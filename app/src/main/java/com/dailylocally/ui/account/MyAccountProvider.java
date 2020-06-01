package com.dailylocally.ui.account;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MyAccountProvider {

    @ContributesAndroidInjector(modules = MyAccountModule.class)
    abstract MyAccountFragment provideMyAccountFragmentFactory();

}
