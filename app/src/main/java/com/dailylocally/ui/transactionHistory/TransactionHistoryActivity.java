package com.dailylocally.ui.transactionHistory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityTransactionHistoryBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.coupons.CouponsActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.splash.SplashActivity;
import com.dailylocally.ui.transactionHistory.view.TransactionDetailsActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class TransactionHistoryActivity extends BaseActivity<ActivityTransactionHistoryBinding, TransactionHistoryViewModel>
        implements TransactionHistoryNavigator, TransactionHistoryAdapter.TransactionHistoryInfoListener {

    @Inject
    TransactionHistoryViewModel mTransactionHistoryViewModel;
    @Inject
    TransactionHistoryAdapter mTransactionHistoryAdapter;
    Analytics analytics;

    private ActivityTransactionHistoryBinding mActivityTransactionHistoryBinding;
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(getApplicationContext());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

        }
    };
    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private void unregisterWifiReceiver() {
        if (mWifiReceiver != null)
            unregisterReceiver(mWifiReceiver);
    }

    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                && networkInfo.isConnected()) {
            return true;
        } else return networkInfo != null
                && networkInfo.isConnected();
    }
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
    public void goHome() {
        Intent intent = MainActivity.newIntent(TransactionHistoryActivity.this, AppConstants.NOTIFY_HOME_FRAG,AppConstants.NOTIFY_TRANS_LIST_ACTV);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
        unregisterWifiReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
