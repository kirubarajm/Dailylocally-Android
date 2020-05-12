package com.dailylocally.ui.splash;

public interface SplashActivityNavigator {

    void handleError(Throwable throwable);

    void checkForUserLoginMode(boolean trueOrFalse);
    void update(boolean updateStatus, boolean forceUpdateStatus);
    void checkForUserGenderStatus(boolean trueOrFalse);
}
