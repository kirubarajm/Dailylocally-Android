package com.dailylocally.ui.onboarding;


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
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dailylocally.BR;
import com.dailylocally.MainActivity;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityOnboardingBinding;
import com.dailylocally.ui.base.BaseActivity;

import com.dailylocally.ui.signup.SignUpActivity;
import com.dailylocally.ui.update.UpdateActivity;
import com.dailylocally.utilities.AppConstants;

import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;


public class OnBoardingActivity extends BaseActivity<ActivityOnboardingBinding, OnBoardingActivityViewModel>
        implements OnBoardingActivityNavigator {

    @Inject
    OnBoardingActivityViewModel mOnBoardingActivityViewModel;
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
            }
        }
    };
    private ActivityOnboardingBinding mActivityOnboardingBinding;
    private PrefManager prefManager;
    private TextView[] dots;
    private int[] layouts;
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            if (position == layouts.length - 1) {
                //mActivityOnboardingBinding.btnNext.setVisibility(View.GONE);
                //mActivityOnboardingBinding.btnSkip.setVisibility(View.GONE);
                //mActivityOnboardingBinding.txtGetStarted.setVisibility(View.VISIBLE);
                mActivityOnboardingBinding.layoutDots.setVisibility(View.INVISIBLE);
                mActivityOnboardingBinding.btnGetStarted.setVisibility(View.VISIBLE);
            } else {
                //mActivityOnboardingBinding.btnNext.setVisibility(View.GONE);
                //mActivityOnboardingBinding.btnSkip.setVisibility(View.GONE);
                //mActivityOnboardingBinding.txtGetStarted.setVisibility(View.GONE);
                mActivityOnboardingBinding.layoutDots.setVisibility(View.VISIBLE);
                mActivityOnboardingBinding.btnGetStarted.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private MyViewPagerAdapter myViewPagerAdapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, OnBoardingActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }
    @Override
    public void update(boolean updateStatus, boolean forceUpdateStatus) {

        //  mSplashActivityViewModel.checkIsUserLoggedInOrNot();
        if (forceUpdateStatus) {
            Intent intent = UpdateActivity.newIntent(OnBoardingActivity.this);
            intent.putExtra("forceUpdate", forceUpdateStatus);
            startActivity(intent);
            finish();
        } else {
            mOnBoardingActivityViewModel.checkIsUserLoggedInOrNot();
        }

    }
    @Override
    public void checkForUserLoginMode(boolean trueOrFalse) {
        if (trueOrFalse) {
            Intent intent = MainActivity.newIntent(OnBoardingActivity.this);
            startActivity(intent);
            finish();
        } else {
            Intent intent = SignUpActivity.newIntent(OnBoardingActivity.this);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.onBoardingViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_onboarding;
    }

    @Override
    public OnBoardingActivityViewModel getViewModel() {
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
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3};

        addBottomDots(0);
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        mActivityOnboardingBinding.viewPager.setAdapter(myViewPagerAdapter);
        mActivityOnboardingBinding.viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


        mActivityOnboardingBinding.btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Analytics().sendClickData(AppConstants.SCREEN_ONBOARDING, AppConstants.CLICK_GET_STARTED);

                launchHomeScreen();
            }
        });

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        mActivityOnboardingBinding.layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setPadding(0, 0, 20, 0);
            dots[i].setTextColor(colorsInactive[currentPage]);
            mActivityOnboardingBinding.layoutDots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
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
