package com.dailylocally.ui.aboutus;


import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class AboutUsModule {

    @Provides
    AboutUsViewModel provideFaqViewModel(DataManager dataManager)
    {
        return new AboutUsViewModel(dataManager);
    }

    @Provides
    AboutUsAdapter provideFaqsAdapter() {
        return new AboutUsAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AboutUsActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
