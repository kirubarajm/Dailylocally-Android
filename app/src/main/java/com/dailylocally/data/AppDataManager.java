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

import android.content.Context;

import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.dailylocally.data.prefs.PreferencesHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;

    private final Gson mGson;

    private Api mApiHelper;

    private PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, Gson gson, PreferencesHelper preferencesHelper) {
        mContext = context;
        mGson = gson;
        mPreferencesHelper = preferencesHelper;
        //mApiHelper = mApiHelper;
    }

    @Override
    public Observable<Boolean> seedDatabaseOptions() {
        return null;
    }

    @Override
    public Observable<Boolean> seedDatabaseQuestions() {
        return null;
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                false);
    }

    @Override
    public void setLogout() {
        updateUserInformation(0L, null, null, null, null);
    }

    @Override
    public void updateApiHeader(Long userId, String accessToken) {

    }

    @Override
    public void updateUserInfo
            (String accessToken,
             Long userId,
             LoggedInMode loggedInMode,
             String userName,
             String email, boolean isLoggedIn
            ) {
        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);
        setIsLoggedIn(isLoggedIn);

        /*setCurrentUserProfilePicUrl(profilePicPath)*/
        ;
    }

    @Override
    public void updateUserInformation(Long userId, String userName, String userEmail, String PhoneNumber, String referralCode) {
        setCurrentUserId(userId);
        setCurrentUserName(userName);
        setCurrentUserEmail(userEmail);
        setCurrentUserPhNo(PhoneNumber);
        setCurrentUserReferrals(referralCode);

    }

    @Override
    public void updateUserGender(boolean genderStatus) {
        setisGenderStatus(genderStatus);
    }

    @Override
    public void updateEmailStatus(boolean status) {
        setEmailStatus(status);
    }

    @Override
    public void updateUserPasswordStatus(boolean passwordStatus) {


        setisPasswordStatus(passwordStatus);

    }

    @Override
    public void showFunnel(boolean status) {
        setFunnelStatus(status);
    }

    @Override
    public void updateCurrentAddress(String title, String address, double lat, double lng, String area, Long aid) {
       /* setCurrentAddressTitle(title);
        setCurrentAddressArea(area);
        setCurrentAddress(address);
        setCurrentLat(lat);
        setCurrentLng(lng);*/

        setCurrentAddressTitle(title);
        setCurrentAddressArea(area);
        setCurrentAddress(address);
        setCurrentLat(lat);
        setCurrentLng(lng);
        setAddressId(aid);
    }

    @Override
    public void saveMaster(String master) {
        setMaster(master);
    }

    @Override
    public void saveFilterSort(String filters) {
        setFilterSort(filters);
    }

    @Override
    public void saveStoriesList(String stories) {
        setStoriesList(stories);
    }

    @Override
    public void saveVegType(Integer type) {
        setVegType(type);
    }

    @Override
    public void currentFragment(Integer id) {
        setCurrentFragment(id);

    }


    @Override
    public void kitchenId(Long id) {
        setMakeitID(id);
    }

    @Override
    public void totalOrders(Integer orders) {
        setTotalOrders(orders);
    }

    @Override
    public void saveRefundId(int rcid) {
        mPreferencesHelper.setRefundId(rcid);
    }

    @Override
    public void savePromotionId(int promotionid) {
        setPromotionId(promotionid);
    }

    @Override
    public void savePromotionCustomerId(Long customerid) {
        setCurrentPromotionUserId(customerid);
    }

    @Override
    public void savePromotionDisplayedCount(int count) {
        setPromotionDisplayedCount(count);
    }

    @Override
    public void savePromotionShowedDate(String date) {
        setPromotionShowedDate(date);
    }

    @Override
    public void savePromotionSeen(boolean seen) {
        setPromotionSeen(seen);
    }

    @Override
    public void saveRazorpayCustomerId(String razorpayCustomerId) {
        setRazorpayCustomerId(razorpayCustomerId);
    }

    @Override
    public void saveRefundBalance(int refundBalance) {
        setRefundBalance(refundBalance);
    }

    @Override
    public void saveRegionId(int regionid) {
        setRegionId(regionid);
    }

    @Override
    public void saveCouponId(int couponid) {
        setCouponId(couponid);
    }

    @Override
    public void saveRatingOrderId(Long orderid) {
        setRatingOrderid(orderid);
    }

    @Override
    public void saveRatingSkipDate(String date, int skips) {
        setRatingSkips(skips);
        setRatingDate(date);
    }

    @Override
    public void saveRatingSkipDate(int skips) {
        setRatingSkips(skips);
    }

    @Override
    public void saveRatingAppStatus(boolean live) {
        setRatingAppStatus(live);
    }

    @Override
    public void saveIsFilterApplied(boolean filter) {
        setIsFilterApplied(filter);
    }

    @Override
    public void saveApiToken(String token) {
        setApiToken(token);
    }

    @Override
    public void saveCouponCode(String coupon) {
        setCouponCode(coupon);
    }

    @Override
    public void saveChatOrderID(String orderid) {
        setChatOrderid(orderid);
    }

    @Override
    public void saveSupportNumber(String number) {
        setSupportNumber(number);
    }

    @Override
    public void orderInstruction(String instruction) {
        setorderInstruction(instruction);
    }

    @Override
    public void saveServiceableStatus(boolean status, String title, String subtitle) {
        setServiceableStatus(status);
        setServiceableTitle(title);
        setServiceableSubTitle(subtitle);
    }

    @Override
    public void currentOrderId(Long orderId) {

        mPreferencesHelper.getOrderId();
    }

    @Override
    public boolean homeAddressadded(boolean status) {
        return false;
    }

    @Override
    public boolean officeAddressadded(boolean status) {
        return false;
    }

    @Override
    public void isFavClicked(boolean status) {
        setIsFav(status);
    }

    @Override
    public void appStartedAgain(boolean status) {
        setAppStartedAgain(status);
    }

    @Override
    public void saveFirstLocation(String address, String locality, String city) {
        setFirstAddress(address);
        setFirstLocality(locality);
        setFirstCity(city);
    }

   /* @Override
    public Call<LoginResponse> userContinueClick(String name, String job) {
        return null;
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return mApiHelper.doServerLoginApiCall(request);
    }*/

    @Override
    public String getAccessToken() {
        return null;
    }

    @Override
    public void setAccessToken(String accessToken) {

    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public Long getCurrentPromotionUserId() {
        return mPreferencesHelper.getCurrentPromotionUserId();
    }

    @Override
    public void setCurrentPromotionUserId(Long userId) {
mPreferencesHelper.setCurrentPromotionUserId(userId);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return 0;
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {

    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    /*
        @Override
        public String getCurrentUserProfilePicUrl() {
            return null;
        }
    */
    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {

    }

    @Override
    public boolean getIsLoggedIn() {
        return mPreferencesHelper.getIsLoggedIn();
    }

    @Override
    public void setIsLoggedIn(boolean isLoggedIn) {
        mPreferencesHelper.setIsLoggedIn(isLoggedIn);
    }

    @Override
    public String getCurrentAddressTitle() {
        return mPreferencesHelper.getCurrentAddressTitle();
    }

    @Override
    public void setCurrentAddressTitle(String title) {
        mPreferencesHelper.setCurrentAddressTitle(title);
    }

    @Override
    public int getRefundBalance() {
        return mPreferencesHelper.getRefundBalance();
    }

    @Override
    public void setRefundBalance(int refundBalance) {
        mPreferencesHelper.setRefundBalance(refundBalance);
    }

    @Override
    public String getRazorpayCustomerId() {
        return mPreferencesHelper.getRazorpayCustomerId();
    }

    @Override
    public void setRazorpayCustomerId(String razorpayCustomerId) {
        mPreferencesHelper.setRazorpayCustomerId(razorpayCustomerId);
    }

    @Override
    public String getCurrentAddressArea() {
        return mPreferencesHelper.getCurrentAddressArea();
    }

    @Override
    public void setCurrentAddressArea(String area) {
        mPreferencesHelper.setCurrentAddressArea(area);
    }

    @Override
    public String getCurrentAddress() {
        return mPreferencesHelper.getCurrentAddress();
    }

    @Override
    public void setCurrentAddress(String address) {
        mPreferencesHelper.setCurrentAddress(address);
    }

    @Override
    public String getCurrentLat() {
        return mPreferencesHelper.getCurrentLat();
    }

    @Override
    public void setChatOrderid(String orderid) {
mPreferencesHelper.setChatOrderid(orderid);
    }

    @Override
    public String getChatOrderid() {
        return mPreferencesHelper.getChatOrderid();
    }

    @Override
    public void setCurrentLat(double lat) {
        mPreferencesHelper.setCurrentLat(lat);
    }

    @Override
    public String getCurrentLng() {
        return mPreferencesHelper.getCurrentLng();
    }

    @Override
    public void setCurrentLng(double lng) {
        mPreferencesHelper.setCurrentLng(lng);

    }

    @Override
    public Long getMakeitID() {
        return mPreferencesHelper.getMakeitID();
    }

    @Override
    public void setMakeitID(Long lng) {
        mPreferencesHelper.setMakeitID(lng);
    }

    @Override
    public Long getOrderId() {
        return mPreferencesHelper.getOrderId();
    }

    @Override
    public void setOrderId(Long orderId) {
        mPreferencesHelper.setOrderId(orderId);
    }

    @Override
    public Long getAddressId() {
        return mPreferencesHelper.getAddressId();
    }

    @Override
    public void setAddressId(Long orderId) {
        mPreferencesHelper.setAddressId(orderId);
    }

    @Override
    public String getMaster() {
        return mPreferencesHelper.getMaster();
    }

    @Override
    public void setMaster(String master) {
        mPreferencesHelper.setMaster(master);
    }

    @Override
    public String getFilterSort() {
        return mPreferencesHelper.getFilterSort();
    }

    @Override
    public void setFilterSort(String filters) {
        mPreferencesHelper.setFilterSort(filters);
    }

    @Override
    public String getStoriesList() {
        return mPreferencesHelper.getStoriesList();
    }

    @Override
    public void setStoriesList(String stories) {
        mPreferencesHelper.setStoriesList(stories);
    }

    @Override
    public Integer getCurrentFragment() {
        return mPreferencesHelper.getCurrentFragment();
    }

    @Override
    public void setCurrentFragment(Integer id) {
        mPreferencesHelper.setCurrentFragment(id);
    }

    @Override
    public String getCurrentUserPhNo() {
        return mPreferencesHelper.getCurrentUserPhNo();
    }

    @Override
    public void setCurrentUserPhNo(String phoneNumber) {
        mPreferencesHelper.setCurrentUserPhNo(phoneNumber);
    }

    @Override
    public String getCurrentUserReferrals() {
        return mPreferencesHelper.getCurrentUserReferrals();
    }

    @Override
    public void setCurrentUserReferrals(String referralCode) {
        mPreferencesHelper.setCurrentUserReferrals(referralCode);
    }

    @Override
    public boolean getIsFav() {
        return mPreferencesHelper.getIsFav();
    }

    @Override
    public void setIsFav(boolean status) {
        mPreferencesHelper.setIsFav(status);
    }

    @Override
    public boolean getisGenderStatus() {
        return mPreferencesHelper.getisGenderStatus();
    }

    @Override
    public void setisGenderStatus(boolean status) {
        mPreferencesHelper.setisGenderStatus(status);
    }

    @Override
    public boolean getisPasswordStatus() {
        return mPreferencesHelper.getisPasswordStatus();
    }

    @Override
    public void setisPasswordStatus(boolean status) {
        mPreferencesHelper.setisPasswordStatus(status);
    }

    @Override
    public void setServiceableStatus(boolean status) {
mPreferencesHelper.setServiceableStatus(status);
    }

    @Override
    public void setServiceableTitle(String title) {
mPreferencesHelper.setServiceableTitle(title);
    }

    @Override
    public void setServiceableSubTitle(String subTitle) {
mPreferencesHelper.setServiceableSubTitle(subTitle);
    }

    @Override
    public boolean getServiceableStatus() {
        return mPreferencesHelper.getServiceableStatus();
    }

    @Override
    public String getServiceableTitle() {
        return mPreferencesHelper.getServiceableTitle();
    }

    @Override
    public String getServiceableSubTitle() {
        return mPreferencesHelper.getServiceableSubTitle();
    }

    @Override
    public Integer getTotalOrders() {
        return mPreferencesHelper.getTotalOrders();
    }

    @Override
    public void setTotalOrders(Integer orders) {
        mPreferencesHelper.setTotalOrders(orders);
    }

    @Override
    public boolean isHomeAddressAdded() {
        return mPreferencesHelper.isHomeAddressAdded();
    }

    @Override
    public void setHomeAddressAdded(boolean status) {

        mPreferencesHelper.setHomeAddressAdded(status);
    }

    @Override
    public boolean isOfficeAddressAdded() {
        return mPreferencesHelper.isOfficeAddressAdded();
    }

    @Override
    public void setOfficeAddressAdded(boolean status) {
        mPreferencesHelper.setOfficeAddressAdded(status);
    }

    @Override
    public int getRefundId() {
        return mPreferencesHelper.getRefundId();
    }

    @Override
    public void setRefundId(int rcid) {
        mPreferencesHelper.setRefundId(rcid);
    }

    @Override
    public int getRegionId() {
        return mPreferencesHelper.getRegionId();
    }

    @Override
    public void setRegionId(int regiionId) {

        mPreferencesHelper.setRegionId(regiionId);

    }

    @Override
    public int getCouponId() {
        return mPreferencesHelper.getCouponId();
    }

    @Override
    public void setCouponId(int couponId) {
        mPreferencesHelper.setCouponId(couponId);
    }

    @Override
    public boolean getEmailStatus() {
        return mPreferencesHelper.getEmailStatus();
    }

    @Override
    public void setEmailStatus(boolean status) {
        mPreferencesHelper.setEmailStatus(status);
    }

    @Override
    public Integer getVegType() {
        return mPreferencesHelper.getVegType();
    }

    @Override
    public void setVegType(Integer type) {
        mPreferencesHelper.setVegType(type);
    }

    @Override
    public Integer getRatingSkips() {
        return mPreferencesHelper.getRatingSkips();
    }

    @Override
    public void setRatingSkips(Integer skips) {
        mPreferencesHelper.setRatingSkips(skips);
    }

    @Override
    public Long getRatingOrderid() {
        return mPreferencesHelper.getRatingOrderid();
    }

    @Override
    public void setRatingOrderid(Long orderid) {
        mPreferencesHelper.setRatingOrderid(orderid);
    }

    @Override
    public String getRatingDate() {
        return mPreferencesHelper.getRatingDate();
    }

    @Override
    public void setRatingDate(String date) {
        mPreferencesHelper.setRatingDate(date);
    }

    @Override
    public String getSupportNumber() {
        return mPreferencesHelper.getSupportNumber();
    }

    @Override
    public void setSupportNumber(String number) {
        mPreferencesHelper.setSupportNumber(number);
    }

    @Override
    public boolean getRatingAppStatus() {
        return mPreferencesHelper.getRatingAppStatus();
    }

    @Override
    public void setRatingAppStatus(boolean status) {
        mPreferencesHelper.setRatingAppStatus(status);
    }

    @Override
    public boolean isFilterApplied() {
        return mPreferencesHelper.isFilterApplied();
    }

    @Override
    public void setIsFilterApplied(boolean filter) {
        mPreferencesHelper.setIsFilterApplied(filter);
    }

    @Override
    public boolean getFunnelStatus() {
        return mPreferencesHelper.getFunnelStatus();
    }

    @Override
    public void setFunnelStatus(boolean status) {
        mPreferencesHelper.setFunnelStatus(status);
    }

    @Override
    public boolean getAppStartedAgain() {
        return mPreferencesHelper.getAppStartedAgain();
    }

    @Override
    public void setAppStartedAgain(boolean status) {
        mPreferencesHelper.setAppStartedAgain(status);
    }

    @Override
    public String getApiToken() {
        return mPreferencesHelper.getApiToken();
    }

    @Override
    public void setApiToken(String token) {
        mPreferencesHelper.setApiToken(token);
    }

    @Override
    public String getCouponCode() {
        return mPreferencesHelper.getCouponCode();
    }

    @Override
    public void setCouponCode(String coupon) {
        mPreferencesHelper.setCouponCode(coupon);
    }

    @Override
    public String getFirstAddress() {
        return mPreferencesHelper.getFirstAddress();
    }

    @Override
    public void setFirstAddress(String address) {
        mPreferencesHelper.setFirstAddress(address);
    }

    @Override
    public String getFirstLocatity() {
        return mPreferencesHelper.getFirstLocatity();
    }

    @Override
    public void setFirstLocality(String locality) {
        mPreferencesHelper.setFirstLocality(locality);
    }

    @Override
    public String getFirstCity() {
        return mPreferencesHelper.getFirstCity();
    }

    @Override
    public void setFirstCity(String city) {
        mPreferencesHelper.setFirstCity(city);
    }

    @Override
    public String getPromotionShowedDate() {
        return mPreferencesHelper.getPromotionShowedDate();
    }

    @Override
    public void setPromotionShowedDate(String date) {
        mPreferencesHelper.setPromotionShowedDate(date);
    }

    @Override
    public boolean getPromotionSeen() {
        return mPreferencesHelper.getPromotionSeen();
    }

    @Override
    public void setPromotionSeen(boolean seen) {
        mPreferencesHelper.setPromotionSeen(seen);
    }

    @Override
    public Integer getPromotionId() {
        return mPreferencesHelper.getPromotionId();
    }

    @Override
    public void setPromotionId(Integer promotionid) {
        mPreferencesHelper.setPromotionId(promotionid);
    }

    @Override
    public Integer getPromotionDisplayedCount() {
        return mPreferencesHelper.getPromotionDisplayedCount();
    }

    @Override
    public void setPromotionDisplayedCount(Integer count) {
        mPreferencesHelper.setPromotionDisplayedCount(count);
    }


    @Override
    public String getCartDetails() {
        return mPreferencesHelper.getCartDetails();
    }

    @Override
    public void setCartDetails(String jsonCart) {
        mPreferencesHelper.setCartDetails(jsonCart);
    }

    @Override
    public String getOrderInstruction() {
        return mPreferencesHelper.getOrderInstruction();
    }

    @Override
    public void setorderInstruction(String instruction) {
        mPreferencesHelper.setorderInstruction(instruction);
    }
}
