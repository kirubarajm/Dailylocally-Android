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

    void setUserAsLoggedOut();

    void setLogout();

    void updateApiHeader(Long userId, String accessToken);

    void updateCurrentAddress(String title, String address, double lat, double lng, String area, Long aid);

    void updateUserInfo(String accessToken, Long userId, LoggedInMode loggedInMode, String userName, String email, boolean isLoggedIn);

    void updateUserInformation(Long userId, String userName, String userEmail, String PhoneNumber, String referralCode);

    void updateUserGender(boolean genderStatus);

    void updateEmailStatus(boolean status);

    void updateUserPasswordStatus(boolean passwordStatus);

    void showFunnel(boolean status);

    void saveMaster(String master);

    void saveFilterSort(String filters);

    void saveStoriesList(String stories);

    void saveVegType(Integer type);

    void currentFragment(Integer id);


    void kitchenId(Long id);


    void totalOrders(Integer orders);


    void saveRefundId(int rcid);


    void savePromotionId(int promotionid);

    void savePromotionCustomerId(Long customerid);

    void savePromotionDisplayedCount(int count);

    void savePromotionShowedDate(String date);

    void savePromotionSeen(boolean seen);


    void saveRazorpayCustomerId(String razorpayCustomerId);

    void saveRefundBalance(int refundBalance);


    void saveRegionId(int regionid);

    void saveCouponId(int couponid);

    void saveRatingOrderId(Long orderid);

    void saveRatingSkipDate(String date, int skips);

    void saveRatingSkipDate(int skips);

    void saveRatingAppStatus(boolean live);

    void saveIsFilterApplied(boolean filter);


    void saveApiToken(String token);

    void saveCouponCode(String coupon);

    void saveChatOrderID(String orderid);

    void saveSupportNumber(String number);

    void orderInstruction(String instruction);

    void saveServiceableStatus(boolean status, String title, String subtitle);


    void currentOrderId(Long orderId);

    boolean homeAddressadded(boolean status);

    boolean officeAddressadded(boolean status);

    void isFavClicked(boolean status);


    void appStartedAgain(boolean status);

    void saveFirstLocation(String address, String locality, String city);


    enum LoggedInMode {
        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
