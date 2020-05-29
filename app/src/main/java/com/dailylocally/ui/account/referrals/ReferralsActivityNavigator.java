package com.dailylocally.ui.account.referrals;

public interface ReferralsActivityNavigator {

    void handleError(Throwable throwable);

    void sendReferralsClick();

    void success(String strMessage);

    void failure(String strMessage);

    void goBack();


}
