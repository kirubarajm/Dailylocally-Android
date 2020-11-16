package com.dailylocally.ui.splash;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivitySplashBinding;
import com.dailylocally.ui.address.addAddress.AddressNewActivity;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.onboarding.OnBoardingActivity;
import com.dailylocally.ui.onboarding.PrefManager;
import com.dailylocally.ui.signup.SignUpActivity;
import com.dailylocally.ui.signup.registration.RegistrationActivity;
import com.dailylocally.ui.update.UpdateActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.Objects;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel>
        implements SplashNavigator {

    @Inject
    SplashViewModel mSplashViewModel;
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent = InternetErrorFragment.newIntent(DailylocallyApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    };
    private ActivitySplashBinding mActivitySplashBinding;
    private PrefManager prefManager;

    public static Intent newIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void checkForUserLogin(boolean status) {
        if (status) {
            Intent intent = MainActivity.newIntent(SplashActivity.this, AppConstants.NOTIFY_HOME_FRAG, AppConstants.NOTIFY_SPLASH_ACTV);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            Intent intent = SignUpActivity.newIntent(SplashActivity.this,AppConstants.SCREEN_NAME_SPLASH,AppConstants.SCREEN_NAME_SIGN_UP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void update(boolean updateStatus, boolean forceUpdateStatus, String addressCreated) {
        if (forceUpdateStatus) {
            Intent intent = UpdateActivity.newIntent(SplashActivity.this,AppConstants.SCREEN_NAME_SPLASH,AppConstants.SCREEN_NAME_UPDATE);
            intent.putExtra("forceUpdate", forceUpdateStatus);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            mSplashViewModel.checkIsUserLoggedInOrNot(addressCreated);
        }
    }

    @Override
    public void userAlreadyRegistered(boolean status) {
        if (!status) {
            Intent intent = RegistrationActivity.newIntent(SplashActivity.this,AppConstants.SCREEN_NAME_SPLASH,AppConstants.SCREEN_NAME_REGISTRATION);
            intent.putExtra("newuser", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void userAddressActivity() {

        //Intent inIntent = AddressNewActivity.newIntent(SplashActivity.this);
        Intent intents = new Intent();
        Intent inIntent = AddressNewActivity.newIntent(this,intents.getExtras().getString(AppConstants.FROM),AppConstants.SCREEN_EDIT_ADDRESS);
        inIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        inIntent.putExtra("newuser", true);
        startActivity(inIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public int getBindingVariable() {
        return BR.splashViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashViewModel getViewModel() {
        return mSplashViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySplashBinding = getViewDataBinding();
        mSplashViewModel.setNavigator(this);

        prefManager = new PrefManager(this);

        Intent intent = getIntent();
        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_SPLASH);
        new Analytics().eventAppOpens(this);
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            mSplashViewModel.version.set("v" + version);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

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


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!prefManager.isFirstTimeLaunch()) {
                    Intent intent = OnBoardingActivity.newIntent(SplashActivity.this);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {

                    mSplashViewModel.checkUpdate();

                }
            }
        }, 1000);

    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }


    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) DailylocallyApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) DailylocallyApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

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

    private void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }

    @Override
    public void canceled() {

    }


}
