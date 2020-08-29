package com.dailylocally.ui.category.viewall;

import com.dailylocally.ui.category.l1.L1CategoryResponse;

public interface CatProductNavigator {
    void handleError(Throwable throwable);
    void goBack();
    void viewCart();
    void createtabs(L1CategoryResponse response);

    void dataLoaded();
}
