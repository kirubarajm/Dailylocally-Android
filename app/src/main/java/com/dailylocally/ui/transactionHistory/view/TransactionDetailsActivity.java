package com.dailylocally.ui.transactionHistory.view;

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
import com.dailylocally.databinding.ActivityTransactionDetailsBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.calendarView.CalendarActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;


public class TransactionDetailsActivity extends BaseActivity<ActivityTransactionDetailsBinding, TransactionDetailsViewModel> implements
        TransactionDetailsNavigator,TransactionProductAdapter.TransactionHistoryInfoListener,TransactionBillDetailAdapter.TransactionHistoryInfoListener {


    public ActivityTransactionDetailsBinding ActivityTransactionDetailsBinding;
    @Inject
    public TransactionDetailsViewModel mTransactionDetailsViewModel;
    @Inject
    public TransactionProductAdapter mTransactionProductAdapter;
    @Inject
    public TransactionBillDetailAdapter mTransactionBillDetailAdapter;
    String orderid = "";

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

        return mTransactionDetailsViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void viewInCalendar() {
        Intent intent = CalendarActivity.newIntent(TransactionDetailsActivity.this);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void success(String date) {
        try {
            SimpleDateFormat dateDayFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
            SimpleDateFormat currentFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            Date date1 = currentFormat.parse(date);
            String datesdf = dateDayFormat.format(date1);

            mTransactionDetailsViewModel.transacDate.set(datesdf);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTransactionDetailsBinding = getViewDataBinding();
        mTransactionDetailsViewModel.setNavigator(this);
        mTransactionProductAdapter.setListener(this);
        mTransactionBillDetailAdapter.setListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            orderid = String.valueOf(bundle.getInt("orderid"));
            mTransactionDetailsViewModel.getTransactionHistoryViewList(orderid);
        }

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ActivityTransactionDetailsBinding.recyclerProducts.setLayoutManager(mLayoutManager);
        ActivityTransactionDetailsBinding.recyclerProducts.setAdapter(mTransactionProductAdapter);

        LinearLayoutManager mLayoutManagerBill
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mLayoutManagerBill.setOrientation(LinearLayoutManager.VERTICAL);
        ActivityTransactionDetailsBinding.recyclerBilDetails.setLayoutManager(mLayoutManagerBill);
        ActivityTransactionDetailsBinding.recyclerBilDetails.setAdapter(mTransactionBillDetailAdapter);


        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        mTransactionDetailsViewModel.getProductsItemsLiveData().observe(this,
                catregoryItemViewModel -> mTransactionDetailsViewModel.addProductsItemsToList(catregoryItemViewModel));

        mTransactionDetailsViewModel.getBilDetailsItemsLiveData().observe(this,
                catregoryItemViewModel -> mTransactionDetailsViewModel.addBilDetailsItemsToList(catregoryItemViewModel));
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
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

    @Override
    public void onViewCalendarClick(TransactionViewResponse.Result.Item cartdetail) {
        Intent intent = CalendarActivity.newIntent(TransactionDetailsActivity.this);
        intent.putExtra("date",cartdetail.getProductDate());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}