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


import com.dailylocally.data.DataManager;


public interface PreferencesHelper {
    

    String getCurrentUserEmail();
    void setCurrentUserEmail(String email);

    String getCurrentUserId();
    void setCurrentUserId(String userId);

    String getCurrentPromotionUserId();
    void setCurrentPromotionUserId(String userId);

    String getCurrentUserName();
    void setCurrentUserName(String userName);


    String getCartDetails();
    void setCartDetails(String jsonCart);


    String getOrderInstruction();
    void setorderInstruction(String instruction);

    String getCurrentAddressTitle();
    void setCurrentAddressTitle(String title);


    String getRazorpayCustomerId();
    void setRazorpayCustomerId(String title);

    String getCurrentAddressArea();
    void setCurrentAddressArea(String area);

    String getCurrentAddress();
    void setCurrentAddress(String area);

    void setChatOrderid(String orderid);
    String getChatOrderid();

    void setCurrentLat(String lat);
    String getCurrentLat();

    String getCurrentLng();
    void setCurrentLng(String lng);


    String getAddressId();
    void setAddressId(String orderId);

    String getFirstAddress();
    void setFirstAddress(String address);

    String getFirstLocatity();
    void setFirstLocality(String locality);

    String getFirstCity();
    void setFirstCity(String city);

    String getMaster();
    void setMaster(String master);


    String getCurrentUserPhNo();
    void setCurrentUserPhNo(String phoneNumber);

    String getCurrentUserReferrals();
    void setCurrentUserReferrals(String area);

    void setServiceableStatus(boolean status);
    void setServiceableTitle(String title);
    void setServiceableSubTitle(String subTitle);
    boolean getServiceableStatus();
    String getServiceableTitle();
    String getServiceableSubTitle();

    boolean isHomeAddressAdded();
    void setHomeAddressAdded(boolean status);

    boolean isOfficeAddressAdded();
    void setOfficeAddressAdded(boolean status);

    int getCouponId();
    void setCouponId(int couponId);

    Integer getRatingSkips();
    void setRatingSkips(Integer skips);

    String getRatingOrderid();
    void setRatingOrderid(String orderid);

    String getRatingDate();
    void setRatingDate(String date);

    String getSupportNumber();
    void setSupportNumber(String number);


    boolean getRatingAppStatus();
    void setRatingAppStatus(boolean status);

    boolean isFilterApplied();

    void setIsFilterApplied(boolean filter);

    boolean getFunnelStatus();
    void setFunnelStatus(boolean status);

    boolean getAppStartedAgain();
    void setAppStartedAgain(boolean status);


    String getApiToken();
    void setApiToken(String token);

    String getCouponCode();
    void setCouponCode(String coupon);


    String getPromotionShowedDate();
    void setPromotionShowedDate(String date);

    boolean getPromotionSeen();
    void setPromotionSeen(boolean seen);

    Integer getPromotionId();
    void setPromotionId(Integer promotionid);

    Integer getPromotionDisplayedCount();
    void setPromotionDisplayedCount(Integer count);

    boolean isUserRegistered();
    void setUserRegistrationStatus(boolean status);

    boolean isUserAddress();
    void setUserAddress(boolean address);

}
