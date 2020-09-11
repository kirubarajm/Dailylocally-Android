package com.dailylocally.ui.community.details;

import com.dailylocally.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class EventDetailsModule {


    @Provides
    EventDetailsViewModel provideOrderPlacedViewModel(DataManager dataManager) {
        return new EventDetailsViewModel(dataManager);
    }
    @Provides
    CommentsListAdapter provideEventListAdapter() {
        return new CommentsListAdapter(new ArrayList<>());
    }

}
