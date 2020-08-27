package com.dailylocally.ui.joinCommunity;

import java.util.List;

public interface CommunityActivityNavigator {

    void handleError(Throwable throwable);

    void goBack();

    void registrationClick();

    void joinClick();

    void uploadJoinImageClick();

    void uploadRegisterImageClick();

    void joinTheCommunityClick();

    void completeRegistrationClick();

    void uploading();

    void uploading1();

    void uploaded(String imageUrl);

    void uploaded1(String imageUrl);

    void whatAppScreenSuccess(String message);

    void mapLatLngArray(List<CommunityResponse.Result> results);

}
