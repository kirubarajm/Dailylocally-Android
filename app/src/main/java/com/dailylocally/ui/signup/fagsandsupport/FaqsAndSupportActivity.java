package com.dailylocally.ui.signup.fagsandsupport;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;


import androidx.annotation.Nullable;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityFaqsSupportBinding;

import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.signup.faqs.FaqActivity;
import com.dailylocally.utilities.AppConstants;

import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class FaqsAndSupportActivity extends BaseActivity<ActivityFaqsSupportBinding, FaqsAndSupportViewModel> implements FaqsAndSupportNavigator {

    @Inject
    FaqsAndSupportViewModel mFaqsAndSupportViewModel;
    ActivityFaqsSupportBinding mActivityFaqsSupportBinding;


    Analytics analytics;
    String pageName= AppConstants.SCREEN_FAQS_AND_SUPPORT;


    public static Intent newIntent(Context context) {

        return new Intent(context, FaqsAndSupportActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void feedBackClick() {
        new Analytics().sendClickData(AppConstants.SCREEN_FAQS_AND_SUPPORT, AppConstants.CLICK_FAQ);

        Intent intent = FaqActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void supportClick() {
        new Analytics().sendClickData(AppConstants.SCREEN_FAQS_AND_SUPPORT, AppConstants.CLICK_SUPPORT);

        /*Intent intent = SupportActivity.newIntent(this);
        startActivity(intent);*/

       /* ZopimChat.init(getString(R.string.zopim_account_id));
        final VisitorInfo.Builder build = new VisitorInfo.Builder();
        ZopimChat.setVisitorInfo(build.build());

// build pre chat form config
        PreChatForm preChatForm = new PreChatForm.Builder()
                .name(PreChatForm.Field.REQUIRED)
                .email(PreChatForm.Field.NOT_REQUIRED)
                .phoneNumber(PreChatForm.Field.REQUIRED)
                .department(PreChatForm.Field.REQUIRED)
                .message(PreChatForm.Field.NOT_REQUIRED)
                .build();
// build session config
        ZopimChat.SessionConfig config = new ZopimChat.SessionConfig()
                .preChatForm(preChatForm)
                .department("EAT")
                .tags("New user" );
// start chat activity with config
        ZopimChatActivity.startActivity(this, config);*/

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(AppConstants.SCREEN_FAQS_AND_SUPPORT, AppConstants.CLICK_BACK_BUTTON);

        super.onBackPressed();
    }

    @Override
    public void callCusstomerCare() {


            new Analytics().sendClickData(AppConstants.SCREEN_FAQS_AND_SUPPORT, AppConstants.CLICK_CALL_SUPPORT);


       // String number = AppConstants.SUPPORT_NUMBER;
        String number = mFaqsAndSupportViewModel.support.get();

        assert number != null;
        if (!number.equals("0")) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        }

    }

    @Override
    public int getBindingVariable() {
        return BR.faqsAndSupportViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_faqs_support;
    }

    @Override
    public FaqsAndSupportViewModel getViewModel() {
        return mFaqsAndSupportViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFaqsAndSupportViewModel.setNavigator(this);
        mActivityFaqsSupportBinding = getViewDataBinding();


        analytics=new Analytics(this, pageName);

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
