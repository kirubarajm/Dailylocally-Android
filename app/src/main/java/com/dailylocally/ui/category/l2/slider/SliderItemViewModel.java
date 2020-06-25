package com.dailylocally.ui.category.l2.slider;

import androidx.databinding.ObservableField;

import com.dailylocally.ui.category.l2.L2CategoryResponse;


public class SliderItemViewModel {


    public final ObservableField<String> url = new ObservableField<>();


    public final SliderItemViewModelListener mListener;
    private final L2CategoryResponse.GetSubCatImage getSubCatImage;

    public SliderItemViewModel(SliderItemViewModelListener mListener, L2CategoryResponse.GetSubCatImage getSubCatImage) {

        this.mListener = mListener;
        this.getSubCatImage = getSubCatImage;

        url.set(getSubCatImage.getImageUrl());

    }


    public void onItemClick() {

     mListener.sliderItemClick(getSubCatImage);
    }


    interface SliderItemViewModelListener {

        void sliderItemClick(L2CategoryResponse.GetSubCatImage getSubCatImage);
    }


}
