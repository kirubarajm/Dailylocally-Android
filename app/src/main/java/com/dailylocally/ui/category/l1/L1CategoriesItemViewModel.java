package com.dailylocally.ui.category.l1;


import androidx.databinding.ObservableField;

import com.dailylocally.ui.home.HomepageResponse;

public class L1CategoriesItemViewModel {


    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();


    public final L1CategoriesItemViewModelListener mListener;
    private final L1CategoryResponse.Result result;


    public L1CategoriesItemViewModel(L1CategoriesItemViewModelListener mListener, L1CategoryResponse.Result result) {
        this.mListener = mListener;
        this.result = result;
        name.set(result.getName());
    }


    public void onItemClick() {
      // if (coupon.isClickable())
            mListener.onItemClick(result);

    }


    public interface L1CategoriesItemViewModelListener {
        void onItemClick(L1CategoryResponse.Result result);
    }

}
