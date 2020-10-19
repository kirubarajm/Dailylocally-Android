package com.dailylocally.ui.communityOnboarding;

public interface CommunityOnBoardingActivityNavigator {

    void handleError(Throwable throwable);

    void goBack();

    void skip();

    void action();
}
