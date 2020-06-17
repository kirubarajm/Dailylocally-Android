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

package com.dailylocally.ui.address.saveAddress;


import androidx.databinding.ObservableBoolean;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.address.DefaultAddressRequest;
import com.dailylocally.ui.address.googleAddress.GoogleAddressResponse;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.CommonResponse;
import com.dailylocally.utilities.DailylocallyApp;

public class SaveAddressViewModel extends BaseViewModel<SaveAddressNavigator> {

    public final ObservableBoolean flagAddressEdit = new ObservableBoolean();
    public final ObservableBoolean SAVEcLICKED = new ObservableBoolean();

    public SaveAddressViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void saveAddressClick(){
        if (getNavigator()!=null){
            getNavigator().saveClick();
        }
    }

    public void saveAddress(){
        int method = 0;
        if (flagAddressEdit.get()) {
            method = Request.Method.PUT;
        } else {
            method = Request.Method.POST;
        }
        GsonRequest gsonRequest = new GsonRequest(method, AppConstants.ADD_ADDRESS_URL, GoogleAddressResponse.class, new SaveAddressRequest(1,1,"","","","","" +
                "","","","","","","",""),
                new Response.Listener<GoogleAddressResponse>() {
                    @Override
                    public void onResponse(GoogleAddressResponse response) {
                        try {
                            if (response.getStatus()) {
                                SAVEcLICKED.set(true);
                                getDataManager().setUserAddress(true);
                                if (getNavigator() != null)
                                    getNavigator().showToast(response.getMessage(),response.getStatus());

/*
                                if (response.getAid() != null) {
                                    getDataManager().updateCurrentAddress(request.getAddressTitle(), request.getAddress(), request.getLat(), request.getLon(), request.getLocality(), response.getAid());
                                    getDataManager().setCurrentAddressTitle(request.getAddressTitle());
                                    getDataManager().setCurrentLat(request.getLat());
                                    getDataManager().setCurrentLng(request.getLon());
                                    getDataManager().setCurrentAddress(request.getAddress());
                                    getDataManager().setCurrentAddressArea(request.getLocality());
                                    getDataManager().setAddressId(response.getAid());
                                    defaultAddress(response.getAid());

                                }
*/
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SAVEcLICKED.set(true);
                getDataManager().setUserAddress(false);
            }
        }, AppConstants.API_VERSION_ONE);

        DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);

    }


    public void defaultAddress(String aid) {

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.DEFAULT_ADDRESS, CommonResponse.class, new DefaultAddressRequest(getDataManager().getCurrentUserId(), aid), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


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
