package com.dailylocally.ui.search;

import com.dailylocally.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    SearchViewModel provideSearchViewModel(DataManager dataManager) {
        return new SearchViewModel(dataManager);
    }

    @Provides
    SearchAdapter provideSearchAdapter() {
        return new SearchAdapter(new ArrayList<>());
    }

}
