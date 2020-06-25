package com.dailylocally.ui.transactionHistory;


import androidx.databinding.ObservableField;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;


public class TransactionHistoryViewModel extends BaseViewModel<TransactionHistoryNavigator> {

    public final ObservableField<String> version = new ObservableField<>();

    public TransactionHistoryViewModel(DataManager dataManager) {
        super(dataManager);
        getDataManager().appStartedAgain(true);
    }

    public void goBack(){
        if (getNavigator()!=null){
            getNavigator().goBack();
        }
    }

}
