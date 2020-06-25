package com.dailylocally.ui.transactionHistory;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.dailylocally.data.DataManager;
import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class TransactionHistoryModule {

    @Provides
    TransactionHistoryViewModel provideFavoritesViewModel(DataManager dataManager) {
        return new TransactionHistoryViewModel(dataManager);
    }

    @Provides
    TransactionHistoryAdapter provideTransactionHistoryAdapter() {
        return new TransactionHistoryAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(TransactionHistoryActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
