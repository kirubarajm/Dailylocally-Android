package com.dailylocally.ui.community.details;

import im.getsocial.sdk.communities.GetSocialActivity;

public interface EventDetailsNavigator {

    void handleError(Throwable throwable);

    void back();

    void postComment(GetSocialActivity getSocialActivity);
}
