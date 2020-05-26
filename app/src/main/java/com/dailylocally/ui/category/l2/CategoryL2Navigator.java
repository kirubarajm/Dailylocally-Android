package com.dailylocally.ui.category.l2;

public interface CategoryL2Navigator {
    void handleError(Throwable throwable);
    void goBack();
    void viewCart();
    void createtabs(L2CategoryResponse response);
}
