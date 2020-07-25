package com.dailylocally.ui.calendarView;

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
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarViewModel extends BaseViewModel<CalendarNavigator> {

    public final ObservableField<String> addressTitle = new ObservableField<>();
    public final ObservableField<String> rateDeliveryButton = new ObservableField<>();
    public final ObservableField<String> dateDay = new ObservableField<>();
    public final ObservableField<String> doid = new ObservableField<>();
    public final ObservableBoolean cart = new ObservableBoolean();
    public final ObservableBoolean isRateBtn = new ObservableBoolean();
    public final ObservableBoolean noDataFound = new ObservableBoolean();
    public final ObservableBoolean isFutureOrder = new ObservableBoolean();

    public MutableLiveData<List<CalendarDayWiseResponse.Result.Item>> dayWiseLiveData;
    public ObservableList<CalendarDayWiseResponse.Result.Item> dayWiseItemViewModels = new ObservableArrayList<>();

    public CalendarViewModel(DataManager dataManager) {
        super(dataManager);
        dayWiseLiveData = new MutableLiveData<>();
    }

    public void getMonthWiseOrderDate(String month, String year) {
        try {
            //SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            //SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            //String yearString = yearFormat.format(year);
            //String monthString = monthFormat.format(month);
            String userId = getDataManager().getCurrentUserId();
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.CALENDAR_MONTH_WISE_ORDER_HISTORY,
                    CalendarMonthResponse.class, new CalendarMonthWiseRequest(userId,
                    year, month), new Response.Listener<CalendarMonthResponse>() {
                @Override
                public void onResponse(CalendarMonthResponse response) {
                    if (response.getStatus()) {
                        if (getNavigator() != null) {
                            getNavigator().success(response.getResult());
                        }
                    } else {
                        if (getNavigator() != null) {
                            getNavigator().failure("");
                        }
                    }
                    setIsLoading(false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                   /* if (getNavigator()!=null) {
                        getNavigator().failure("Failed");
                    }*/
                }
            }, AppConstants.API_VERSION_ONE);
            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void getDayWiseOrderDetails(Date date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            String dateString = dateFormat.format(date);
            String monthString = monthFormat.format(date);
            String userId = getDataManager().getCurrentUserId();
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.CALENDAR_DAY_WISE_ORDER_HISTORY,
                    CalendarDayWiseResponse.class, new CalendarDayWiseRequest(userId,
                    dateString, monthString), new Response.Listener<CalendarDayWiseResponse>() {
                @Override
                public void onResponse(CalendarDayWiseResponse response) {
                    if (response != null) {
                        if (response.getStatus()) {
                            if (response.getResult() != null && response.getResult().size() > 0) {
                                noDataFound.set(false);

                                if (response.getResult().get(0).getRatingStatus() != null) {
                                    isRateBtn.set(response.getResult().get(0).getRatingStatus());
                                } else {
                                    isRateBtn.set(false);
                                }
                                dayWiseLiveData.setValue(response.getResult().get(0).getItems());
                                doid.set(String.valueOf(response.getResult().get(0).getItems().get(0).getDoid()));



                            } else {
                                noDataFound.set(true);
                            }
                        } else {
                            if (getNavigator() != null) {
                                getNavigator().failure("");
                            }
                            noDataFound.set(true);
                        }
                    }
                    setIsLoading(false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    if (getNavigator() != null) {
                        getNavigator().failure("");
                    }
                }
            }, AppConstants.API_VERSION_ONE);
            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public MutableLiveData<List<CalendarDayWiseResponse.Result.Item>> getOrdernowLiveData() {
        return dayWiseLiveData;
    }

    public void addOrderNowItemsToList(List<CalendarDayWiseResponse.Result.Item> results) {
        dayWiseItemViewModels.clear();
        dayWiseItemViewModels.addAll(results);

    }

    public void ratingClick() {
        if (getNavigator() != null) {
            getNavigator().ratingClick();
        }
    }

    public void helpClick() {
        if (getNavigator() != null) {
            getNavigator().helpClick();
        }
    }

    public void goBack() {
        if (getNavigator() != null) {
            getNavigator().goBack();
        }
    }

    public void goToHome() {
        if (getNavigator() != null) {
            getNavigator().goBack();
        }
    }
}