package com.dailylocally.ui.account;


public interface MyAccountNavigator {

    void handleError(Throwable throwable);


    void changeAddress();

    void orderHistory();

    void couponsAndOffers();

    void favourites();

    void referrals();

    void favorites();

    void offers();

    void logout();

    void logoutFailure(String message);

    void feedbackAndSupport();

    void editProfile();

    void transactions();

    void gotoCommunity();
}
