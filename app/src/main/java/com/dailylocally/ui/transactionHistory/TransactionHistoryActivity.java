package com.dailylocally.ui.transactionHistory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityTransactionHistoryBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.analytics.Analytics;

import javax.inject.Inject;

public class TransactionHistoryActivity extends BaseActivity<ActivityTransactionHistoryBinding, TransactionHistoryViewModel>
        implements TransactionHistoryNavigator {

    @Inject
    TransactionHistoryViewModel mTransactionHistoryViewModel;
    Analytics analytics;
    String pageName = "Favorites";
    private ActivityTransactionHistoryBinding mActivityTransactionHistoryBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, TransactionHistoryActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public int getBindingVariable() {
        return BR.transactionHistoryViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_transaction_history;
    }

    @Override
    public TransactionHistoryViewModel getViewModel() {
        return mTransactionHistoryViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityTransactionHistoryBinding = getViewDataBinding();
        mTransactionHistoryViewModel.setNavigator(this);
        analytics = new Analytics(this, pageName);
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void canceled() {

    }
}
