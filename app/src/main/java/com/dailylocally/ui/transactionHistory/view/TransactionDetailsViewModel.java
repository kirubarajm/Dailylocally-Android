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

package com.dailylocally.ui.transactionHistory.view;

import androidx.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.address.googleAddress.UserAddressResponse;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.coupons.CouponsRequest;
import com.dailylocally.ui.transactionHistory.TransactionHistoryResponse;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;


public class TransactionDetailsViewModel extends BaseViewModel<TransactionDetailsNavigator> {


    public final ObservableField<String> aId = new ObservableField<>();

    public TransactionDetailsViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void getTransactionHistoryViewList(String orderid){
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        String userId = getDataManager().getCurrentUserId();
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_TRANSACTION_VIEW, TransactionViewResponse.class,
                new TransactionViewRequest(orderid),
                new Response.Listener<TransactionViewResponse>() {
                    @Override
                    public void onResponse(TransactionViewResponse response) {
                        try {
                            if (response!=null){
                                if (response.getStatus()){

                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        setIsLoading(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(true);
            }
        }, AppConstants.API_VERSION_ONE);

        DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
    }


    public void goBack(){
        if (getNavigator()!=null){
            getNavigator().goBack();
        }
    }
}
