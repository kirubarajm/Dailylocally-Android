package com.dailylocally.ui.productDetail.dialogProductCancel;


import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class DialogProductCancelModule {

    @Provides
    DialogProductCancelViewModel provideRateKitchenViewModel(DataManager dataManager) {
        return new DialogProductCancelViewModel(dataManager);
    }
}
