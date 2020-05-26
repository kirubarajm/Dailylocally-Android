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
       name.set(result.getName());

       // name.set("Abcdefghijklmnopqrstuvwxyz a b c d e f g h i j k l m n o p q r s t u v w x y z ");
    }


    public void onItemClick() {
      // if (coupon.isClickable())
            mListener.onItemClick(result);

    }


    public interface CategoriesItemViewModelListener {
        void onItemClick(HomepageResponse.Result result);
    }

}
