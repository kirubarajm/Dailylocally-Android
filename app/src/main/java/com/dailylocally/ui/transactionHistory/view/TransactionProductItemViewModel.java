package com.dailylocally.ui.transactionHistory.view;


import androidx.databinding.ObservableField;

public class TransactionProductItemViewModel {

    public final ObservableField<String> productName = new ObservableField<>();
    public final ObservableField<String> itemsCount = new ObservableField<>();
    public final ObservableField<String> unitWeight = new ObservableField<>();





    public final TransactionHistoryViewModelListener mListener;
    private final TransactionViewResponse.Result.Item cartdetail;

    public TransactionProductItemViewModel(TransactionViewResponse.Result.Item cartdetail, TransactionHistoryViewModelListener mListener) {
        this.cartdetail = cartdetail;
        this.mListener = mListener;

        productName.set(String.valueOf(cartdetail.getProductName()));
        unitWeight.set(cartdetail.getQuantityInfo()+" "+cartdetail.getPkts());
        //itemsCount.set();

    }

    public void viewInCalendarClick() {
        mListener.onViewCalendarClick(cartdetail);
    }

    public interface TransactionHistoryViewModelListener {
        void onViewCalendarClick(TransactionViewResponse.Result.Item cartdetail);
    }

}
