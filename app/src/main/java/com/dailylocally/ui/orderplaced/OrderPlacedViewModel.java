package com.dailylocally.ui.orderplaced;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;

public class OrderPlacedViewModel extends BaseViewModel<OrderPlacedNavigator> {

    public OrderPlacedViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void gotoOrders(){
        getNavigator().gotoOrders();

    }
    public void goHome(){
        getNavigator().goHome();
    }

}
