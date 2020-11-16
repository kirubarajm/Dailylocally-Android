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

package com.dailylocally.ui.address.addAddress;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
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
import com.dailylocally.ui.address.googleAddress.CheckAddressRequest;
import com.dailylocally.ui.address.googleAddress.CheckAddressResponse;
import com.dailylocally.ui.address.googleAddress.GoogleAddressResponse;
import com.dailylocally.ui.address.googleAddress.UserAddressResponse;
import com.dailylocally.ui.address.saveAddress.SaveAddressRequest;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.ui.joinCommunity.CommunityRequest;
import com.dailylocally.ui.joinCommunity.CommunityResponse;
import com.dailylocally.ui.joinCommunity.JoinCommunityRequest;
import com.dailylocally.ui.joinCommunity.JoinCommunityResponse;
import com.dailylocally.ui.joinCommunity.SearchCommunityRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressNewViewModel extends BaseViewModel<AddressNewNavigator> {

    public final ObservableBoolean isApartment = new ObservableBoolean();
    public final ObservableBoolean newUser = new ObservableBoolean();
    public final ObservableBoolean dummy = new ObservableBoolean();
    public final ObservableBoolean isGoogleAddress = new ObservableBoolean();
    public final ObservableBoolean isAddress = new ObservableBoolean();
    public final ObservableBoolean isAddressEdit = new ObservableBoolean();
    public final ObservableBoolean isSaveAddress = new ObservableBoolean();
    public final ObservableBoolean isClickableLocality = new ObservableBoolean();
    public final ObservableBoolean isJoinCommunity = new ObservableBoolean();
    public final ObservableBoolean isCommunitySearch = new ObservableBoolean();
    public final ObservableBoolean isCommunitySearchClicked = new ObservableBoolean();
    public final ObservableBoolean clickableApartment = new ObservableBoolean();
    public final ObservableBoolean residenceClicked = new ObservableBoolean();
    public final ObservableField<String> aId = new ObservableField<>();

    int method = 0;
    public final ObservableField<String> locationAddress = new ObservableField<>();
    public final ObservableField<String> area = new ObservableField<>();
    public final ObservableField<String> house = new ObservableField<>();
    public final ObservableField<String> landmark = new ObservableField<>();
    public final ObservableBoolean typeHome = new ObservableBoolean();
    public final ObservableBoolean typeOffice = new ObservableBoolean();
    public final ObservableBoolean typeOther = new ObservableBoolean();
    public final ObservableBoolean SAVEcLICKED = new ObservableBoolean();


    public final ObservableBoolean flagAddressEdit = new ObservableBoolean();

    public final ObservableField<String> cmId = new ObservableField<>();
    public final ObservableField<String> googleAddress = new ObservableField<>();
    public final ObservableField<String> googleLat = new ObservableField<>();
    public final ObservableField<String> googleLon = new ObservableField<>();
    public final ObservableField<String> googlePinCode = new ObservableField<>();
    public final ObservableField<String> googleArea = new ObservableField<>();

    public final ObservableField<String> address = new ObservableField<>();
    public final ObservableField<String> addressType = new ObservableField<>();

    public final ObservableField<String> comLat = new ObservableField<>();
    public final ObservableField<String> comLon = new ObservableField<>();


    public final ObservableBoolean loading = new ObservableBoolean();
    public ObservableList<CommunityResponse.Result> communityItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<CommunityResponse.Result>> communityItemsLiveData;


    public AddressNewViewModel(DataManager dataManager) {
        super(dataManager);
        communityItemsLiveData = new MutableLiveData<>();
    }

    public void apartment() {
        if (getNavigator() != null) {
            getNavigator().apartmentClick();
        }
    }
    public void locateMe() {
        if (getNavigator() != null)
            getNavigator().myLocationn();

    }
    public void goBack() {
        if (getNavigator() != null) {
            getNavigator().goBack();
        }
    }
public void goHome() {
        if (getNavigator() != null) {
            getNavigator().goHome();
        }
    }

    public void searchAddress() {
        if (getNavigator() != null)
            getNavigator().searchAddress();
    }





    public void openSaveAddress() {
        isGoogleAddress.set(false);
        isSaveAddress.set(true);
        isJoinCommunity.set(false);
        isCommunitySearch.set(false);
        isAddress.set(false);
    }

    public void openCommunitySearch() {
        isGoogleAddress.set(false);
        isSaveAddress.set(false);
        isJoinCommunity.set(false);
        isCommunitySearch.set(true);
        isAddress.set(false);
    }


    public void openJoinCommunity() {
        isGoogleAddress.set(false);
        isJoinCommunity.set(true);
        isSaveAddress.set(false);
        isCommunitySearch.set(false);
        isAddress.set(false);
    }


    public void openAddress() {
        isGoogleAddress.set(false);
        isJoinCommunity.set(false);
        isSaveAddress.set(false);
        isCommunitySearch.set(false);
        isAddress.set(true);
        isAddressEdit.set(true);
    }

    public void openGoogleAddress() {
        isGoogleAddress.set(true);
        isJoinCommunity.set(false);
        isSaveAddress.set(false);
        isCommunitySearch.set(false);
        isAddress.set(true);
    }

    public void knowMore() {
        if (getNavigator() != null) {
            getNavigator().knowMore();
        }
    }

    public void googleAddressConfirm() {

        /*isGoogleAddress.set(false);
        isJoinCommunity.set(false);
        isCommunitySearch.set(false);
        isSaveAddress.set(false);
        isAddress.set(true);*/



        if (getNavigator() != null) {
            getNavigator().googleAddressConfirm();
        }
    }

    public void joinCommunityClick() {
        if (getNavigator() != null) {
            getNavigator().joinCommunityClick();
        }
    }


    public void communityClick() {
       /* isGoogleAddress.set(false);
        isJoinCommunity.set(true);
        isCommunitySearch.set(false);
        isSaveAddress.set(false);
        isAddress.set(false);*/


        if (getNavigator() != null) {
            getNavigator().communityClick();
        }
    }


    public void addManually() {
       /* isGoogleAddress.set(false);
        isJoinCommunity.set(false);
        isSaveAddress.set(false);
        isCommunitySearch.set(false);
        isAddress.set(true);*/
        if (getNavigator() != null) {
            getNavigator().addManually();
        }
    }


    public void saveAddress(String address, String area, String lat, String lng, String pincode) {
        googleAddress.set(address);
        googleArea.set(area);
        googleLat.set(lat);
        googleLon.set(lng);
        googlePinCode.set(pincode);
    }

    public void individual() {

        if (getNavigator() != null) {
            getNavigator().individualClick();
        }
    }


    public void addApartmentClick() {
        if (getNavigator() != null) {
            getNavigator().addApartmentClick();
        }
    }

    public void searchCommunity() {
        if (getNavigator() != null)
            getNavigator().searchCommunity();
    }

    public void confirmClick() {
        if (getNavigator() != null) {
            getNavigator().confirmClick();
        }
    }

    public void saveAddressClick(){
        if (getNavigator()!=null){
            getNavigator().saveAddressClick();
        }
    }



    public void editClick(){
        if (getNavigator()!=null){
            getNavigator().editAddressClick();
        }
    }

    public void joinTheCommunityAPI(String houseFlatNo, String floorNo, boolean changeAddress) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        String userId = getDataManager().getCurrentUserId();

        try {
            JoinCommunityRequest communityRequest = new JoinCommunityRequest(userId, cmId.get(), "", houseFlatNo, floorNo, comLat.get(), comLat.get(), changeAddress);

            Gson gson = new GsonBuilder().create();
            String payloadStr = gson.toJson(communityRequest);
            JsonObjectRequest jsonObjectRequest = null;
            setIsLoading(true);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_JOIN_THE_COMMUNITY, new JSONObject(payloadStr), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    setIsLoading(false);
                    try {
                        if (response.getString("status").equals("true")) {
                            if (getNavigator() != null) {
                                getNavigator().communityJoined(response.getString("message"));
                            }
                            Gson gson = new Gson();
                            JoinCommunityResponse joinCommunityResponse = gson.fromJson(response.toString(), JoinCommunityResponse.class);

                            String completeAddress = joinCommunityResponse.getResult().get(0).getCompleteAddress();
                            String lat = String.valueOf(joinCommunityResponse.getResult().get(0).getLat());
                            String lon = String.valueOf(joinCommunityResponse.getResult().get(0).getLon());
                            String aid = String.valueOf(joinCommunityResponse.getResult().get(0).getAid());
                            String city = String.valueOf(joinCommunityResponse.getResult().get(0).getCity());
                            String addressType = String.valueOf(joinCommunityResponse.getResult().get(0).getAddressType());
                            String flatHouseNo = String.valueOf(joinCommunityResponse.getResult().get(0).getFlatHouseNo());
                            String blockName = String.valueOf(joinCommunityResponse.getResult().get(0).getBlockName());
                            String apartmentName = String.valueOf(joinCommunityResponse.getResult().get(0).getApartmentName());
                            String plotHouseNo = String.valueOf(joinCommunityResponse.getResult().get(0).getPlotHouseNo());
                            String floor = String.valueOf(joinCommunityResponse.getResult().get(0).getFloor());


                            getDataManager().updateCurrentAddress("", completeAddress, lat, lon, city, aid);
                            getDataManager().setCurrentLat(lat);
                            getDataManager().setCurrentLng(lon);
                            getDataManager().setCurrentAddress(completeAddress);
                            getDataManager().setCurrentAddressArea(city);
                            getDataManager().setCurrentAddressTitle(city);
                            getDataManager().setAddressId(aid);
                            getDataManager().setUserAddress(true);
                            if (addressType.equals("1")) {
                                String cAddress = "No." + flatHouseNo + ", " + blockName + ", " + apartmentName + ", " + completeAddress;
                                getDataManager().setCurrentAddress(cAddress);

                            } else {
                                String cAddress = "No." + plotHouseNo + ", Floor-" + floor + ", " + completeAddress;
                                getDataManager().setCurrentAddress(cAddress);
                            }

                            if (getNavigator()!=null)
                                getNavigator().communityJoined(joinCommunityResponse.getMessage());

                        } else {
                            if (response.getString("show_alert").equals("true")) {
                                if (response.getString("order_available").equals("true")) {
                                    if (getNavigator() != null)
                                        getNavigator().showAlert(response.getString("alert_title"), response.getString("alert_message"), "", "", "", "", "");
                                } else {
                                    if (getNavigator() != null)
                                        getNavigator().showAlert(response.getString("alert_title"), response.getString("alert_message"));
                                }
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    return AppConstants.setHeaders(AppConstants.API_VERSION_TWO);
                }
            };
            DailylocallyApp.getInstance().addToRequestQueue(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
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
    public void saveAddress(String addressType, String googleAddress, String completeAddress,
                            String flatHouseNo, String plotHouseNo, String city, String pincode, String lat,
                            String lon, String landmark, String floor, String blockName, String apartmentName,
                            String aId){
        String userId = getDataManager().getCurrentUserId();


        String as=getDataManager().getAddressId();

        if (getDataManager().getAddressId()!=null) {
           if (!getDataManager().getAddressId().equals("0")){
               method = Request.Method.PUT;
           }else {
               method = Request.Method.POST;
           }
        } else {
            method = Request.Method.POST;
        }



        GsonRequest gsonRequest = new GsonRequest(method, AppConstants.ADD_ADDRESS_URL, GoogleAddressResponse.class,
                new SaveAddressRequest(userId,addressType,googleAddress,completeAddress,flatHouseNo,plotHouseNo,city,pincode,
                        lat,lon,landmark,floor,blockName,apartmentName,getDataManager().getAddressId()),
                new Response.Listener<GoogleAddressResponse>() {
                    @Override
                    public void onResponse(GoogleAddressResponse response) {
                        try {
                            if (response!=null && response.getStatus()) {
                                SAVEcLICKED.set(true);
                                getDataManager().setUserAddress(true);
                                if (getNavigator() != null)
                                  //  getNavigator().showToast(response.getMessage(),response.getStatus());
                                if (getDataManager().getGender()==2) {
                                    getNavigator().addresSaved(response.getMessage(), response.getStatus(), getDataManager().getCurrentUserEmail(),"FEMALE", method);
                                }else {
                                    getNavigator().addresSaved(response.getMessage(), response.getStatus(), getDataManager().getCurrentUserEmail(),"MALE", method);
                                }
                                /*if (response.getAid() != null) {*/
                                getDataManager().updateCurrentAddress("", completeAddress, lat, lon,
                                        city, String.valueOf(response.getAid()));
                                getDataManager().setCurrentLat(lat);
                                getDataManager().setCurrentLng(lon);
                                getDataManager().setCurrentAddress(completeAddress);
                                getDataManager().setCurrentAddressArea(city);
                                getDataManager().setCurrentAddressTitle(city);
                                getDataManager().setAddressId(response.getAid());

                                if (addressType.equals("1")){
                                    String cAddress="No."+flatHouseNo+", "+blockName+", "+apartmentName+", "+completeAddress;

                                    getDataManager().setCurrentAddress(cAddress);

                                }else {
                                    String cAddress="No."+plotHouseNo+", Floor-"+floor+", "+completeAddress;
                                    getDataManager().setCurrentAddress(cAddress);
                                }





                                /*}*/
                            }else {
                                if (getNavigator()!=null){
                                    getNavigator().saveAddressFailed(response.getMessage());
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
                if (getNavigator()!=null){
                    getNavigator().saveAddressFailed("Failed");
                }
            }
        }, AppConstants.API_VERSION_ONE);

        DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);

    }



    public MutableLiveData<List<CommunityResponse.Result>> getCommunityListItemsLiveData() {
        return communityItemsLiveData;
    }

    public ObservableList<CommunityResponse.Result> getCommunityListItemViewModels() {
        return communityItemViewModels;
    }

    public void addCommunityListItemsToList(List<CommunityResponse.Result> ordersItems) {
        if (ordersItems != null) {
            communityItemViewModels.clear();
            communityItemViewModels.addAll(ordersItems);
        }
    }

    public void quickSearch(String search) {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        try {
            SearchCommunityRequest communityRequest = new SearchCommunityRequest(search,googleLat.get(),googleLon.get());
            Gson gson = new GsonBuilder().create();
            String payloadStr = gson.toJson(communityRequest);
            JsonObjectRequest jsonObjectRequest = null;
            setIsLoading(true);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_COMMUNITY_SEARCH, new JSONObject(payloadStr), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    setIsLoading(false);
                    loading.set(false);
                    try {
                        if (response.getString("status").equals("true")) {
                            Gson sGson = new GsonBuilder().create();
                            CommunityResponse communityResponse1 = sGson.fromJson(response.toString(), CommunityResponse.class);
                            communityItemsLiveData.setValue(communityResponse1.getResult());

                        }else {
                            List<CommunityResponse.Result> results=new ArrayList<>();
                            communityItemsLiveData.setValue(results);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    loading.set(false);
                    if (getNavigator() != null) {
                        //getNavigator().updateFailure("Failed to update");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                }
            };
            DailylocallyApp.getInstance().addToRequestQueue(jsonObjectRequest);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getCommunityList() {
        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        String userId = getDataManager().getCurrentUserId();

        try {
            CommunityRequest communityRequest = new CommunityRequest(userId,googleLat.get(),googleLon.get());
            Gson gson = new GsonBuilder().create();
            String payloadStr = gson.toJson(communityRequest);
            JsonObjectRequest jsonObjectRequest = null;
            setIsLoading(true);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_COMMUNITY_LIST, new JSONObject(payloadStr), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    setIsLoading(false);

                    try {
                        if (response.getString("status").equals("true")) {
                            Gson sGson = new GsonBuilder().create();
                            CommunityResponse communityResponse1 = sGson.fromJson(response.toString(), CommunityResponse.class);
                            communityItemsLiveData.setValue(communityResponse1.getResult());

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (getNavigator() != null) {
                        getNavigator().dataloaded();
                    }
                    loading.set(false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    loading.set(false);
                    if (getNavigator() != null) {
                        getNavigator().dataloaded();
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                }
            };
            DailylocallyApp.getInstance().addToRequestQueue(jsonObjectRequest);

        } catch (Exception e){
            e.printStackTrace();
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
