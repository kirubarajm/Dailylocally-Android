package com.dailylocally.ui.transactionHistory;


import androidx.databinding.ObservableField;

import com.dailylocally.ui.cart.CartResponse;

public class TransactionHistoryItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();

    public final FavoritesViewModelListener mListener;
    private final CartResponse.Cartdetail cartdetail;

    public TransactionHistoryItemViewModel(CartResponse.Cartdetail cartdetail, FavoritesViewModelListener mListener) {
        this.cartdetail = cartdetail;
        this.mListener = mListener;

        title.set(cartdetail.getTitle());

    }


    public void infoClick() {
        mListener.onItemClick(cartdetail);
    }

    public interface FavoritesViewModelListener {
        void onItemClick(CartResponse.Cartdetail cartdetail);
    }

}
