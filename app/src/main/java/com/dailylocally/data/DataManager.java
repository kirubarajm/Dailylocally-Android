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

package com.dailylocally.data;


import com.dailylocally.data.prefs.PreferencesHelper;

import io.reactivex.Observable;


public interface DataManager extends PreferencesHelper {

    Observable<Boolean> seedDatabaseOptions();

    Observable<Boolean> seedDatabaseQuestions();


    void setLogout();


    void updateCurrentAddress(String title, String address, String lat, String lng, String area, String aid);

    void updateUserInformation(String userId, String userName, String userEmail, String PhoneNumber, String referralCode);

    void userRegistered(boolean isRegistered);


    void showFunnel(boolean status);

    void saveMaster(String master);

    void savePromotionId(int promotionid);
    void savePromotionDailyCount(int count);
    void savePromotionAppStartAgain(boolean ststus);

    void savePromotionCustomerId(String customerid);

    void savePromotionDisplayedCount(int count);

    void savePromotionShowedDate(String date);

    void savePromotionSeen(boolean seen);
    void updateAvailable(boolean available);


    void saveRazorpayCustomerId(String razorpayCustomerId);



    void saveCouponId(int couponid);

    void saveRatingOrderId(String orderid);

    void saveRatingSkipDate(String date, int skips);

    void saveRatingSkipDate(int skips);

    void saveRatingAppStatus(boolean live);

    void saveIsFilterApplied(boolean filter);


    void saveApiToken(String token);

    void saveCouponCode(String coupon);

    void saveFiletrSort(String filterSort);

    void saveChatOrderID(String orderid);

    void saveSupportNumber(String number);

    void orderInstruction(String instruction);

    void saveServiceableStatus(boolean status, String title, String subtitle);



    boolean homeAddressadded(boolean status);

    boolean officeAddressadded(boolean status);

    void appStartedAgain(boolean status);

    void saveFirstLocation(String address, String locality, String city);



    void saveUserDetails(String details);



}
