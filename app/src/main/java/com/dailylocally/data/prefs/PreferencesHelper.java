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

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getCurrentUserEmail();

    void setCurrentUserEmail(String email);

    Long getCurrentUserId();

    void setCurrentUserId(Long userId);

    Long getCurrentPromotionUserId();

    void setCurrentPromotionUserId(Long userId);

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    String getCurrentUserName();

    void setCurrentUserName(String userName);


    void setCurrentUserProfilePicUrl(String profilePicUrl);

    boolean getIsLoggedIn();

    void setIsLoggedIn(boolean isLoggedIn);

    String getCartDetails();

    void setCartDetails(String jsonCart);


    String getOrderInstruction();

    void setorderInstruction(String instruction);


    String getCurrentAddressTitle();

    void setCurrentAddressTitle(String title);

    int getRefundBalance();

    void setRefundBalance(int refundBalance);


    String getRazorpayCustomerId();

    void setRazorpayCustomerId(String title);


    String getCurrentAddressArea();

    void setCurrentAddressArea(String area);

    String getCurrentAddress();

    void setCurrentAddress(String area);

    String getCurrentLat();

    void setChatOrderid(String orderid);

    String getChatOrderid();


    void setCurrentLat(double lat);


    String getCurrentLng();


    void setCurrentLng(double lng);


    Long getMakeitID();


    void setMakeitID(Long id);


    Long getOrderId();


    void setOrderId(Long orderId);


    Long getAddressId();


    void setAddressId(Long orderId);


    String getMaster();

    void setMaster(String master);


    String getFilterSort();

    void setFilterSort(String master);

    String getStoriesList();

    void setStoriesList(String stories);


    Integer getCurrentFragment();


    void setCurrentFragment(Integer orderId);


    String getCurrentUserPhNo();

    void setCurrentUserPhNo(String phoneNumber);

    String getCurrentUserReferrals();

    void setCurrentUserReferrals(String area);


    boolean getIsFav();

    void setIsFav(boolean status);


    boolean getisGenderStatus();

    void setisGenderStatus(boolean status);


    boolean getisPasswordStatus();

    void setisPasswordStatus(boolean status);

    void setServiceableStatus(boolean status);
    void setServiceableTitle(String title);
    void setServiceableSubTitle(String subTitle);

 boolean getServiceableStatus();
    String getServiceableTitle();
    String getServiceableSubTitle();


    Integer getTotalOrders();


    void setTotalOrders(Integer orders);


    boolean isHomeAddressAdded();

    void setHomeAddressAdded(boolean status);

    boolean isOfficeAddressAdded();

    void setOfficeAddressAdded(boolean status);


    int getRefundId();

    void setRefundId(int rcid);


    int getRegionId();

    void setRegionId(int regiionId);


    int getCouponId();

    void setCouponId(int couponId);


    boolean getEmailStatus();

    void setEmailStatus(boolean status);

    Integer getVegType();

    void setVegType(Integer type);

    Integer getRatingSkips();

    void setRatingSkips(Integer skips);

    Long getRatingOrderid();

    void setRatingOrderid(Long orderid);


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

    String getFirstAddress();

    void setFirstAddress(String address);

    String getFirstLocatity();

    void setFirstLocality(String locality);

    String getFirstCity();

    void setFirstCity(String city);


    String getPromotionShowedDate();

    void setPromotionShowedDate(String date);

    boolean getPromotionSeen();

    void setPromotionSeen(boolean seen);

    Integer getPromotionId();

    void setPromotionId(Integer promotionid);

    Integer getPromotionDisplayedCount();

    void setPromotionDisplayedCount(Integer count);


}
