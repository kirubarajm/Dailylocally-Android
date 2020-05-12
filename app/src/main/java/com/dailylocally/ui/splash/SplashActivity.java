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

import com.android.databinding.library.baseAdapters.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivitySplashBinding;
import com.dailylocally.ui.account.feedbackandsupport.support.replies.RepliesActivity;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.home.MainActivity;
import com.dailylocally.ui.onboarding.OnBoardingActivity;
import com.dailylocally.ui.onboarding.PrefManager;
import com.dailylocally.ui.signup.SignUpActivity;
import com.dailylocally.ui.signup.registration.RegistrationActivity;
import com.dailylocally.ui.update.UpdateActivity;
import com.dailylocally.utilities.MvvmApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashActivityViewModel>
        implements SplashActivityNavigator {

    @Inject
    SplashActivityViewModel mSplashActivityViewModel;

    private ActivitySplashBinding mActivitySplashBinding;
    private PrefManager prefManager;

    Analytics analytics;
    String pageName="Splash screen";

    public static Intent newIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void checkForUserLoginMode(boolean trueOrFlase) {
        if (trueOrFlase) {
            Intent intent = MainActivity.newIntent(SplashActivity.this);
            startActivity(intent);
            finish();
        } else {
           /* SharedPreferences settings = getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
            settings.edit().clear().apply();*/
            Intent intent = SignUpActivity.newIntent(SplashActivity.this);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void update(boolean updateStatus, boolean forceUpdateStatus) {

      //  mSplashActivityViewModel.checkIsUserLoggedInOrNot();
        if (forceUpdateStatus) {
            Intent intent = UpdateActivity.newIntent(SplashActivity.this);
            intent.putExtra("forceUpdate", forceUpdateStatus);
            startActivity(intent);
            finish();
        } else {
            mSplashActivityViewModel.checkIsUserLoggedInOrNot();
        }

    }

    @Override
    public void checkForUserGenderStatus(boolean trueOrFalse) {
        Intent intent = RegistrationActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        finish();
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
    public SplashActivityViewModel getViewModel() {
        return mSplashActivityViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySplashBinding = getViewDataBinding();
        mSplashActivityViewModel.setNavigator(this);

        prefManager = new PrefManager(this);


        /*final InstallReferrerClient referrerClient = InstallReferrerClient.newBuilder(SplashActivity.this).build();
        referrerClient.startConnection(new InstallReferrerStateListener() {

            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:

                        try {
                            ReferrerDetails response = referrerClient.getInstallReferrer();
                            String installReferrer = response.getInstallReferrer();

                          *//*  long referrerClickTime = response.getReferrerClickTimestampSeconds();
                            long appInstallTime = response.getInstallBeginTimestampSeconds();
                            boolean instantExperienceLaunched = response.getGooglePlayInstantParam();
                            Log.e("Referrer",installReferrer);
                            Log.e("Referrer", String.valueOf(response.getGooglePlayInstantParam()));*//*

                            HashMap<String, String> values = new HashMap<>();

                                try {
                                    if (installReferrer != null) {
                                        String referrers[] = installReferrer.split("&");

                                        for (String referrerValue : referrers) {
                                            String keyValue[] = referrerValue.split("=");
                                            values.put(URLDecoder.decode(keyValue[0], "UTF-8"), URLDecoder.decode(keyValue[1], "UTF-8"));
                                        }

                                        Log.e("TAG", "UTM medium:" + values.get("utm_medium"));
                                        Log.e("TAG", "UTM term:" + values.get("utm_term"));


                                        Toast.makeText(SplashActivity.this, values.get("utm_term"), Toast.LENGTH_SHORT).show();


                                    }
                                } catch (Exception e) {

                                }
                            // handle referrer string

                        } catch (RemoteException  e) {
                            e.printStackTrace();
                        }
                        break;

                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app
                        Log.e("Referrer","Not supported");
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection could not be established
                        Log.e("Referrer","unavailable");
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.e("Referrer","disconnected");
            }
        });*/
        mSplashActivityViewModel.clearLatLng();


        analytics=new Analytics( this,pageName);
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            mSplashActivityViewModel.version.set("v" + version);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



      /*  mSplashActivityViewModel.getDataManager().saveApiToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6IjkwOTQ5MzkzNDciLCJpYXQiOjE1NjYyMTEyNDZ9.jOg5m2fkw6U6dGyhKpNWn594N34deElh5kqKemXe_x8");


        mSplashActivityViewModel. getDataManager().updateEmailStatus(true);



            mSplashActivityViewModel.getDataManager().saveRegionId(0);



        mSplashActivityViewModel.getDataManager().updateUserInformation(127, null, null, null, null);

        Intent intent = MainActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        finish();*/



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
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (null != intent.getExtras().getString("pageid") && intent.getExtras().getString("pageid").equals("9")) {

                Intent repliesIntent = RepliesActivity.newIntent(SplashActivity.this);
                startActivity(repliesIntent);
                finish();

            } else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!prefManager.isFirstTimeLaunch()) {
                            Intent intent = OnBoardingActivity.newIntent(SplashActivity.this);
                            startActivity(intent);
                            finish();
                        } else {

                            mSplashActivityViewModel.checkUpdate();

                        }
                    }
                }, 1000);
            }
        }else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!prefManager.isFirstTimeLaunch()) {
                        Intent intent = OnBoardingActivity.newIntent(SplashActivity.this);
                        startActivity(intent);
                        finish();
                    } else {

                        mSplashActivityViewModel.checkUpdate();

                    }
                }
            }, 1000);


        }
    }
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
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

    private  void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }


    @Override
    public void canceled() {

    }
}
