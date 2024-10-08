package com.dailylocally.ui.splash;

public interface SplashNavigator {

    void handleError(Throwable throwable);

    void checkForUserLogin(boolean status);
    void update(boolean updateStatus, boolean status,String addressCreated);
    void userAlreadyRegistered(boolean status);

    void userAddressActivity();
}
