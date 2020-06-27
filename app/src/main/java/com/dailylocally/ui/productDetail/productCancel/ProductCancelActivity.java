package com.dailylocally.ui.productDetail.productCancel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityProductCancelBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

import javax.inject.Inject;


public class ProductCancelActivity extends BaseActivity<ActivityProductCancelBinding, ProductCancelViewModel> implements
        ProductCancelNavigator {


    public ActivityProductCancelBinding mActivityProductCancelBinding;
    @Inject
    public ProductCancelViewModel mAddAddressViewModel;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_ADD_ADDRESS;

    public static Intent newIntent(Context context) {
        return new Intent(context, ProductCancelActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.productCancelViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_cancel;
    }

    @Override
    public ProductCancelViewModel getViewModel() {

        return mAddAddressViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityProductCancelBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);

        analytics = new Analytics(this, pageName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAddAddressViewModel.getProductCancelDetails("","");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void canceled() {

    }
}