package com.dailylocally.ui.transactionHistory;



import com.dailylocally.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class TransactionHistoryModule {

    @Provides
    TransactionHistoryViewModel provideFavoritesViewModel(DataManager dataManager) {
        return new TransactionHistoryViewModel(dataManager);
    }

}
