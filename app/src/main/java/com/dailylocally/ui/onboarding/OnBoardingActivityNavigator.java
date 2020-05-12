package com.dailylocally.ui.onboarding;

public interface OnBoardingActivityNavigator {

    void handleError(Throwable throwable);

    void checkForUserLoginMode(boolean trueOrFalse);
    void update(boolean updateStatus, boolean forceUpdateStatus);
}
