package com.dailylocally.ui.account;


import com.dailylocally.ui.signup.registration.GetUserDetailsResponse;

public interface MyAccountNavigator {

    void handleError(Throwable throwable);


    void changeAddress();

    void orderHistory();

    void favourites();

    void referrals();

    void favorites();

    void offers();

    void logout();

    void logoutFailure(String message);

    void feedbackAndSupport();

    void editProfile();

}
