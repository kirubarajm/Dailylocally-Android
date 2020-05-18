package com.dailylocally.ui.home;


import androidx.databinding.ObservableField;

public class CategoriesItemViewModel {


    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();


    public final CategoriesItemViewModelListener mListener;
    private final HomepageResponse.Result result;


    public CategoriesItemViewModel(CategoriesItemViewModelListener mListener, HomepageResponse.Result result) {
        this.mListener = mListener;
        this.result = result;
        image.set(result.getImage());
    }


    public void onItemClick() {
      // if (coupon.isClickable())
            mListener.onItemClick(result);

    }


    public interface CategoriesItemViewModelListener {
        void onItemClick(HomepageResponse.Result result);
    }

}
