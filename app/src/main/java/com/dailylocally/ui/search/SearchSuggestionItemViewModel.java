package com.dailylocally.ui.search;


import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

public class SearchSuggestionItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> type = new ObservableField<>();

    public final ObservableBoolean isFavourite = new ObservableBoolean();

    public final SearchItemViewModelListener mListener;
    private final QuickSearchResponse.Result.ProductsList result;


    public SearchSuggestionItemViewModel(SearchItemViewModelListener mListener, QuickSearchResponse.Result.ProductsList result) {

        this.mListener = mListener;
        this.result = result;
        this.title.set(result.getProductname());
        this.type.set(result.getPacketsize()+" "+result.getUnit()+", in "+result.getName());
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

        void onItemClick(QuickSearchResponse.Result.ProductsList result);


    }

}
