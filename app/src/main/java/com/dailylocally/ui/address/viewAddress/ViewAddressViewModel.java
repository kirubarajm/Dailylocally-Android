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

package com.dailylocally.ui.address.viewAddress;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.R;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.address.googleAddress.UserAddressResponse;
import com.dailylocally.ui.base.BaseViewModel;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;


public class ViewAddressViewModel extends BaseViewModel<ViewAddressNavigator> {


    public final ObservableField<String> aId = new ObservableField<>();
    public final ObservableField<String> note = new ObservableField<>();

    public final ObservableBoolean communityUser=new ObservableBoolean();

    public ViewAddressViewModel(DataManager dataManager) {
        super(dataManager);
        fetchUserDetails();
    }

    public void updateClick(){
        if (getNavigator()!=null){
            getNavigator().updateClick();
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
                        if (response!=null) {
                            if (response.getStatus()) {
                                if (response.getResult()!=null && response.getResult().size()>0){

                                    note.set( DailylocallyApp.getInstance().getString(R.string.info_symbol) +" "+response.getResult().get(0).getNote());

                                    communityUser.set(response.getResult().get(0).getCommunityUserStatus());

                                    if (getNavigator()!=null){
                                        getNavigator().getAddressSuccess(response.getResult().get(0));
                                    }
                                }
                                aId.set(String.valueOf(response.getResult().get(0).getAid()));
                            }else {
                                if (getNavigator()!=null){
                                    getNavigator().getAddressFailure();
                                }
                            }
                        }else {
                            if (getNavigator()!=null){
                                getNavigator().getAddressFailure();
                            }
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        setIsLoading(false);
                        if (getNavigator()!=null){
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

    public void goBack(){
        if (getNavigator()!=null){
            getNavigator().goBack();
        }
    }

}
