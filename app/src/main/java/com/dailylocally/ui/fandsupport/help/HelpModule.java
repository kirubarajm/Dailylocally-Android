package com.dailylocally.ui.fandsupport.help;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;
import com.dailylocally.utilities.chat.IssuesAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class HelpModule {

    @Provides
    HelpViewModel provideOrdersCancelViewModel(DataManager dataManager) {
        return new HelpViewModel(dataManager);
    }
    @Provides
    LinearLayoutManager provideLinearLayoutManager(HelpActivity activity) {
        return new LinearLayoutManager(activity);
    }
    @Provides
    IssuesAdapter provideIssuesAdapter() {
        return new IssuesAdapter(new ArrayList<>());
    }
}
