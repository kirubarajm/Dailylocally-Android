/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.dailylocally.ui.rating;
import androidx.databinding.ObservableArrayList;
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
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class RatingViewModel extends BaseViewModel<RatingNavigator> {


    public MutableLiveData<List<CalendarDayWiseResponse.Result.Item>> dayWiseLiveData;
    public ObservableList<CalendarDayWiseResponse.Result.Item> dayWiseItemViewModels = new ObservableArrayList<>();


    public RatingViewModel(DataManager dataManager) {
        super(dataManager);
        dayWiseLiveData = new MutableLiveData<>();
    }

    public void goBack() {
        if (getNavigator() != null) {
            getNavigator().goBack();
        }
    }

    public MutableLiveData<List<CalendarDayWiseResponse.Result.Item>> getOrdernowLiveData() {
        return dayWiseLiveData;
    }

    public void addOrderNowItemsToList(List<CalendarDayWiseResponse.Result.Item> results) {
        dayWiseItemViewModels.clear();
        dayWiseItemViewModels.addAll(results);

    }

    public void getDayWiseOrderDetails(Date date){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            String dateString = dateFormat.format(date);
            String monthString = monthFormat.format(date);
            String userId = getDataManager().getCurrentUserId();
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.CALENDAR_DAY_WISE_ORDER_HISTORY,
                    CalendarDayWiseResponse.class, new CalendarDayWiseRequest("2",
                    "2020-06-26","6"), new Response.Listener<CalendarDayWiseResponse>() {
                @Override
                public void onResponse(CalendarDayWiseResponse response) {
                    if (response!=null) {
                        if (response.getStatus()) {
                            if (response.getResult() != null && response.getResult().size() > 0) {
                                dayWiseLiveData.setValue(response.getResult().get(0).getItems());
                            }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*
    public void userRating(){
        try {
            String userId = getDataManager().getCurrentUserId();
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_USER_RATING,
                    CalendarDayWiseResponse.class, new RatingRequest(1,
                    1,1,1,,""), new Response.Listener<CalendarDayWiseResponse>() {
                @Override
                public void onResponse(CalendarDayWiseResponse response) {
                    if (response!=null) {
                        if (response.getStatus()) {
                            if (response.getResult() != null && response.getResult().size() > 0) {
                                dayWiseLiveData.setValue(response.getResult().get(0).getItems());
                            }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    public void helpClick(){
        if (getNavigator()!=null){
            getNavigator().helpClick();
        }
    }

    public void ratingAPICall(){
        try {
            /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            String dateString = dateFormat.format(date);
            String monthString = monthFormat.format(date);*/
            String userId = getDataManager().getCurrentUserId();
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.CALENDAR_DAY_WISE_ORDER_HISTORY,
                    CalendarDayWiseResponse.class, new CalendarDayWiseRequest(userId,
                    "",""), new Response.Listener<CalendarDayWiseResponse>() {
                @Override
                public void onResponse(CalendarDayWiseResponse response) {
                    if (response!=null) {
                        if (response.getStatus()) {
                            if (response.getResult() != null && response.getResult().size() > 0) {

                            }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
