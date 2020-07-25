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

public interface GoogleAddressNavigator {

    void handleError(Throwable throwable);

    void addressSaved();

    void emptyFields();

    boolean validationForAddress();

    void myLocationn();

    void showToast(String msg);
    void showAlert(String title,String message,String locationAddress,String area,String lat,
                   String lng,String pinCode);

    void goBack();

    void searchAddress();

    void getAddressSuccess(UserAddressResponse.Result result);

    void getAddressFailure();

    void googleAddressClick();

    void confirmLocationClick(String locationAddress,String area,String lat,
                              String lng,String pinCode);

}
