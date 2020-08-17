package com.dailylocally.ui.communityOnboarding;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityCommunityOnboardingBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.onboarding.PrefManager;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.fonts.quicksand.ButtonTextView;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;


public class CommunityOnBoardingActivity extends BaseActivity<ActivityCommunityOnboardingBinding, CommunityOnBoardingActivityViewModel>
        implements CommunityOnBoardingActivityNavigator {

    @Inject
    CommunityOnBoardingActivityViewModel mOnBoardingActivityViewModel;
    Analytics analytics;
    String pageName = "Onboarding";
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(DailylocallyApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    };
    private ActivityCommunityOnboardingBinding mActivityOnboardingBinding;
    private PrefManager prefManager;
    private TextView[] dots;
    private int[] layouts;

    private MyViewPagerAdapter myViewPagerAdapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, CommunityOnBoardingActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public int getBindingVariable() {
        return BR.communityOnBoardingViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_community_onboarding;
    }

    @Override
    public CommunityOnBoardingActivityViewModel getViewModel() {
        return mOnBoardingActivityViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOnboardingBinding = getViewDataBinding();
        mOnBoardingActivityViewModel.setNavigator(this);

        prefManager = new PrefManager(this);

        analytics = new Analytics(this, pageName);
/*
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }*/
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        layouts = new int[]{
                R.layout.community_slide_1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4};

        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        mActivityOnboardingBinding.viewPager.setAdapter(myViewPagerAdapter);
    }

    private int getItem(int i) {
        return mActivityOnboardingBinding.viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(true);

        mOnBoardingActivityViewModel.checkUpdate();
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
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

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);


            if (position==3) {
                ButtonTextView textView = view.findViewById(R.id.btn_get_started);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        launchHomeScreen();

                    }
                });
            }

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


}
