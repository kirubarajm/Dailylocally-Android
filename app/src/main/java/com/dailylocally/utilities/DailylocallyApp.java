/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.dailylocally.utilities;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.dailylocally.di.component.DaggerAppComponent;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.dailylocally.BuildConfig;
import com.dailylocally.R;
import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import zendesk.core.Zendesk;
import zendesk.support.Support;

public class DailylocallyApp extends Application implements HasActivityInjector {

    public static final String TAG = DailylocallyApp.class
            .getSimpleName();
    /*@Inject
    CalligraphyConfig mCalligraphyConfig;*/
    private static DailylocallyApp mInstance;
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;


    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {


            } else {

                startActivity(new Intent(InternetErrorFragment.newIntent(DailylocallyApp.getInstance())));

            }

        }
    };
    private RequestQueue mRequestQueue;

    public static synchronized DailylocallyApp getInstance() {
        return mInstance;
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }


    private void unregisterWifiReceiver() {
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



  /*  private class DaggerAppComponent extends AndroidInjector<MvvmApp>{


    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);


        // Init Support SDK
        Zendesk.INSTANCE.init(this, getResources().getString(R.string.zd_url),
                getResources().getString(R.string.zd_appid),
                getResources().getString(R.string.zd_oauth));
        Support.INSTANCE.init(Zendesk.INSTANCE);



        // ZopimChat.init(getString(R.string.zopim_account_id));


        PushUtils.registerWithZendesk();


        if (!BuildConfig.ENABLE_DEBUG) {
            FirebaseAnalytics.getInstance(this);
        }



        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);
        appPreferencesHelper.setRatingAppStatus(true);

        FacebookSdk.fullyInitialize();
        AppEventsLogger.activateApp(this);

       /* if (!BuildConfig.ENABLE_DEBUG)
            Crashlytics.getInstance();*/




//TODO: Disable debug crashes
       /* Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();
        // Initialize Fabric with the debug-disabled crashlytics.
        Fabric.with(this, crashlyticsKit);

        FacebookSdk.fullyInitialize();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);*/

        //AppLogger.init();

        /*AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }*/

        //CalligraphyConfig.initDefault(mCalligraphyConfig);
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public Map<String, String> setHeaders(String version) {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        //  headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("accept-version", version);
        headers.put("apptype", AppConstants.APP_TYPE_ANDROID);
        //  headers.put("Authorization","Bearer");
        AppPreferencesHelper preferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);
        headers.put("Authorization", "Bearer " + preferencesHelper.getApiToken());

        // headers.put("token",preferencesHelper.getApiToken());


        return headers;

    }


    public int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }


    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public boolean onCheckNetWork() {
        boolean isOnline = NetworkUtils.isNetworkConnected(this);
        if (!isOnline)
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        return isOnline;

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);
        appPreferencesHelper.setRatingAppStatus(false);

        if (appPreferencesHelper.getAddressId() == 0L) {
            appPreferencesHelper.setCurrentLat(0.0);
            appPreferencesHelper.setCurrentLng(0.0);
        }

    }

}
