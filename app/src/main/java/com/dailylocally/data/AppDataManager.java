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

import com.dailylocally.data.prefs.PreferencesHelper;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;

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
    public void setLogout() {
        updateUserInformation(null, null, null, null, null);
    }


    @Override
    public void updateUserInformation(String userId, String userName, String userEmail, String PhoneNumber, String referralCode) {
        setCurrentUserId(userId);
        setCurrentUserName(userName);
        setCurrentUserEmail(userEmail);
        setCurrentUserPhNo(PhoneNumber);
        setCurrentUserReferrals(referralCode);

    }

    @Override
    public void userRegistered(boolean isRegistered) {
        setUserRegistrationStatus(isRegistered);
    }


    @Override
    public void showFunnel(boolean status) {
        setFunnelStatus(status);
    }

    @Override
    public void updateCurrentAddress(String title, String address, String lat, String lng, String area, String aid) {
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
    public void savePromotionId(int promotionid) {
        setPromotionId(promotionid);
    }

    @Override
    public void savePromotionDailyCount(int count) {
        setPromotionDailyCount(count);
    }

    @Override
    public void savePromotionAppStartAgain(boolean ststus) {
        setPromotionAppStartAgain(ststus);
    }

    @Override
    public void savePromotionCustomerId(String customerid) {
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
    public void saveCouponId(int couponid) {
        setCouponId(couponid);
    }

    @Override
    public void saveRatingOrderId(String orderid) {
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
    public void saveFiletrSort(String filterSort) {
        setFilterSort(filterSort);
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
    public boolean homeAddressadded(boolean status) {
        return false;
    }

    @Override
    public boolean officeAddressadded(boolean status) {
        return false;
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



    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(String userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentPromotionUserId() {
        return mPreferencesHelper.getCurrentPromotionUserId();
    }

    @Override
    public void setCurrentPromotionUserId(String userId) {
        mPreferencesHelper.setCurrentPromotionUserId(userId);
    }


    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
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
    public void setCurrentLat(String lat) {
        mPreferencesHelper.setCurrentLat(lat);
    }

    @Override
    public String getChatOrderid() {
        return mPreferencesHelper.getChatOrderid();
    }

    @Override
    public void setChatOrderid(String orderid) {
        mPreferencesHelper.setChatOrderid(orderid);
    }

    @Override
    public String getCurrentLng() {
        return mPreferencesHelper.getCurrentLng();
    }

    @Override
    public void setCurrentLng(String lng) {
        mPreferencesHelper.setCurrentLng(lng);

    }


    @Override
    public String getAddressId() {
        return mPreferencesHelper.getAddressId();
    }

    @Override
    public void setAddressId(String aid) {
        mPreferencesHelper.setAddressId(aid);
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
    public boolean getServiceableStatus() {
        return mPreferencesHelper.getServiceableStatus();
    }

    @Override
    public void setServiceableStatus(boolean status) {
        mPreferencesHelper.setServiceableStatus(status);
    }

    @Override
    public String getServiceableTitle() {
        return mPreferencesHelper.getServiceableTitle();
    }

    @Override
    public void setServiceableTitle(String title) {
        mPreferencesHelper.setServiceableTitle(title);
    }

    @Override
    public String getServiceableSubTitle() {
        return mPreferencesHelper.getServiceableSubTitle();
    }

    @Override
    public void setServiceableSubTitle(String subTitle) {
        mPreferencesHelper.setServiceableSubTitle(subTitle);
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
    public int getCouponId() {
        return mPreferencesHelper.getCouponId();
    }

    @Override
    public void setCouponId(int couponId) {
        mPreferencesHelper.setCouponId(couponId);
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
    public String getRatingOrderid() {
        return mPreferencesHelper.getRatingOrderid();
    }

    @Override
    public void setRatingOrderid(String orderid) {
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
    public String getFilterSort() {
        return mPreferencesHelper.getFilterSort();
    }

    @Override
    public void setFilterSort(String filterSort) {
mPreferencesHelper.setFilterSort(filterSort);
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
    public Integer getPromotionDailyCount() {
        return mPreferencesHelper.getPromotionDailyCount();
    }

    @Override
    public void setPromotionDailyCount(int count) {
mPreferencesHelper.setPromotionDailyCount(count);
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
    public Boolean getPromotionAppStartAgain() {
        return mPreferencesHelper.getPromotionAppStartAgain();
    }

    @Override
    public void setPromotionAppStartAgain(Boolean status) {
mPreferencesHelper.setPromotionAppStartAgain(status);
    }

    @Override
    public boolean isUserRegistered() {
        return mPreferencesHelper.isUserRegistered();
    }

    @Override
    public void setUserRegistrationStatus(boolean status) {
mPreferencesHelper.setUserRegistrationStatus(status);
    }

    @Override
    public boolean isUserAddress() {
        return mPreferencesHelper.isUserAddress();
    }

    @Override
    public void setUserAddress(boolean address) {
        mPreferencesHelper.setUserAddress(address);
    }

    @Override
    public Integer getGender() {
        return mPreferencesHelper.getGender();
    }

    @Override
    public void setGender(Integer gender) {
        mPreferencesHelper.setGender(gender);
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
