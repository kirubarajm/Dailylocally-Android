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

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

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

import java.util.List;


public class TransactionDetailsViewModel extends BaseViewModel<TransactionDetailsNavigator> {


    public final ObservableField<String> aId = new ObservableField<>();
    public final ObservableField<String> paymentId = new ObservableField<>();
    public final ObservableField<String> transactionOrderId = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> transacDate = new ObservableField<>();

    public ObservableList<TransactionViewResponse.Result.Item> productItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<TransactionViewResponse.Result.Item>> productItemsLiveData;

    public ObservableList<TransactionViewResponse.Result.Cartdetail> billDetailsItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<TransactionViewResponse.Result.Cartdetail>> billDetailsItemsLiveData;

    public TransactionDetailsViewModel(DataManager dataManager) {
        super(dataManager);
        productItemsLiveData = new MutableLiveData<>();
        billDetailsItemsLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<TransactionViewResponse.Result.Item>> getProductsItemsLiveData() {
        return productItemsLiveData;
    }

    public ObservableList<TransactionViewResponse.Result.Item> getProductsItemViewModels() {
        return productItemViewModels;
    }

    public void addProductsItemsToList(List<TransactionViewResponse.Result.Item> ordersItems) {
        if (ordersItems != null) {
            productItemViewModels.clear();
            productItemViewModels.addAll(ordersItems);
        }
    }

     public MutableLiveData<List<TransactionViewResponse.Result.Cartdetail>> getBilDetailsItemsLiveData() {
            return billDetailsItemsLiveData;
        }

    public ObservableList<TransactionViewResponse.Result.Cartdetail> getBilDetailsViewModels() {
        return billDetailsItemViewModels;
    }

    public void addBilDetailsItemsToList(List<TransactionViewResponse.Result.Cartdetail> ordersItems) {
        if (ordersItems != null) {
            billDetailsItemViewModels.clear();
            billDetailsItemViewModels.addAll(ordersItems);
        }
    }

    public void getTransactionHistoryViewList(String orderid){
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_TRANSACTION_VIEW, TransactionViewResponse.class,
                new TransactionViewRequest("5"),
                new Response.Listener<TransactionViewResponse>() {
                    @Override
                    public void onResponse(TransactionViewResponse response) {
                        try {
                            if (response!=null){
                                if (response.getStatus()){
                                    if (getNavigator()!=null){
                                        getNavigator().success(response.getResult().get(0).getTransactionTime());
                                    }
                                    paymentId.set(String.valueOf(response.getResult().get(0).getTsid()));
                                    transactionOrderId.set(String.valueOf(response.getResult().get(0).getOrderid()));
                                    price.set(String.valueOf(response.getResult().get(0).getPrice()));

                                    productItemsLiveData.setValue(response.getResult().get(0).getItems());
                                    billDetailsItemsLiveData.setValue(response.getResult().get(0).getCartdetails());
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

    public void viewInCalendar(){
        if (getNavigator()!=null){
            getNavigator().viewInCalendar();
        }
    }
}
