package com.dailylocally.ui.search;


import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

public class SearchSubCategorytemViewModel {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> type = new ObservableField<>();

    public final ObservableBoolean isFavourite = new ObservableBoolean();

    public final SearchItemViewModelListener mListener;
    private final QuickSearchResponse.Result.SubcategoryList result;


    public SearchSubCategorytemViewModel(SearchItemViewModelListener mListener, QuickSearchResponse.Result.SubcategoryList result) {

        this.mListener = mListener;
        this.result = result;
        this.title.set(result.getSubCategory());

/*
        if (result.getType() == 1) {

            this.type.set("PRODUCT");


        } else if (result.getType() == 2) {
            this.type.set("CATEGORY");


        } else if (result.getType() == 3) {
            this.type.set("Other");
        }*/

    }


    public void onItemClick() {
        mListener.onItemClick(result);

    }

    public interface SearchItemViewModelListener {
        // void onItemClick(boolean completed_status, Object salesEmpId, int makeitUserId, String date, String name, String email, String phNum, String brandName, String address, String lat, String lng);

        void onItemClick(QuickSearchResponse.Result.SubcategoryList result);


    }

}
