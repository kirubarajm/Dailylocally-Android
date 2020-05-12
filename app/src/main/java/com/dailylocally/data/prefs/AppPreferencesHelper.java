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

package com.dailylocally.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.dailylocally.data.DataManager;
import com.dailylocally.di.PreferenceInfo;

import javax.inject.Inject;


public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";

    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";
    private static final String PREF_KEY_CURRENT_PROMOTION_USER_ID = "PREF_KEY_CURRENT_PROMOTION_USER_ID";

    private static final String PREF_KEY_RAZORPAY_CUSTOMER_ID = "RAZORPAY_CUSTOMER_ID";

    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";

    private static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_URL = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL";

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";
    private static final String PREF_KEY_FILTER_APPLIED = "FILTER_APPLIED";

    private static final String PREF_KEY_FUNNEL_STATUS = "FUNNEL_STATUS";

    private static final String PREF_KEY_APP_STARTED_AGAIN = "APP_STARTED_AGAIN";
    private static final String PREF_KEY_ORDER_INSTRUCTION = "ORDER_INSTRUCTION";

    private static final String PREF_KEY_SUPPORT_NUMBER = "SUPPORT_NUMBER";

    private static final String PREF_KEY_FIRST_ADDRESS = "FIRST_ADDRESS";
    private static final String PREF_KEY_FIRST_LOCALLITY = "FIRST_LOCALITY";
    private static final String PREF_KEY_FIRST_CITY = "FIRST_CITY";


    private static final String PREF_KEY_REFUND_ID = "PREF_KEY_REFUND_ID";
    private static final String PREF_KEY_REFUND_BALANCE = "PREF_KEY_REFUND_BALANCE";
    private static final String PREF_KEY_COUPON_ID = "PREF_KEY_COUPON_ID";
    private static final String PREF_KEY_COUPON_CODE = "COUPON_CODE";
    private static final String PREF_KEY_RATING_SKIPS = "PREF_KEY_RATING_SKIPS";
    private static final String PREF_KEY_RATING_DATE = "PREF_KEY_RATING_DATE";
    private static final String PREF_KEY_RATING_APP_STATUS = "RATING_APP_STATUS";

    private static final String PREF_KEY_REGION_ID = "REGION_ID";
    private static final String PREF_KEY_RATING_ORDER_ID = "RATING_ORDER_ID";
    private static final String PREF_KEY_VEG_TYPE = "VEG_TYPE";

    private static final String PREF_KEY_EMAIL_STATUS = "EMAIL_STATUS";


    private static final String PREF_KEY_IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";


    private static final String PREF_KEY_SELECTED_MAKEIT_ID = "SELECTED_MAKEIT_ID";

    private static final String PREF_KEY_ORDER_ID = "ORDER_ID";
    private static final String PREF_KEY_MASTER = "MASTER";
    private static final String PREF_KEY_FILTER = "FILTER";
    private static final String PREF_KEY_STORIES_LIST = "STORIES_IST";
    private static final String PREF_KEY_CURRENT_FRAGMENT = "CURRENT_FRAGMENT";


    private static final String PREF_KEY_ADDRESS_TITLE = "ADDRESS_TITLE";
    private static final String PREF_KEY_ADDRESS_AREA = "ADDRESS_AREA";
    private static final String PREF_KEY_CURRENT_LAT = "CURRENT_LAT";
    private static final String PREF_KEY_CURRENT_LNG = "CURRENT_LNG";
    private static final String PREF_KEY_CURRENT_ADDRESS = "CURRENT_ADDRESS";
    private static final String PREF_KEY_CURRENT_ADDRESS_ID = "CURRENT_ADDRESS_ID";
    private static final String PREF_KEY_IS_FAV_CLICKED = "IS_FAV_CLICKED";

    private static final String PREF_KEY_CURRENT_USER_PHONE_NUMBER = "CURRENT_USER_PHONE_NUMBER";
    private static final String PREF_KEY_CURRENT_USER_REFERRALS = "CURRENT_USER_REFERRALS";
    private static final String PREF_KEY_GENDER_STATUS = "CURRENT_GENDER_STATUS";
    private static final String PREF_KEY_PASSWORD_STATUS = "CURRENT_PASSWORD_STATUS";
    private static final String PREF_KEY_TOTAL_ORDERS = "TOTAL_ORDERS";
    private static final String PREF_KEY_HOME_ADDRESS_ADDED = "HOME_ADDRESS_ADDED";
    private static final String PREF_KEY_OFFICE_ADDRESS_ADDED = "OFFICE_ADDRESS_ADDED";
    private static final String PREF_KEY_API_TOKEN = "API_TOKEN";


    private static final String PREF_KEY_PROMOTION_SEEN = "PROMOTION_SEEN";
    private static final String PREF_KEY_PROMOTION_ID = "PROMOTION_ID";
    private static final String PREF_KEY_PROMOTION_SEEN_COUNT= "PROMOTION_SEEN_COUNT";
    private static final String PREF_KEY_PROMOTION_SEEN_DATE = "PROMOTION_SEEN_DATE";

    private static final String PREF_KEY_CHAT_ORDERID = "CHAT_ORDERID";

    private static final String PREF_KEY_SERVICEABLESTATUS = "SERVICEABLESTATUS";
    private static final String PREF_KEY_SERVICEABLETITLE = "SERVICEABLETITLE";
    private static final String PREF_KEY_SERVICEABLESUBTITLE= "SERVICEABLESUBTITLE";


    private static final String PREF_KEY_CART = "PRODUCTS_IN_CART";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getCurrentUserEmail() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, null);
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply();
    }

    @Override
    public Long getCurrentUserId() {
        // return userId == AppConstants.NULL_INDEX ? null : userId;
        //return mPrefs.getInt(PREF_KEY_CURRENT_USER_ID, null);

        return mPrefs.getLong(PREF_KEY_CURRENT_USER_ID, 0L);
    }

    @Override
    public void setCurrentUserId(Long userId) {
        // Integer id = userId == null ? AppConstants.NULL_INDEX : userId;
        /*if (userId == null) {
            userId = 0;
        }
        mPrefs.edit().putInt(PREF_KEY_CURRENT_USER_ID, userId).apply();*/

        // Long id = userId == null ? AppConstants.NULL_INDEX : userId;
        mPrefs.edit().putLong(PREF_KEY_CURRENT_USER_ID, userId).apply();
    }

    @Override
    public Long getCurrentPromotionUserId() {
        return mPrefs.getLong(PREF_KEY_CURRENT_PROMOTION_USER_ID, 0L);
    }

    @Override
    public void setCurrentPromotionUserId(Long userId) {
        mPrefs.edit().putLong(PREF_KEY_CURRENT_PROMOTION_USER_ID, userId).apply();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPrefs.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode) {
        mPrefs.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public String getCurrentUserName() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, null);
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NAME, userName).apply();
    }

