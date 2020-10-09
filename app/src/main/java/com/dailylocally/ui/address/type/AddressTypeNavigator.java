package com.dailylocally.ui.address.type;

public interface AddressTypeNavigator {

    void handleError(Throwable throwable);

    void checkForUserLogin(boolean status);
    void update(boolean updateStatus, boolean status);
    void userAlreadyRegistered(boolean status);

    void userAddressActivity();
}
