package com.dailylocally.ui.signup.privacy;

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
import com.dailylocally.ui.registration.RegistrationActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.MvvmApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class PrivacyActivity extends BaseActivity<ActivityPrivacyBinding, PrivacyViewModel> implements PrivacyNavigator {

    @Inject
    PrivacyViewModel mPrivacyViewModel;
    ActivityPrivacyBinding mActivityPrivacyBinding;


    Analytics analytics;
    String pageName= AppConstants.SCREEN_PRIVACY_POLICY;


    public static Intent newIntent(Context context) {
        return new Intent(context, PrivacyActivity.class);
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
    public PrivacyViewModel getViewModel() {
        return mPrivacyViewModel;
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void openRegActivity() {
        Intent intent = RegistrationActivity.newIntent(PrivacyActivity.this);
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
        mPrivacyViewModel.setNavigator(this);

        mActivityPrivacyBinding.webview.getSettings().setJavaScriptEnabled(true);




        analytics=new Analytics(this,pageName);
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(AppConstants.SCREEN_PRIVACY_POLICY, AppConstants.CLICK_BACK_BUTTON);
       super.onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
        mActivityPrivacyBinding.webview.loadUrl("http://www.dailylocally.co.in/pp.html");
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
        ConnectivityManager manager = (ConnectivityManager) MvvmApp.getInstance(). getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) MvvmApp.getInstance() .getSystemService(Context.CONNECTIVITY_SERVICE);

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
                Intent inIntent= InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                InternetErrorFragment fragment = new InternetErrorFragment();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                internetCheck = true;*/
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
