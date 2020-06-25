package com.dailylocally.ui.transactionHistory;

public interface TransactionHistoryNavigator {

    void handleError(Throwable throwable);

    void goBack();

}
