package com.dailylocally.ui.calendarView;


import androidx.databinding.ObservableField;

public class CalendarDayWiseItemViewModel {

    public final ObservableField<String> productName = new ObservableField<>();
    public final ObservableField<String> quantity = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();

    public final CalendarItemViewModelListener mListener;
    private final CalendarDayWiseResponse.Result.Item result;

    public CalendarDayWiseItemViewModel(CalendarItemViewModelListener mListener, CalendarDayWiseResponse.Result.Item result) {
        this.mListener = mListener;
        this.result = result;
       productName.set("Product name : "+result.getProductName());
       quantity.set("Quantity : "+result.getQuantity());
       price.set("Price : Rs."+result.getPrice());
    }

    public void onItemClick() {
            mListener.onItemClick(result);
    }

    public interface CalendarItemViewModelListener {
        void onItemClick(CalendarDayWiseResponse.Result.Item result);
    }
}
