package com.dailylocally.ui.community;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CommunityProvider {

    @ContributesAndroidInjector(modules = CommunityModule.class)
    abstract CommunityFragment provideCommunityFragmentFactory();
}
