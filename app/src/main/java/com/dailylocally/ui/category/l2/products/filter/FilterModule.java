package com.dailylocally.ui.category.l2.products.filter;



import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class FilterModule {


    DataManager dataManager;
    @Provides
    FilterViewModel provideFilterViewModel(DataManager dataManager) {
        this.dataManager=dataManager;
        return new FilterViewModel(dataManager);
    }

    /*@Provides
    FilterAdapter provideFilterAdapter() {
        return new FilterAdapter(new ArrayList<>(),dataManager);
    }*/


    @Provides
    LinearLayoutManager provideLinearLayoutManager(FilterFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
