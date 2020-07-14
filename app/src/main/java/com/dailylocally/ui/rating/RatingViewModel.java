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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.calendarView.CalendarDayWiseRequest;
import com.dailylocally.ui.calendarView.CalendarDayWiseResponse;
import com.dailylocally.ui.coupons.CouponsRequest;
import com.dailylocally.ui.transactionHistory.TransactionHistoryResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RatingViewModel extends BaseViewModel<RatingNavigator> {


    public MutableLiveData<List<CalendarDayWiseResponse.Result.Item>> dayWiseLiveData;
    public ObservableList<CalendarDayWiseResponse.Result.Item> dayWiseItemViewModels = new ObservableArrayList<>();
    public final ObservableField<String> itemCount = new ObservableField<>();
    public final ObservableField<String> orderId = new ObservableField<>();

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
                    CalendarDayWiseResponse.class, new CalendarDayWiseRequest(userId,
                    dateString,monthString), new Response.Listener<CalendarDayWiseResponse>() {
                @Override
                public void onResponse(CalendarDayWiseResponse response) {
                    if (response!=null) {
                        if (response.getStatus()) {
                            if (response.getResult() != null && response.getResult().size() > 0) {
                                dayWiseLiveData.setValue(response.getResult().get(0).getItems());
                                itemCount.set(response.getResult().get(0).getItemsCount()+" Items");
                            }
                            if (getNavigator()!=null){
                                getNavigator().getProductList(response.getResult().get(0).getItems());
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

    public void ratingAPICall(Integer ratingProduct, Integer ratingDelivery, Integer productReceived, Integer doid,
                              ArrayList<Integer> vpid, String comments, Integer packageSealed){
        RatingRequest makeProductUpdateRequest;
        if (productReceived==0) {
            makeProductUpdateRequest = new RatingRequest(ratingProduct,
                    ratingDelivery, productReceived, doid, vpid, comments, packageSealed);
        }else {
            makeProductUpdateRequest = new RatingRequest(ratingProduct,
                    ratingDelivery, productReceived, doid, comments, packageSealed);
        }

        Gson gson = new GsonBuilder().create();
        String strData = gson.toJson(makeProductUpdateRequest);

        JsonObjectRequest jsonObjectRequest = null;
        try {
            if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
            setIsLoading(true);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_USER_RATING,
                    new JSONObject(strData), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("status").equals("true")){
                            if (getNavigator()!=null){
                                getNavigator().ratingSuccess(response.getString("message"));
                            }
                        }else {
                            if (getNavigator()!=null){
                                getNavigator().ratingFailure(response.getString("message"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setIsLoading(true);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    if (getNavigator()!=null){
                        getNavigator().ratingFailure("Rating failed");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("accept-version", AppConstants.API_VERSION_ONE);
                    headers.put("apptype","1");
                    //  headers.put("Authorization","Bearer");
                    headers.put("Authorization", "Bearer " + getDataManager().getApiToken());
                    return headers;
                }
            };
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DailylocallyApp.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    public void helpClick(){
        if (getNavigator()!=null){
            getNavigator().helpClick();
        }
    }


    public void submitClick(){
        if (getNavigator()!=null){
            getNavigator().submit();
        }
    }
}
