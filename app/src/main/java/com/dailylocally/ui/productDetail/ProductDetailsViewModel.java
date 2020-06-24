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

package com.dailylocally.ui.productDetail;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.R;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.address.googleAddress.GoogleAddressResponse;
import com.dailylocally.ui.address.googleAddress.UserAddressResponse;
import com.dailylocally.ui.address.saveAddress.SaveAddressRequest;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;


public class ProductDetailsViewModel extends BaseViewModel<ProductDetailsNavigator> {


    public final ObservableField<String> offer = new ObservableField<>();
    public final ObservableField<String> offerCost = new ObservableField<>();
    public final ObservableField<String> unit = new ObservableField<>();
    public final ObservableField<String> short_desc = new ObservableField<>();
    public final ObservableField<String> productname = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> mrp = new ObservableField<>();

    public final ObservableField<String> aId = new ObservableField<>();
    public final ObservableBoolean discount_cost_status = new ObservableBoolean();

    public ProductDetailsViewModel(DataManager dataManager) {
        super(dataManager);
        getProductDetails();
    }

    public void getProductDetails(){
        String userId = getDataManager().getCurrentUserId();
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_PRODUCT_DETAILS, ProductDetailsResponse.class,
                new ProductRequest(1,"13.05067500","80.00000000",18),
                new Response.Listener<ProductDetailsResponse>() {
                    @Override
                    public void onResponse(ProductDetailsResponse response) {
                        try {
                            if (response!=null){
                                if (response.getStatus() && response.getResult().size()>0){

                                    if (getNavigator()!=null){
                                        getNavigator().productsDetailsSuccess(response.getResult().get(0));
                                    }
                                    imageUrl.set(response.getResult().get(0).getImage());
                                    offer.set(response.getResult().get(0).getOffer());
                                    unit.set(response.getResult().get(0).getWeight()+" " + response.getResult().get(0).getUnit());
                                    short_desc.set(response.getResult().get(0).getShortDesc());
                                    productname.set(response.getResult().get(0).getProductname());
                                    discount_cost_status.set(response.getResult().get(0).getDiscountCostStatus());
                                    //mrp.set(String.valueOf(response.getResult().get(0).getMrp()));
                                    //offerCost.set("\u20B9"+response.getResult().get(0).getMrp() + " OFF on " + response.getResult().get(0).getProductname());
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
