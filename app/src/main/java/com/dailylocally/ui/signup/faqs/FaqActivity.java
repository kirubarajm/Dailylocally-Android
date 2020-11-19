package com.dailylocally.ui.signup.faqs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;


import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;


import com.dailylocally.databinding.ActivityFaqsBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.signup.fagsandsupport.FaqsAndSupportActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.List;

import javax.inject.Inject;

public class FaqActivity extends BaseActivity<ActivityFaqsBinding, FaqFragmentViewModel> implements FaqFragmentNavigator {

    public static final String TAG = FaqActivity.class.getSimpleName();
    @Inject
    FaqFragmentViewModel mFaqViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    FaqsAdapter mFaqsAdapter;
    private ActivityFaqsBinding mActivityFaqsBinding;

    public static Intent newIntent(Context context,String fromPage,String ToPage) {
        Intent intent = new Intent(context, FaqActivity.class);
        intent.putExtra(AppConstants.FROM, fromPage);
        intent.putExtra(AppConstants.PAGE, ToPage);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.faqsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_faqs;
    }

    @Override
    public FaqFragmentViewModel getViewModel() {
        return mFaqViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void backClick() {
       onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFaqViewModel.setNavigator(this);
        mActivityFaqsBinding = getViewDataBinding();



        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityFaqsBinding.recyclerFaqs.setLayoutManager(mLayoutManager);
        mActivityFaqsBinding.recyclerFaqs.setAdapter(mFaqsAdapter);
        subscribeToLiveData();
    }


    private void subscribeToLiveData() {
        mFaqViewModel.getFaqs().observe(this,
                FaqFragmentViewModel -> mFaqViewModel.addFaqsItemsToList(FaqFragmentViewModel));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
        mFaqViewModel.fetchRepos();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }


    private  boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) DailylocallyApp.getInstance(). getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) DailylocallyApp.getInstance() .getSystemService(Context.CONNECTIVITY_SERVICE);

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

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent= InternetErrorFragment.newIntent(DailylocallyApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
            }
        }
    };
    private  void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }


    @Override
    public void canceled() {

    }
}
