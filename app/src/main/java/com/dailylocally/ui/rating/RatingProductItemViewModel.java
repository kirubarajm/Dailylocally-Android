package com.dailylocally.ui.rating;

import androidx.databinding.ObservableField;

public class RatingProductItemViewModel {

    public final ObservableField<String> productName = new ObservableField<>();


    public final FavoritesViewModelListener mListener;
    private final RatingResponse.Result cartdetail;

    public RatingProductItemViewModel(RatingResponse.Result cartdetail, FavoritesViewModelListener mListener) {
        this.cartdetail = cartdetail;
        this.mListener = mListener;

        this.productName.set(cartdetail.getProductname());

    }

    public void onApplyClick() {
        mListener.onItemClick(cartdetail);
    }

    public interface FavoritesViewModelListener {
        void onItemClick(RatingResponse.Result cartdetail);
    }

}
