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

package com.dailylocally.ui.address.googleAddress;

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

public class GoogleAddressViewModel extends BaseViewModel<GoogleAddressNavigator> {


    public final ObservableBoolean cart = new ObservableBoolean();

    public final ObservableField<String> locationAddress = new ObservableField<>();
    public final ObservableField<String> area = new ObservableField<>();
    public final ObservableField<String> house = new ObservableField<>();
    public final ObservableField<String> landmark = new ObservableField<>();
    public final ObservableField<String> aId = new ObservableField<>();
    public final ObservableBoolean typeHome = new ObservableBoolean();
    public final ObservableBoolean typeOffice = new ObservableBoolean();
    public final ObservableBoolean typeOther = new ObservableBoolean();
    public final ObservableBoolean SAVEcLICKED = new ObservableBoolean();


    public final ObservableBoolean home = new ObservableBoolean();
    public final ObservableBoolean office = new ObservableBoolean();
    public final ObservableBoolean flagAddressEdit = new ObservableBoolean();


    public final ObservableField<String> googleAddress = new ObservableField<>();
    public final ObservableField<String> googleLat = new ObservableField<>();
    public final ObservableField<String> googleLon = new ObservableField<>();
    public final ObservableField<String> googlePinCode = new ObservableField<>();
    public final ObservableField<String> googleArea = new ObservableField<>();

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
    GoogleAddressRequestPojo request = new GoogleAddressRequestPojo();

