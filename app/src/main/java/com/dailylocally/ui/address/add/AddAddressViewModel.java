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

package com.dailylocally.ui.address.add;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.address.DefaultAddressRequest;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.CommonResponse;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;

public class AddAddressViewModel extends BaseViewModel<AddAddressNavigator> {


    public final ObservableBoolean cart = new ObservableBoolean();

    public final ObservableField<String> locationAddress = new ObservableField<>();
    public final ObservableField<String> area = new ObservableField<>();
    public final ObservableField<String> house = new ObservableField<>();
    public final ObservableField<String> landmark = new ObservableField<>();
    public final ObservableBoolean typeHome = new ObservableBoolean();
    public final ObservableBoolean typeOffice = new ObservableBoolean();
    public final ObservableBoolean typeOther = new ObservableBoolean();
    public final ObservableBoolean SAVEcLICKED = new ObservableBoolean();


    public final ObservableBoolean home = new ObservableBoolean();
    public final ObservableBoolean office = new ObservableBoolean();
    public TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    };
    AddressRequestPojo request = new AddressRequestPojo();

    public AddAddressViewModel(DataManager dataManager) {
        super(dataManager);

        home.set(getDataManager().isHomeAddressAdded());
        office.set(getDataManager().isOfficeAddressAdded());
        clickOther();

    }


    public void locateMe() {
        if (getNavigator() != null)
            getNavigator().myLocationn();

    }


    public void clickHome() {


        if (typeHome.get()) {

            typeHome.set(false);

        } else {

            new Analytics().sendClickData(AppConstants.SCREEN_ADD_ADDRESS, AppConstants.CLICK_ADDRESS_HOME);

            typeHome.set(true);
            typeOffice.set(false);
            typeOther.set(false);
        }

    }

    public void clickOffice() {


        if (typeOffice.get()) {

            typeOffice.set(false);

        } else {

            new Analytics().sendClickData(AppConstants.SCREEN_ADD_ADDRESS, AppConstants.CLICK_ADDRESS_WORK);
            typeHome.set(false);
            typeOffice.set(true);
            typeOther.set(false);


        }

    }


    public void goBack() {
        if (getNavigator() != null)

            getNavigator().goBack();


    }

    public void searchAddress() {

        if (getNavigator() != null)
            getNavigator().searchAddress();


    }

    public void clickOther() {


        if (typeOther.get()) {

            typeOther.set(false);

        } else {
            new Analytics().sendClickData(AppConstants.SCREEN_ADD_ADDRESS, AppConstants.CLICK_ADDRESS_OTHER);
            typeHome.set(false);
            typeOffice.set(false);
            typeOther.set(true);
        }

    }


    public void saveAddress(String lat, String lng, String pincode) {

        request.setLat(lat);
        request.setLon(lng);
        request.setPincode(pincode);


    }


    public void saveAddress(String locationAddress, String house, String area, String landmark/*, String title*/) {


        new Analytics().sendClickData(AppConstants.SCREEN_ADD_ADDRESS, AppConstants.CLICK_SAVE);


        if (locationAddress.equals("")) {

        }

        if (house.equals("")) {


        }

        if (area.equals("")) {

        }

        if (landmark.equals("")) {

        }
        if (house.equals("")) {

        }

        if (!locationAddress.equals("") && !area.equals("") && !house.equals("")) {


            if (!DailylocallyApp.getInstance().onCheckNetWork()) return;

            //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );


            if (request == null) request = new AddressRequestPojo();


            request.setAddress(locationAddress);
            request.setFlatno(house);
            request.setLocality(area);
            request.setLandmark(landmark);

            request.setAddressTitle("Home");
            request.setAddressType(1);
            /*if (typeHome.get()) {
                request.setAddressTitle("Home");
                request.setAddressType(1);

            } else if (typeOffice.get()) {
                request.setAddressTitle("Work");
                request.setAddressType(2);

            } else if (typeOther.get()) {

                if (title.isEmpty()) {
                    Toast.makeText(DailylocallyApp.getInstance(), "Please enter the address title", Toast.LENGTH_SHORT).show();
                    return;
                }


                request.setAddressTitle(title);
                request.setAddressType(3);
            } else {
                Toast.makeText(DailylocallyApp.getInstance(), "Please pin your address type", Toast.LENGTH_SHORT).show();
                return;
            }*/

            if (request == null) request = new AddressRequestPojo();
            request.setUserid(getDataManager().getCurrentUserId());

            try {
                setIsLoading(true);
                if (!SAVEcLICKED.get()) {
                    GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.ADD_ADDRESS_URL, AddressResponse.class, request, new Response.Listener<AddressResponse>() {
                        @Override
                        public void onResponse(AddressResponse response) {

                            if (response.getStatus()) {
                                SAVEcLICKED.set(true);
                                getDataManager().setUserAddress(true);
                                if (getNavigator() != null)
                                    getNavigator().showToast(response.getMessage());


                                if (response.getAid() != null) {
                                    getDataManager().updateCurrentAddress(request.getAddressTitle(), request.getAddress(), request.getLat(), request.getLon(), request.getLocality(), response.getAid());
                                    getDataManager().setCurrentAddressTitle(request.getAddressTitle());
                                    getDataManager().setCurrentLat(request.getLat());
                                    getDataManager().setCurrentLng(request.getLon());
                                    getDataManager().setCurrentAddress(request.getAddress());
                                    getDataManager().setCurrentAddressArea(request.getLocality());
                                    getDataManager().setAddressId(response.getAid());
                                    defaultAddress(response.getAid());
                                    if (getNavigator() != null)
                                        getNavigator().addressSaved();


                           /* Gson sGson = new GsonBuilder().create();
                            filterRequestPojo = sGson.fromJson(getDataManager().getFilterSort(), FilterRequestPojo.class);

                          *//*  filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
                            filterRequestPojo.setLat(getDataManager().getCurrentLat());
                            filterRequestPojo.setLat(getDataManager().getCurrentLng());*//*

                            Gson gson = new Gson();
                            String json = gson.toJson(filterRequestPojo);
                            getDataManager().setFilterSort(json);*/
                                }
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
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception ee) {

                ee.printStackTrace();

            }


        } else {
            if (getNavigator() != null)
                getNavigator().emptyFields();

        }

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
