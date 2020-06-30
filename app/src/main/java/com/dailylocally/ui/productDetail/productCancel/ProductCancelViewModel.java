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

package com.dailylocally.ui.productDetail.productCancel;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.address.googleAddress.UserAddressResponse;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.productDetail.ProductDetailsResponse;
import com.dailylocally.ui.productDetail.ProductRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;


public class ProductCancelViewModel extends BaseViewModel<ProductCancelNavigator> {


    public final ObservableField<String> aId = new ObservableField<>();
    public final ObservableField<String> unit = new ObservableField<>();
    public final ObservableField<String> quantity = new ObservableField<>();
    public final ObservableField<String> short_desc = new ObservableField<>();
    public final ObservableField<String> productname = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> mrp = new ObservableField<>();
    public final ObservableField<String> productDate = new ObservableField<>();
    public final ObservableField<String> isCancelable = new ObservableField<>();
    public final ObservableBoolean isCancel = new ObservableBoolean();

    public ProductCancelViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void getProductCancelDetails(String doid,String dayOrderPId) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_DAY_ORDER_PRODUCT_DETAILS,
                ProductCancelResponse.class, new ProductCancelRequest(1, 1),
                new Response.Listener<ProductCancelResponse>() {
                    @Override
                    public void onResponse(ProductCancelResponse response) {
                        try {
                            if (response!=null){
                                if (response.getResult()!=null && response.getResult().size()>0){
                                unit.set(response.getResult().get(0).getItems().get(0).getWeight() + " " +
                                        response.getResult().get(0).getItems().get(0).getUnit());
                                short_desc.set(response.getResult().get(0).getItems().get(0).getProductShortDesc());
                                productname.set(response.getResult().get(0).getItems().get(0).getProductName());
                                imageUrl.set(String.valueOf(response.getResult().get(0).getItems().get(0).getProductImage()));
                                mrp.set(String.valueOf(response.getResult().get(0).getItems().get(0).getPrice()));
                                    quantity.set(String.valueOf(response.getResult().get(0).getItems().get(0).getQuantity()));
                                //productDate.set(String.valueOf(response.getResult().get(0).getItems().get(0).getProductDate()));
                                //isCancelable.set(String.valueOf(response.getResult().get(0).getItems().get(0).getCancelAvailable()));
                                if (getNavigator()!=null){
                                    getNavigator().success(response.getResult().get(0).getItems().get(0).getCancelAvailable(),
                                            String.valueOf(response.getResult().get(0).getItems().get(0).getProductDate()));
                                }
                            }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(false);
            }
        }, AppConstants.API_VERSION_ONE);

        DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);
    }

    public void goBack(){
        if (getNavigator()!=null){
            getNavigator().goBack();
        }
    }

    public void cancelProduct(){
        if (getNavigator()!=null){
            getNavigator().cancelProductClick();
        }
    }
}
