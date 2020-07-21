package com.dailylocally.ui.signup.faqs;


import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;
import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class FaqFragmentModule {

    @Provides
    FaqFragmentViewModel provideFaqViewModel(DataManager dataManager)
    {
        return new FaqFragmentViewModel(dataManager);
    }

    @Provides
    FaqsAdapter provideFaqsAdapter() {
        return new FaqsAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(FaqActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
