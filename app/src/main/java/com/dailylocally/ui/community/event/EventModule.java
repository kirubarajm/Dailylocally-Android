package com.dailylocally.ui.community.event;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.community.CommunityPostListAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class EventModule {


    @Provides
    EventViewModel provideOrderPlacedViewModel(DataManager dataManager) {
        return new EventViewModel(dataManager);
    }
    @Provides
    EventListAdapter provideEventListAdapter() {
        return new EventListAdapter(new ArrayList<>());
    }

}
