package com.dailylocally.ui.coupons;

public interface CouponsNavigator {

    void handleError(Throwable throwable);

    void goBack();

    void validateCouponClick();

    void validateCouponSuccess(String message);

    void validateCouponFailure(String message);

}
