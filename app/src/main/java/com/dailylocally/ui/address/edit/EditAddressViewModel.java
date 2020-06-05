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

package com.dailylocally.ui.address.edit;

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
import com.dailylocally.ui.address.add.AddressRequestPojo;
import com.dailylocally.ui.address.add.AddressResponse;
import com.dailylocally.ui.address.add.UserAddressResponse;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.CommonResponse;

import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;

public class EditAddressViewModel extends BaseViewModel<EditAddressNavigator> {


    public final ObservableBoolean cart = new ObservableBoolean();

    public final ObservableField<String> locationAddress = new ObservableField<>();
    public final ObservableField<String> area = new ObservableField<>();
    public final ObservableField<String> house = new ObservableField<>();
    public final ObservableField<String> landmark = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableBoolean home = new ObservableBoolean();
    public final ObservableBoolean office = new ObservableBoolean();
    public final ObservableBoolean other = new ObservableBoolean();

    public final ObservableBoolean typeHome = new ObservableBoolean();
    public final ObservableBoolean typeOffice = new ObservableBoolean();
    public final ObservableBoolean typeOther = new ObservableBoolean();


    public String mAid;
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

    public EditAddressViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void goBack() {


        getNavigator().goBack();

    }

    public void locateMe() {

        getNavigator().myLocationn();

    }

    public void searchAddress() {


        getNavigator().searchAddress();


    }

    public void clickHome() {


        if (typeHome.get()) {

            typeHome.set(false);

        } else {
            new Analytics().sendClickData(AppConstants.SCREEN_EDIT_ADDRESS, AppConstants.CLICK_ADDRESS_HOME);
            typeHome.set(true);
            typeOffice.set(false);
            typeOther.set(false);
        }

    }

    public void clickOffice() {


        if (typeOffice.get()) {

            typeOffice.set(false);

        } else {

            new Analytics().sendClickData(AppConstants.SCREEN_EDIT_ADDRESS, AppConstants.CLICK_ADDRESS_WORK);
            typeHome.set(false);
            typeOffice.set(true);
            typeOther.set(false);


        }

    }


    public void clickOther() {


        if (typeOther.get()) {

            typeOther.set(false);

        } else {
            new Analytics().sendClickData(AppConstants.SCREEN_EDIT_ADDRESS, AppConstants.CLICK_ADDRESS_OTHER);
            typeHome.set(false);
            typeOffice.set(false);
            typeOther.set(true);
        }

    }


    public void saveAddress(String lat, String lng, String pincode, String aid) {

        request.setLat(lat);
        request.setLon(lng);
        request.setPincode(pincode);
        mAid = aid;

        if (aid != null ) {
            request.setAid(aid);

        }


    }


    public void saveAddress(String locationAddress, String house, String area, String landmark, String title) {

        new Analytics().sendClickData(AppConstants.SCREEN_EDIT_ADDRESS, AppConstants.CLICK_SAVE);

       /* if (locationAddress.isEmpty()) {
            return;
        }
        if (area.isEmpty()) {
            return;
        }

        if (landmark.isEmpty()) {
            return;
        }
        if (house.isEmpty()) {
            return;
        }*/

        if (!locationAddress.equals("") && !area.equals("") && !house.equals("")) {
            if (!DailylocallyApp.getInstance().onCheckNetWork()) return;

            request.setAddress(locationAddress);
            request.setFlatno(house);
            request.setLocality(area);
            request.setLandmark(landmark);
            request.setAddressType(1);

            request.setUserid(getDataManager().getCurrentUserId());

            try {
                setIsLoading(true);
                GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.ADD_ADDRESS_URL, AddressResponse.class, request, new Response.Listener<AddressResponse>() {
                    @Override
                    public void onResponse(AddressResponse response) {

                        if (getDataManager().getAddressId().equals(mAid)) {
                            getDataManager().updateCurrentAddress(request.getAddressTitle(), request.getAddress(), request.getLat(), request.getLon(), request.getLocality(), request.getAid());
                        }
                        if (getNavigator() != null)
                            getNavigator().addressSaved();





                       /* if (response != null &&response.getStatus()!=null&& response.getStatus()) {

                            try {
                              //  getDataManager().updateCurrentAddress(request.getAddressTitle(), request.getAddress(), Double.parseDouble(request.getLat()), Double.parseDouble(request.getLon()), request.getLocality(), request.getAid());
                              //  defaultAddress(request.getAid());
                                getNavigator().addressSaved();
                            } catch (Exception dd) {
                                dd.printStackTrace();
                            }

                        }*/





                        /*FilterRequestPojo filterRequestPojo;

                        Gson sGson = new GsonBuilder().create();
                        filterRequestPojo = sGson.fromJson(getDataManager().getFilterSort(), FilterRequestPojo.class);

                        filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
                        filterRequestPojo.setLat(getDataManager().getCurrentLat());
                        filterRequestPojo.setLat(getDataManager().getCurrentLng());

                        Gson gson = new Gson();
                        String json = gson.toJson(filterRequestPojo);
                        getDataManager().setFilterSort(json);*/


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


        } else {
            getNavigator().emptyFields();
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
                    if (response != null && response.getResult() != null && response.getResult().size() > 0) {


                        locationAddress.set(response.getResult().get(0).getAddress());
                        area.set(response.getResult().get(0).getLocality());
                        house.set(response.getResult().get(0).getFlatno());
                        landmark.set(response.getResult().get(0).getLandmark());


                        title.set(response.getResult().get(0).getAddressTitle());

                        if (response.getResult().get(0).getAddressType() == 1) {

                            typeHome.set(true);

                        } else if (response.getResult().get(0).getAddressType() == 2) {

                            typeOffice.set(true);
                        } else {

                            typeOther.set(true);

                        }
                        if (getNavigator() != null)
                            getNavigator().setLatLng(response.getResult().get(0).getLat(), response.getResult().get(0).getLon());

                        request.setLat(String.valueOf(response.getResult().get(0).getLat()));
                        request.setLon(String.valueOf(response.getResult().get(0).getLon()));
                        request.setPincode(response.getResult().get(0).getPincode());
                        request.setAid(response.getResult().get(0).getAid());
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
