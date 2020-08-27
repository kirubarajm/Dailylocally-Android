package com.dailylocally.ui.joinCommunity;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class CommunityActivityModule {

    @Provides
    CommunityActivityViewModel provideCommunityViewModel(DataManager dataManager) {
        return new CommunityActivityViewModel(dataManager);
    }

    @Provides
    CommunityAdapter provideCommunityAdapter() {
        return new CommunityAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(CommunityActivity activity) {
        return new LinearLayoutManager(activity);
    }

}
