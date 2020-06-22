package com.dailylocally.ui.category.l2.products.sort;



import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class SortModule {


    DataManager dataManager;
    @Provides
    SortViewModel provideFilterViewModel(DataManager dataManager) {
        this.dataManager=dataManager;
        return new SortViewModel(dataManager);
    }

    @Provides
    SortAdapter provideFilterAdapter() {
        return new SortAdapter(new ArrayList<>(),dataManager);
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(SortFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
