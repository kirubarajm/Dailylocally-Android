package com.dailylocally.ui.rating;


import androidx.databinding.ObservableField;

import com.dailylocally.ui.calendarView.CalendarDayWiseResponse;

public class RatingDayWiseItemViewModel {

    public final ObservableField<String> productName = new ObservableField<>();
    public final ObservableField<String> quantity = new ObservableField<>();
    public final ObservableField<String> units = new ObservableField<>();
    public final ObservableField<String> weight = new ObservableField<>();
    public final ObservableField<String> unitWeight = new ObservableField<>();

    public final CalendarItemViewModelListener mListener;
    private final CalendarDayWiseResponse.Result.Item result;

    public RatingDayWiseItemViewModel(CalendarItemViewModelListener mListener, CalendarDayWiseResponse.Result.Item result) {
        this.mListener = mListener;
        this.result = result;
        productName.set(result.getProductName());
        quantity.set(String.valueOf(result.getQuantity()));
        units.set(result.getUnit());
        weight.set(String.valueOf(result.getWeight()));
        unitWeight.set(result.getWeight()+result.getUnit());
    }

    public void onItemClick() {
            mListener.onItemClick(result);
    }

    public interface CalendarItemViewModelListener {
        void onItemClick(CalendarDayWiseResponse.Result.Item result);
    }
}