    public GoogleAddressViewModel(DataManager dataManager) {
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

            typeHome.set(true);
            typeOffice.set(false);
            typeOther.set(false);
        }

    }

    public void clickOffice() {


        if (typeOffice.get()) {

            typeOffice.set(false);

        } else {

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
            typeHome.set(false);
            typeOffice.set(false);
            typeOther.set(true);
        }

    }

    public void saveAddress(String address, String area, String lat, String lng, String pincode) {
        request.setLat(lat);
        request.setLon(lng);
        request.setPincode(pincode);
        googleAddress.set(address);
        googleArea.set(area);
        googleLat.set(lat);
        googleLon.set(lng);
        googlePinCode.set(pincode);
    }

    public void confirmLocation(String locationAddress, String house, String area, String landmark) {

        if (googleAddress.get() == null || googleAddress.get().isEmpty()) {
            Toast.makeText(DailylocallyApp.getInstance(), "Unable to find address", Toast.LENGTH_SHORT).show();
            return;
        } else if (googleArea.get() == null || googleArea.get().isEmpty()) {
            Toast.makeText(DailylocallyApp.getInstance(), "Unable to find area", Toast.LENGTH_SHORT).show();
            return;
        } else if (googleLat.get() == null || googleLat.get().isEmpty()) {
            Toast.makeText(DailylocallyApp.getInstance(), "Unable to find latitude", Toast.LENGTH_SHORT).show();
            return;
        } else if (googleLon.get() == null || googleLon.get().isEmpty()) {
            Toast.makeText(DailylocallyApp.getInstance(), "Unable to find longitude", Toast.LENGTH_SHORT).show();
            return;
        } else if (googlePinCode.get() == null || googlePinCode.get().isEmpty()) {
            Toast.makeText(DailylocallyApp.getInstance(), "Unable to find PinCode", Toast.LENGTH_SHORT).show();
            return;
        }

        if (flagAddressEdit.get()) {
            checkAddress(googleAddress.get(), googleArea.get(), googleLat.get(),
                    googleLon.get(), googlePinCode.get());
        } else {
            if (getNavigator() != null) {
                getNavigator().confirmLocationClick(googleAddress.get(), googleArea.get(), googleLat.get(),
                        googleLon.get(), googlePinCode.get());
            }
        }
    }

    public void saveAddress(String locationAddress, String house, String area, String landmark/*, String title*/) {

        if (getNavigator().validationForAddress()) {


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

            if (!locationAddress.equals("") && !area.equals("") && !house.equals("") && !landmark.equals("")) {


                if (!DailylocallyApp.getInstance().onCheckNetWork()) return;

                if (request == null) request = new GoogleAddressRequestPojo();


                request.setAddress(locationAddress);
                request.setFlatno(house);
                request.setLocality(area);
                request.setLandmark(landmark);

                request.setAddressTitle("Home");
                request.setAddressType(1);
                request.setAid(aId.get());
                if (request == null) request = new GoogleAddressRequestPojo();
                request.setUserid(getDataManager().getCurrentUserId());

                try {
                    setIsLoading(true);
                    int method = 0;
                    if (!SAVEcLICKED.get()) {
                        if (flagAddressEdit.get()) {
                            method = Request.Method.PUT;
                        } else {
                            method = Request.Method.POST;
                        }
                        GsonRequest gsonRequest = new GsonRequest(method, AppConstants.ADD_ADDRESS_URL, GoogleAddressResponse.class, request,
                                new Response.Listener<GoogleAddressResponse>() {
                                    @Override
                                    public void onResponse(GoogleAddressResponse response) {
                                        try {
                                            if (response.getStatus()) {
                                                SAVEcLICKED.set(true);
                                                getDataManager().setUserAddress(true);
                                                if (getNavigator() != null)
                                                    getNavigator().showToast(response.getMessage());

                                                if (response.getAid() != null) {
                                                    getDataManager().updateCurrentAddress(request.getAddressTitle(), request.getAddress(), request.getLat(), request.getLon(), request.getLocality(), String.valueOf(response.getAid()));
                                                    getDataManager().setCurrentAddressTitle(request.getAddressTitle());
                                                    getDataManager().setCurrentLat(request.getLat());
                                                    getDataManager().setCurrentLng(request.getLon());
                                                    getDataManager().setCurrentAddress(request.getAddress());
                                                    getDataManager().setCurrentAddressArea(request.getLocality());
                                                    getDataManager().setAddressId(String.valueOf(response.getAid()));
                                                    defaultAddress(String.valueOf(response.getAid()));
                                                    if (getNavigator() != null)
                                                        getNavigator().addressSaved();
                                                }
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

    public void fetchUserDetails() {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {
            String userID = getDataManager().getCurrentUserId();
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.GET_USER_ADDRESS + userID, UserAddressResponse.class, new Response.Listener<UserAddressResponse>() {
                @Override
                public void onResponse(UserAddressResponse response) {
                    try {
                        if (response != null) {
                            if (response.getStatus()) {
                                if (response.getResult() != null && response.getResult().size() > 0) {
                                    if (getNavigator() != null) {
                                        getNavigator().getAddressSuccess(response.getResult().get(0));
                                    }
                                }
                                aId.set(String.valueOf(response.getResult().get(0).getAid()));
                            } else {
                                if (getNavigator() != null) {
                                    getNavigator().getAddressFailure();
                                }
                            }
                        } else {
                            if (getNavigator() != null) {
                                getNavigator().getAddressFailure();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        setIsLoading(false);
                        if (getNavigator() != null) {
                            getNavigator().getAddressFailure();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
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

    public void locationClick() {
        if (getNavigator() != null) {
            getNavigator().googleAddressClick();
        }
    }


    public void checkAddress(String locationAddress, String area, String lat,
                             String lng, String pinCode) {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;


        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_CHECK_ADDRESS, CheckAddressResponse.class, new CheckAddressRequest(getDataManager().getCurrentUserId(), lat, lng), new Response.Listener<CheckAddressResponse>() {
                @Override
                public void onResponse(CheckAddressResponse response) {
                    if (!response.getStatus()) {
                        if (response.getServicableStatus()) {

                            if (getNavigator() != null)
                                getNavigator().confirmLocationClick(googleAddress.get(), googleArea.get(), googleLat.get(),
                                        googleLon.get(), googlePinCode.get());

                        } else {

                            if (getNavigator() != null)
                                getNavigator().showAlert(response.getTitle(), response.getMessage(), locationAddress, area, lat, lng, pinCode);

                        }
                    } else {
                        if (getNavigator() != null)
                            getNavigator().confirmLocationClick(googleAddress.get(), googleArea.get(), googleLat.get(),
                                    googleLon.get(), googlePinCode.get());
                    }
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
