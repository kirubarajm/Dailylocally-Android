package com.dailylocally.ui.calendarView;


import androidx.databinding.ObservableField;

public class CalendarItemViewModel {

    public final ObservableField<String> name = new ObservableField<>();

    public final CalendarItemViewModelListener mListener;
    private final CalendarMonthResponse.Result result;

    public CalendarItemViewModel(CalendarItemViewModelListener mListener, CalendarMonthResponse.Result result) {
        this.mListener = mListener;
        this.result = result;
       name.set(String.valueOf(result.getUserid()));
    }

    public void onItemClick() {
            mListener.onItemClick(result);
    }

    public interface CalendarItemViewModelListener {
        void onItemClick(CalendarMonthResponse.Result result);
    }
}
