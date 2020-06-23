package com.dailylocally.ui.transactionHistory.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityProductDetailsBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

import javax.inject.Inject;


public class TransactionDetailsActivity extends BaseActivity<ActivityProductDetailsBinding, TransactionDetailsViewModel> implements
        TransactionDetailsNavigator {


    public ActivityProductDetailsBinding mActivityProductDetailsBinding;
    @Inject
    public TransactionDetailsViewModel mAddAddressViewModel;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_ADD_ADDRESS;

    public static Intent newIntent(Context context) {
        return new Intent(context, TransactionDetailsActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.transactionDetailsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_transaction_details;
    }

    @Override
    public TransactionDetailsViewModel getViewModel() {

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
        mActivityProductDetailsBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);

        analytics = new Analytics(this, pageName);
    }

    @Override
    protected void onResume() {
        super.onResume();
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