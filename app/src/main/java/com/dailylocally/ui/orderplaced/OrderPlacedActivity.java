package com.dailylocally.ui.orderplaced;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.dailylocally.BR;
import com.dailylocally.R;

import com.dailylocally.databinding.OrderPlacedBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

import javax.inject.Inject;

public class OrderPlacedActivity extends BaseActivity<OrderPlacedBinding, OrderPlacedViewModel> implements OrderPlacedNavigator {

    @Inject
    OrderPlacedViewModel mOrderPlacedViewModel;
    OrderPlacedBinding mOrderPlacedBinding;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_ORDER_PLACED;

    public static Intent newIntent(Context context) {
        return new Intent(context, OrderPlacedActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void gotoOrders() {
       /* Intent intent = MainActivity.newIntent(OrderPlacedActivity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("page", SCREEN_MY_ORDERS);
        intent.putExtra("screenName", AppConstants.SCREEN_ORDER_PLACED);
        startActivity(intent);
        finish();*/

      /*  Intent intent = OrderHistoryActivity.newIntent(OrderPlacedActivity.this);
        intent.putExtra("page", AppConstants.PAGE_ORDER_PLACED);
        startActivity(intent);*/

    }

    @Override
    public void goHome() {
        Intent intent = MainActivity.newIntent(OrderPlacedActivity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public int getBindingVariable() {
        return BR.orderPlacedViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.order_placed;
    }

    @Override
    public OrderPlacedViewModel getViewModel() {
        return mOrderPlacedViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderPlacedViewModel.setNavigator(this);
        mOrderPlacedBinding = getViewDataBinding();

        analytics = new Analytics(this, pageName);


    }

    @Override
    public void canceled() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(() -> {
            mOrderPlacedBinding.check.check();
        }, 800);
    }
}
