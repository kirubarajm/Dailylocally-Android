package com.dailylocally.ui.joinCommunity;

import java.util.List;

public interface CommunityActivityNavigator {

    void handleError(Throwable throwable);

    void goBack();


    void joinClick();

    void uploadJoinImageClick();

    void close();


    void joinTheCommunityClick();


    void uploading();

    void uploading1();

    void uploaded(String imageUrl);

    void uploaded1(String imageUrl);

    void communityJoined(String message);

    void showAlert(String title,String message,String locationAddress,String area,String lat,
                   String lng,String pinCode);

    void showAlert(String title,String message);


    void knowMore();
}
