package com.dailylocally.ui.promotion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityPrivacyBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.signup.registration.RegistrationActivity;
import com.dailylocally.utilities.AppConstants;

import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class AdActivity extends BaseActivity<ActivityPrivacyBinding, AdViewModel> implements AdNavigator {

    @Inject
    AdViewModel mAdViewModel;
    ActivityPrivacyBinding mActivityPrivacyBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, AdActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.privacyViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_privacy;
    }

    @Override
    public AdViewModel getViewModel() {
        return mAdViewModel;
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void openRegActivity() {
        Intent intent = RegistrationActivity.newIntent(AdActivity.this);
        intent.putExtra("edit","0");
        startActivity(intent);
        finish();
    }

    @Override
    public void goBack() {
        onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityPrivacyBinding = getViewDataBinding();
        mAdViewModel.setNavigator(this);

        mActivityPrivacyBinding.webview.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
        mActivityPrivacyBinding.webview.loadUrl("http://www.eatalltime.co.in/pp.html");
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
