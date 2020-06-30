package com.dailylocally.ui.productDetail.dialogProductCancel;

public interface DialogProductCancelCallBack {

    void productCancelClick();

    void goBackClick();

    void cancelSuccess(String message);

    void cancelFailed(String message);

}
