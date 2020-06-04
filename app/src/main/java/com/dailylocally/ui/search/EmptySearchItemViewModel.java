package com.dailylocally.ui.search;


import androidx.databinding.ObservableField;

public class EmptySearchItemViewModel {


    public final ObservableField<String> message = new ObservableField<>();

    public EmptySearchItemViewModel(String message) {
        this.message.set(message);
    }


}
