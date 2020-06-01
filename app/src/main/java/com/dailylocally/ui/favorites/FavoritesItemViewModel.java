package com.dailylocally.ui.favorites;


import androidx.databinding.ObservableField;

import com.dailylocally.ui.cart.CartResponse;

public class FavoritesItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();

    public final FavoritesViewModelListener mListener;
    private final CartResponse.Cartdetail cartdetail;

    public FavoritesItemViewModel(CartResponse.Cartdetail cartdetail, FavoritesViewModelListener mListener) {
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
