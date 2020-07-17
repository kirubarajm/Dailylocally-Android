package com.dailylocally.ui.coupons;


import androidx.databinding.ObservableField;

public class CouponsItemViewModel {

    public final ObservableField<String> couponName = new ObservableField<>();
    public final ObservableField<String> description = new ObservableField<>();
    public final ObservableField<String> offer1 = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> discount_percent = new ObservableField<>();

    public final FavoritesViewModelListener mListener;
    private final CouponsResponse.Result cartdetail;

    public CouponsItemViewModel(CouponsResponse.Result cartdetail, FavoritesViewModelListener mListener) {
        this.cartdetail = cartdetail;
        this.mListener = mListener;

        this.couponName.set(cartdetail.getCouponName());
        this.discount_percent.set(String.valueOf(cartdetail.getDiscountPercent()));
        this.description.set(cartdetail.getDescription());
        this.offer1.set(cartdetail.getValidityContent());
        this.title.set(cartdetail.getCouponTitle());


    }

    public void onApplyClick() {
        mListener.onItemClick(cartdetail);
    }

    public interface FavoritesViewModelListener {
        void onItemClick(CouponsResponse.Result cartdetail);
    }

}