/*
    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, null);
    }
*/

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl).apply();
    }

    @Override
    public boolean getIsLoggedIn() {
        return mPrefs.getBoolean(PREF_KEY_IS_USER_LOGGED_IN, false);
    }

    @Override
    public void setIsLoggedIn(boolean isLoggedIn) {
        mPrefs.edit().putBoolean(PREF_KEY_IS_USER_LOGGED_IN, isLoggedIn).apply();
    }

    @Override
    public String getCurrentAddressTitle() {
        return mPrefs.getString(PREF_KEY_ADDRESS_TITLE, null);
    }

    @Override
    public void setCurrentAddressTitle(String title) {
        mPrefs.edit().putString(PREF_KEY_ADDRESS_TITLE, title).apply();
    }

    @Override
    public int getRefundBalance() {
        return mPrefs.getInt(PREF_KEY_REFUND_BALANCE, 0);
    }

    @Override
    public void setRefundBalance(int refundBalance) {
        mPrefs.edit().putInt(PREF_KEY_REFUND_BALANCE, refundBalance).apply();
    }

    @Override
    public String getRazorpayCustomerId() {
        return mPrefs.getString(PREF_KEY_RAZORPAY_CUSTOMER_ID, null);
    }

    @Override
    public void setRazorpayCustomerId(String razorpayCustomerId) {
        mPrefs.edit().putString(PREF_KEY_RAZORPAY_CUSTOMER_ID, razorpayCustomerId).apply();
    }

    @Override
    public String getCurrentAddressArea() {
        return mPrefs.getString(PREF_KEY_ADDRESS_AREA, null);
    }

    @Override
    public void setCurrentAddressArea(String area) {
        mPrefs.edit().putString(PREF_KEY_ADDRESS_AREA, area).apply();
    }

    @Override
    public String getCurrentAddress() {
        return mPrefs.getString(PREF_KEY_CURRENT_ADDRESS, null);
    }

    @Override
    public void setCurrentAddress(String address) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_ADDRESS, address).apply();
    }

    @Override
    public String getCurrentLat() {
        return mPrefs.getString(PREF_KEY_CURRENT_LAT, null);
    }

    @Override
    public void setChatOrderid(String orderid) {
        mPrefs.edit().putString(PREF_KEY_CHAT_ORDERID, orderid).apply();
    }

    @Override
    public String getChatOrderid() {
        return mPrefs.getString(PREF_KEY_CHAT_ORDERID, null);
    }

    @Override
    public void setCurrentLat(double lat) {
        if (lat == 0.0) {
            mPrefs.edit().putString(PREF_KEY_CURRENT_LAT, null).apply();
        } else {
            mPrefs.edit().putString(PREF_KEY_CURRENT_LAT, String.valueOf(lat)).apply();
        }
    }

    @Override
    public String getCurrentLng() {
        return mPrefs.getString(PREF_KEY_CURRENT_LNG, null);
    }

    @Override
    public void setCurrentLng(double lng) {

        if (lng == 0.0) {
            mPrefs.edit().putString(PREF_KEY_CURRENT_LNG, null).apply();
        } else {
            mPrefs.edit().putString(PREF_KEY_CURRENT_LNG, String.valueOf(lng)).apply();
        }


    }

    @Override
    public Long getMakeitID() {
        return mPrefs.getLong(PREF_KEY_SELECTED_MAKEIT_ID, 0);
    }

    @Override
    public void setMakeitID(Long id) {
        mPrefs.edit().putLong(PREF_KEY_SELECTED_MAKEIT_ID, id).apply();
    }

    @Override
    public Long getOrderId() {
        return mPrefs.getLong(PREF_KEY_ORDER_ID, 0);
    }

    @Override
    public void setOrderId(Long orderId) {
        mPrefs.edit().putLong(PREF_KEY_ORDER_ID, orderId).apply();
    }

    @Override
    public Long getAddressId() {
        return mPrefs.getLong(PREF_KEY_CURRENT_ADDRESS_ID, 0L);
    }

    @Override
    public void setAddressId(Long orderId) {
        mPrefs.edit().putLong(PREF_KEY_CURRENT_ADDRESS_ID, orderId).apply();
    }

    @Override
    public String getMaster() {
        return mPrefs.getString(PREF_KEY_MASTER, null);
    }

    @Override
    public void setMaster(String master) {
        mPrefs.edit().putString(PREF_KEY_MASTER, master).apply();
    }

    @Override
    public String getFilterSort() {
        return mPrefs.getString(PREF_KEY_FILTER, null);
    }

    @Override
    public void setFilterSort(String master) {
        mPrefs.edit().putString(PREF_KEY_FILTER, master).apply();
    }

    @Override
    public String getStoriesList() {
        return mPrefs.getString(PREF_KEY_STORIES_LIST, null);
    }

    @Override
    public void setStoriesList(String stories) {
        mPrefs.edit().putString(PREF_KEY_STORIES_LIST, stories).apply();
    }

    @Override
    public Integer getCurrentFragment() {
        return mPrefs.getInt(PREF_KEY_CURRENT_FRAGMENT, 0);
    }

    @Override
    public void setCurrentFragment(Integer id) {
        mPrefs.edit().putInt(PREF_KEY_CURRENT_FRAGMENT, id).apply();
    }


    @Override
    public String getCurrentUserPhNo() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PHONE_NUMBER, null);
    }

    @Override
    public void setCurrentUserPhNo(String phoneNumber) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PHONE_NUMBER, phoneNumber).apply();
    }

    @Override
    public String getCurrentUserReferrals() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_REFERRALS, null);
    }

    @Override
    public void setCurrentUserReferrals(String referralCode) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_REFERRALS, referralCode).apply();
    }

    @Override
    public boolean getIsFav() {
        return mPrefs.getBoolean(PREF_KEY_IS_FAV_CLICKED, false);
    }

    @Override
    public void setIsFav(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_IS_FAV_CLICKED, status).apply();
    }

    @Override
    public boolean getisGenderStatus() {
        return mPrefs.getBoolean(PREF_KEY_GENDER_STATUS, false);
    }

    @Override
    public void setisGenderStatus(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_GENDER_STATUS, status).apply();
    }

    @Override
    public boolean getisPasswordStatus() {
        return mPrefs.getBoolean(PREF_KEY_PASSWORD_STATUS, false);
    }

    @Override
    public void setisPasswordStatus(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_PASSWORD_STATUS, status).apply();
    }

    @Override
    public void setServiceableStatus(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_SERVICEABLESTATUS, status).apply();
    }

    @Override
    public void setServiceableTitle(String title) {
        mPrefs.edit().putString(PREF_KEY_SERVICEABLETITLE, title).apply();
    }

    @Override
    public void setServiceableSubTitle(String subTitle) {
        mPrefs.edit().putString(PREF_KEY_SERVICEABLESUBTITLE, subTitle).apply();
    }

    @Override
    public boolean getServiceableStatus() {
        return mPrefs.getBoolean(PREF_KEY_SERVICEABLESTATUS, false);
    }

    @Override
    public String getServiceableTitle() {
        return mPrefs.getString(PREF_KEY_SERVICEABLETITLE,"");
    }

    @Override
    public String getServiceableSubTitle() {
        return mPrefs.getString(PREF_KEY_SERVICEABLESUBTITLE,"");

    }

    @Override
    public Integer getTotalOrders() {
        return mPrefs.getInt(PREF_KEY_TOTAL_ORDERS, 0);
    }

    @Override
    public void setTotalOrders(Integer orders) {
        mPrefs.edit().putInt(PREF_KEY_TOTAL_ORDERS, orders).apply();
    }

    @Override
    public boolean isHomeAddressAdded() {
        return mPrefs.getBoolean(PREF_KEY_HOME_ADDRESS_ADDED, false);
    }

    @Override
    public void setHomeAddressAdded(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_HOME_ADDRESS_ADDED, status).apply();
    }

    @Override
    public boolean isOfficeAddressAdded() {
        return mPrefs.getBoolean(PREF_KEY_OFFICE_ADDRESS_ADDED, false);
    }

    @Override
    public void setOfficeAddressAdded(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_OFFICE_ADDRESS_ADDED, status).apply();
    }

    @Override
    public int getRefundId() {
        return mPrefs.getInt(PREF_KEY_REFUND_ID, 0);
    }

    @Override
    public void setRefundId(int rcid) {
        mPrefs.edit().putInt(PREF_KEY_REFUND_ID, rcid).apply();
    }

    @Override
    public int getRegionId() {
        return mPrefs.getInt(PREF_KEY_REGION_ID, 0);
    }

    @Override
    public void setRegionId(int regiionId) {
        mPrefs.edit().putInt(PREF_KEY_REGION_ID, regiionId).apply();
    }

    @Override
    public int getCouponId() {
        return mPrefs.getInt(PREF_KEY_COUPON_ID, 0);

    }

    @Override
    public void setCouponId(int couponId) {
        mPrefs.edit().putInt(PREF_KEY_COUPON_ID, couponId).apply();
    }

    @Override
    public boolean getEmailStatus() {
        return mPrefs.getBoolean(PREF_KEY_EMAIL_STATUS, false);
    }

    @Override
    public void setEmailStatus(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_EMAIL_STATUS, status).apply();
    }

    @Override
    public Integer getVegType() {
        return mPrefs.getInt(PREF_KEY_VEG_TYPE, 0);
    }

    @Override
    public void setVegType(Integer type) {
        mPrefs.edit().putInt(PREF_KEY_VEG_TYPE, type).apply();
    }

    @Override
    public Integer getRatingSkips() {
        return mPrefs.getInt(PREF_KEY_RATING_SKIPS, 0);
    }

    @Override
    public void setRatingSkips(Integer skips) {
        mPrefs.edit().putInt(PREF_KEY_RATING_SKIPS, skips).apply();
    }

    @Override
    public Long getRatingOrderid() {
        return mPrefs.getLong(PREF_KEY_RATING_ORDER_ID, 0);
    }

    @Override
    public void setRatingOrderid(Long orderid) {
        mPrefs.edit().putLong(PREF_KEY_RATING_ORDER_ID, orderid).apply();
    }

    @Override
    public String getRatingDate() {
        return mPrefs.getString(PREF_KEY_RATING_DATE, null);
    }

    @Override
    public void setRatingDate(String date) {
        mPrefs.edit().putString(PREF_KEY_RATING_DATE, date).apply();
    }

    @Override
    public String getSupportNumber() {
        return mPrefs.getString(PREF_KEY_SUPPORT_NUMBER, "0");
    }

    @Override
    public void setSupportNumber(String number) {
        mPrefs.edit().putString(PREF_KEY_SUPPORT_NUMBER, number).apply();
    }

    @Override
    public boolean getRatingAppStatus() {
        return mPrefs.getBoolean(PREF_KEY_RATING_APP_STATUS, false);
    }

    @Override
    public void setRatingAppStatus(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_RATING_APP_STATUS, status).apply();
    }

    @Override
    public boolean isFilterApplied() {
        return mPrefs.getBoolean(PREF_KEY_FILTER_APPLIED, false);
    }

    @Override
    public void setIsFilterApplied(boolean filter) {
        mPrefs.edit().putBoolean(PREF_KEY_FILTER_APPLIED, filter).apply();
    }

    @Override
    public boolean getFunnelStatus() {
        return mPrefs.getBoolean(PREF_KEY_FUNNEL_STATUS, false);
    }

    @Override
    public void setFunnelStatus(boolean filter) {
        mPrefs.edit().putBoolean(PREF_KEY_FUNNEL_STATUS, filter).apply();
    }

    @Override
    public boolean getAppStartedAgain() {

        return mPrefs.getBoolean(PREF_KEY_APP_STARTED_AGAIN, false);
    }

    @Override
    public void setAppStartedAgain(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_APP_STARTED_AGAIN, status).apply();
    }

    @Override
    public String getApiToken() {
        return mPrefs.getString(PREF_KEY_API_TOKEN, "login");
    }

    @Override
    public void setApiToken(String token) {
        mPrefs.edit().putString(PREF_KEY_API_TOKEN, token).apply();
    }

    @Override
    public String getCouponCode() {
        return mPrefs.getString(PREF_KEY_COUPON_CODE, null);
    }

    @Override
    public void setCouponCode(String coupon) {
        mPrefs.edit().putString(PREF_KEY_COUPON_CODE, coupon).apply();
    }

    @Override
    public String getFirstAddress() {
        return mPrefs.getString(PREF_KEY_FIRST_ADDRESS, null);
    }

    @Override
    public void setFirstAddress(String address) {
        mPrefs.edit().putString(PREF_KEY_FIRST_ADDRESS, address).apply();
    }

    @Override
    public String getFirstLocatity() {
        return mPrefs.getString(PREF_KEY_FIRST_LOCALLITY, null);
    }

    @Override
    public void setFirstLocality(String locality) {
        mPrefs.edit().putString(PREF_KEY_FIRST_LOCALLITY, locality).apply();
    }

    @Override
    public String getFirstCity() {
        return mPrefs.getString(PREF_KEY_FIRST_CITY, null);
    }

    @Override
    public void setFirstCity(String city) {
        mPrefs.edit().putString(PREF_KEY_FIRST_CITY, city).apply();
    }

    @Override
    public String getPromotionShowedDate() {
        return  mPrefs.getString(PREF_KEY_PROMOTION_SEEN_DATE, " ");
    }

    @Override
    public void setPromotionShowedDate(String date) {
        mPrefs.edit().putString(PREF_KEY_PROMOTION_SEEN_DATE, date).apply();
    }

    @Override
    public boolean getPromotionSeen() {
        return mPrefs.getBoolean(PREF_KEY_PROMOTION_SEEN, false);
    }

    @Override
    public void setPromotionSeen(boolean seen) {
        mPrefs.edit().putBoolean(PREF_KEY_PROMOTION_SEEN, seen).apply();
    }

    @Override
    public Integer getPromotionId() {
        return mPrefs.getInt(PREF_KEY_PROMOTION_ID, 0);
    }

    @Override
    public void setPromotionId(Integer promotionid) {
        mPrefs.edit().putInt(PREF_KEY_PROMOTION_ID, promotionid).apply();
    }

    @Override
    public Integer getPromotionDisplayedCount() {
        return mPrefs.getInt(PREF_KEY_PROMOTION_SEEN_COUNT, 0);
    }

    @Override
    public void setPromotionDisplayedCount(Integer count) {
        mPrefs.edit().putInt(PREF_KEY_PROMOTION_SEEN_COUNT, count).apply();
    }

    @Override
    public String getCartDetails() {
        return mPrefs.getString(PREF_KEY_CART, null);
    }

    @Override
    public void setCartDetails(String jsonCart) {
        mPrefs.edit().putString(PREF_KEY_CART, jsonCart).apply();
    }

    @Override
    public String getOrderInstruction() {
        return mPrefs.getString(PREF_KEY_ORDER_INSTRUCTION, null);
    }

    @Override
    public void setorderInstruction(String instruction) {
        mPrefs.edit().putString(PREF_KEY_ORDER_INSTRUCTION, instruction).apply();

    }

    public boolean getPrefKeyIsUserLoggedIn() {
        return mPrefs.getBoolean(PREF_KEY_IS_USER_LOGGED_IN, false);
    }
}
