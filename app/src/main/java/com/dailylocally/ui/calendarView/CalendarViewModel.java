package com.dailylocally.ui.calendarView;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarViewModel extends BaseViewModel<CalendarNavigator> {

    public final ObservableField<String> addressTitle = new ObservableField<>();
    public final ObservableBoolean cart = new ObservableBoolean();

    public CalendarViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void getMonthWiseOrderDate(){
        try {
            Date currentDate = Calendar.getInstance().getTime();////current date
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            String yearString = yearFormat.format(currentDate);
            String monthString = monthFormat.format(currentDate);
            String userId = getDataManager().getCurrentUserId();
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.CALENDAR_MONTH_WISE_ORDER_HISTORY,
                    CalendarMonthResponse.class, new CalendarRequest("1",
                    "2020","5"), new Response.Listener<CalendarMonthResponse>() {
                @Override
                public void onResponse(CalendarMonthResponse response) {
                    if (response.getStatus()){
                        if (getNavigator()!=null) {
                            getNavigator().success(response.getResult());
                        }
                    }else {
                        if (getNavigator()!=null) {
                            getNavigator().failure();
                        }
                    }
                    setIsLoading(false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                }
            }, AppConstants.API_VERSION_ONE);
            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }
}