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

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityCommunityOnboardingBinding;
import com.dailylocally.ui.address.addAddress.AddressNewActivity;
import com.dailylocally.ui.address.type.CommunitySearchActivity;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.onboarding.PrefManager;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;


public class CommunityOnBoardingActivity extends BaseActivity<ActivityCommunityOnboardingBinding, CommunityOnBoardingActivityViewModel>
        implements CommunityOnBoardingActivityNavigator {

    @Inject
    CommunityOnBoardingActivityViewModel mOnBoardingActivityViewModel;

    Boolean next = false;


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
    public void goBack() {
        super.onBackPressed();
    }

    @Override
    public void skip() {

        action();

    }

    @Override
    public void action() {

        mOnBoardingActivityViewModel.getDataManager().saveCommunityOnboardSeen(true);


        if (next) {
           /* Intent inIntent = AddressNewActivity.newIntent(CommunityOnBoardingActivity.this);
            inIntent.putExtra("newuser", false);
            startActivity(inIntent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
           finish();

        } else {
            Intent inIntent = CommunitySearchActivity.newIntent(CommunityOnBoardingActivity.this);
            inIntent.putExtra("newuser", false);
            inIntent.putExtra("lat", mOnBoardingActivityViewModel.getDataManager().getCurrentLat());
            inIntent.putExtra("lng", mOnBoardingActivityViewModel.getDataManager().getCurrentLng());
            startActivityForResult(inIntent, AppConstants.SELECT_COMMUNITY_REQUEST_CODE);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }

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

        if (getIntent().getExtras() != null)
            next = getIntent().getExtras().getBoolean("next", false);


        if (next) {
            mOnBoardingActivityViewModel.btnText.set("Next");
        } else {
            mOnBoardingActivityViewModel.btnText.set("Select community");
        }

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
                R.layout.community_slide_2,
                R.layout.community_slide_3};


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
                        break;
                    case 1:
                        mOnBoardingActivityViewModel.lastScreen.set(false);
                        mActivityOnboardingBinding.indi1.setImageResource(R.drawable.ic_round_indicator);
                        mActivityOnboardingBinding.indi2.setImageResource(R.drawable.ic_round_indicator_selected);
                        mActivityOnboardingBinding.indi3.setImageResource(R.drawable.ic_round_indicator);
                        // mActivityOnboardingBinding.indi2.setColorFilter(R.color.medium_black);
                        break;
                    case 2:

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
