package com.dailylocally.ui.community.catlist;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CommunityCatProvider {

    @ContributesAndroidInjector(modules = CommunityCatModule.class)
    abstract CommunityCatFragment provideHomeFragmentFactory();
}
