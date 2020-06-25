package com.dailylocally.ui.category.l2;



import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.category.l2.products.ProductListAdapter;
import com.dailylocally.ui.category.l2.slider.L2SliderAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class CategoryL2Module {

    DataManager dataManager;

    @Provides
    CategoryL2ViewModel provideCategoryL1ViewModel(DataManager dataManager) {
        this.dataManager = dataManager;
        return new CategoryL2ViewModel(dataManager);
    }

    @Provides
    L2SliderAdapter provideL2SliderAdapter() {
        return new L2SliderAdapter(new ArrayList<>(),dataManager);
    }
}

