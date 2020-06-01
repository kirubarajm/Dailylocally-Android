package com.dailylocally.ui.signup.registration;

public interface RegistrationNavigator {

    void handleError(Throwable throwable);

    void proceedClick();


    void genderSuccess(String strMessage,Boolean flagEdit);

    void genderFailure(String strMessage);



}
