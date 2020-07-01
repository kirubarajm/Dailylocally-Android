package com.dailylocally.ui.transactionHistory.view;


import androidx.databinding.ObservableField;

public class TransactionBillDetailItemViewModel {

    public final ObservableField<String> txt = new ObservableField<>();
    public final ObservableField<String> value = new ObservableField<>();

    public final TransactionHistoryViewModelListener mListener;
    private final TransactionViewResponse.Result.Cartdetail cartdetail;

    public TransactionBillDetailItemViewModel(TransactionViewResponse.Result.Cartdetail cartdetail, TransactionHistoryViewModelListener mListener) {
        this.cartdetail = cartdetail;
        this.mListener = mListener;

        txt.set(String.valueOf(cartdetail.getTitle()));
        value.set(cartdetail.getCharges());
    }

    public void viewInCalendarClick() {
        mListener.onViewCalendarClick(cartdetail);
    }

    public interface TransactionHistoryViewModelListener {
        void onViewCalendarClick(TransactionViewResponse.Result.Cartdetail cartdetail);
    }

}
