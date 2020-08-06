package com.dailylocally.ui.calendarView;


import androidx.databinding.ObservableField;

public class CalendarDayWiseItemViewModel {

    public final ObservableField<String> productName = new ObservableField<>();
    public final ObservableField<String> quantity = new ObservableField<>();
    public final ObservableField<String> units = new ObservableField<>();
    public final ObservableField<String> weight = new ObservableField<>();
    public final ObservableField<String> unitWeight = new ObservableField<>();

    public final CalendarItemViewModelListener mListener;
    private final CalendarDayWiseResponse.Result.Item result;

    public CalendarDayWiseItemViewModel(CalendarItemViewModelListener mListener, CalendarDayWiseResponse.Result.Item result) {
        this.mListener = mListener;
        this.result = result;
        productName.set(result.getProductName());
        quantity.set( result.getQuantityInfo() +" "+ result.getPkts() );
        units.set(result.getUnit());
        weight.set(String.valueOf(result.getPacketSize()));
        unitWeight.set(result.getPacketSize()+ " " +result.getUnit());
    }

    public void onItemClick() {
            mListener.onItemClick(result);
    }

    public interface CalendarItemViewModelListener {
        void onItemClick(CalendarDayWiseResponse.Result.Item result);
    }
}
