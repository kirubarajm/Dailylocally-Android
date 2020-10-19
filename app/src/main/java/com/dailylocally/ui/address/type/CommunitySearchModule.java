package com.dailylocally.ui.address.type;



import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.joinCommunity.CommunityActivity;
import com.dailylocally.ui.joinCommunity.CommunityAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class CommunitySearchModule {

    @Provides
    CommunitySearchViewModel provideSplashViewModel(DataManager dataManager) {
        return new CommunitySearchViewModel(dataManager);
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
