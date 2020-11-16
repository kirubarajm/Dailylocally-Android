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


import com.android.volley.Request;
import com.dailylocally.ui.address.googleAddress.UserAddressResponse;

public interface AddressNewNavigator {

    void handleError(Throwable throwable);

    void apartmentClick();

    void individualClick();

    void confirmClick();

    void getAddressSuccess(UserAddressResponse.Result result);

    void getAddressFailure();

    void goBack();


    void addApartmentClick();

    void searchCommunity();

    void googleAddressConfirm();

    void joinCommunityClick();

    void communityClick();

    void addManually();

    void searchAddress();

    void knowMore();

    void showAlert(String title,String message);

    void addresSaved(String message, Boolean status, String email, String gender,int method);

    void communityJoined(String message);

    void showToast(String message, Boolean status);

    void saveAddressFailed(String message);

    void saveAddressClick();

    void editAddressClick();

    void dataloaded();

    void goHome();

    void myLocationn();


    void showAlert(String title,String message,String locationAddress,String area,String lat,
                   String lng,String pinCode);
    void confirmLocationClick(String locationAddress,String area,String lat,
                              String lng,String pinCode);
}
