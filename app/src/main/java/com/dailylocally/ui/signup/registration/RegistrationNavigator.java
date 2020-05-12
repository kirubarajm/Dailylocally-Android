package com.dailylocally.ui.signup.registration;

import com.dailylocally.ui.home.region.RegionSearchModel;

import java.util.List;

public interface RegistrationNavigator {

    void handleError(Throwable throwable);

    void proceedClick();

    void genderSuccess(String strMessage);

    void genderFailure(String strMessage);

    void regionListLoaded(List<RegionSearchModel.Result> regionList);

}
