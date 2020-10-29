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


import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dailylocally.BR;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityOnboardingBinding;
import com.dailylocally.ui.base.BaseActivity;

import com.dailylocally.ui.signup.SignUpActivity;
import com.dailylocally.ui.update.UpdateActivity;
import com.dailylocally.utilities.AppConstants;

import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.fonts.quicksand.ButtonTextView;
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
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    };
    private ActivityOnboardingBinding mActivityOnboardingBinding;
    private PrefManager prefManager;
    private TextView[] dots;
    private int[] layouts;

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
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            mOnBoardingActivityViewModel.checkIsUserLoggedInOrNot();
        }

    }
    @Override
    public void checkForUserLoginMode(boolean trueOrFalse) {
        if (trueOrFalse) {
            Intent intent = MainActivity.newIntent(OnBoardingActivity.this,AppConstants.NOTIFY_HOME_FRAG,AppConstants.NOTIFY_ONBOARDING_ACTV);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            Intent intent = SignUpActivity.newIntent(OnBoardingActivity.this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                R.layout.welcome_slide3,
                R.layout.welcome_slide4};


        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        mActivityOnboardingBinding.viewPager.setAdapter(myViewPagerAdapter);




        mActivityOnboardingBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                switch (position) {
                    case 0:
                        mOnBoardingActivityViewModel.lastScreen.set(false);
                        mActivityOnboardingBinding.indi1.setImageResource(R.drawable.ic_round_indicator_selected);
                        mActivityOnboardingBinding.indi2.setImageResource(R.drawable.ic_round_indicator);
                        mActivityOnboardingBinding.indi3.setImageResource(R.drawable.ic_round_indicator);
                        mActivityOnboardingBinding.indi4.setImageResource(R.drawable.ic_round_indicator);
                        break;
                    case 1:
                        mOnBoardingActivityViewModel.lastScreen.set(false);
                        mActivityOnboardingBinding.indi1.setImageResource(R.drawable.ic_round_indicator);
                        mActivityOnboardingBinding.indi2.setImageResource(R.drawable.ic_round_indicator_selected);
                        mActivityOnboardingBinding.indi3.setImageResource(R.drawable.ic_round_indicator);
                        mActivityOnboardingBinding.indi4.setImageResource(R.drawable.ic_round_indicator);
                        // mActivityOnboardingBinding.indi2.setColorFilter(R.color.medium_black);
                        break;
                        case 2:
                        mOnBoardingActivityViewModel.lastScreen.set(false);
                        mActivityOnboardingBinding.indi1.setImageResource(R.drawable.ic_round_indicator);
                        mActivityOnboardingBinding.indi2.setImageResource(R.drawable.ic_round_indicator);
                        mActivityOnboardingBinding.indi3.setImageResource(R.drawable.ic_round_indicator_selected);
                        mActivityOnboardingBinding.indi4.setImageResource(R.drawable.ic_round_indicator);
                        // mActivityOnboardingBinding.indi2.setColorFilter(R.color.medium_black);
                        break;
                    case 3:

                        mOnBoardingActivityViewModel.lastScreen.set(true);
                       /* mActivityOnboardingBinding.indi1.setImageResource(R.drawable.ic_round_indicator);
                        mActivityOnboardingBinding.indi2.setImageResource(R.drawable.ic_round_indicator);
                        mActivityOnboardingBinding.indi3.setImageResource(R.drawable.ic_round_indicator_selected);*/
                        //  mActivityOnboardingBinding.indi3.setColorFilter(R.color.medium_black);
                        break;

                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



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
