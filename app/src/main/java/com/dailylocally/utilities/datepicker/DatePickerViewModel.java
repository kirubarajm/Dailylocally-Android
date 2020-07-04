package com.dailylocally.utilities.datepicker;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.calendarView.CalendarDayWiseRequest;
import com.dailylocally.ui.calendarView.CalendarDayWiseResponse;
import com.dailylocally.ui.calendarView.CalendarMonthResponse;
import com.dailylocally.ui.calendarView.CalendarMonthWiseRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DatePickerViewModel extends BaseViewModel<DatePickerNavigator> {

    public DatePickerViewModel(DataManager dataManager) {
        super(dataManager);

    }

    public void goBack(){
        if (getNavigator()!=null){
            getNavigator().goBack();
        }
    }
}