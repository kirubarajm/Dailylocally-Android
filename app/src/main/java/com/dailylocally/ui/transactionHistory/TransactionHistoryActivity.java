package com.dailylocally.ui.transactionHistory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityTransactionHistoryBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.coupons.CouponsActivity;
import com.dailylocally.ui.transactionHistory.view.TransactionDetailsActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

import javax.inject.Inject;

public class TransactionHistoryActivity extends BaseActivity<ActivityTransactionHistoryBinding, TransactionHistoryViewModel>
        implements TransactionHistoryNavigator, TransactionHistoryAdapter.TransactionHistoryInfoListener {

    @Inject
    TransactionHistoryViewModel mTransactionHistoryViewModel;
    @Inject
    TransactionHistoryAdapter mTransactionHistoryAdapter;
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
        mTransactionHistoryAdapter.setListener(this);
        analytics = new Analytics(this, pageName);


        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityTransactionHistoryBinding.recyclerTransactionHistory.setLayoutManager(mLayoutManager);
        mActivityTransactionHistoryBinding.recyclerTransactionHistory.setAdapter(mTransactionHistoryAdapter);

        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        mTransactionHistoryViewModel.getTransactionHistoryItemsLiveData().observe(this,
                catregoryItemViewModel -> mTransactionHistoryViewModel.addTransactionHistoryItemsToList(catregoryItemViewModel));
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
       mTransactionHistoryViewModel.getTransactionHistoryList();
    }

    @Override
    public void canceled() {

    }

    @Override
    public void viewClick(TransactionHistoryResponse.Result cartdetail) {
        Intent intent = TransactionDetailsActivity.newIntent(this);
        intent.putExtra("orderid",cartdetail.getOrderid());
        startActivity(intent);
    }
}
