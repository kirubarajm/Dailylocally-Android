package com.dailylocally.ui.category.l2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityCategoryl12Binding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class CategoryL2Activity extends BaseActivity<ActivityCategoryl12Binding, CategoryL2ViewModel> implements CategoryL2Navigator, HasSupportFragmentInjector {

    @Inject
    CategoryL2ViewModel mCategoryL2ViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    ActivityCategoryl12Binding mActivityCategoryl2Binding;
    String categoryid;
    String scl1id;
    ViewPager viewPager;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(CategoryL2Activity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
            }
        }
    };

    private TextView[] dots;

    public static Intent newIntent(Context context) {

        return new Intent(context, CategoryL2Activity.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryL2ViewModel.setNavigator(this);
        mActivityCategoryl2Binding = getViewDataBinding();
        subscribeLiveData();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            categoryid = intent.getExtras().getString("catid");
            scl1id = intent.getExtras().getString("scl1id");
            mCategoryL2ViewModel.fetchSubCategoryList(categoryid,scl1id);
        }


    }




    public void subscribeLiveData() {
        mCategoryL2ViewModel.getCategoryListLiveData().observe(this,
                categoryList -> mCategoryL2ViewModel.addDatatoList(categoryList));

    }
 public void refreshCart() {
        mCategoryL2ViewModel.totalCart();

    }

    @Override
    public int getBindingVariable() {
        return BR.categoryL2ViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_categoryl12;
    }

    @Override
    public CategoryL2ViewModel getViewModel() {
        return mCategoryL2ViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void viewCart() {
        Intent intent= MainActivity.newIntent(CategoryL2Activity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("page",AppConstants.SCREEN_CART_PAGE);
        startActivity(intent);
    }

    @Override
    public void createtabs(L2CategoryResponse response) {



        for (int k = 0; k < response.getResult().size(); k++) {

            mActivityCategoryl2Binding.categorytabs.addTab(mActivityCategoryl2Binding.categorytabs.newTab().setText(response.getResult().get(k).getName()));
        }




        PlansPagerAdapter adapter = new PlansPagerAdapter
                (getSupportFragmentManager(),   mActivityCategoryl2Binding.categorytabs.getTabCount(),response);
        mActivityCategoryl2Binding.frameLayout.setAdapter(adapter);
//        mActivityCategoryl2Binding.frameLayout.setOffscreenPageLimit(1);
        mActivityCategoryl2Binding.frameLayout.setCurrentItem(0);
        mActivityCategoryl2Binding.frameLayout.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(  mActivityCategoryl2Binding.categorytabs));




        /*mActivityCategoryl2Binding.frameLayout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mActivityCategoryl2Binding.frameLayout.setCurrentItem(position);
            }

            @Override
            public void onPageSelected(int position) {
                mActivityCategoryl2Binding.frameLayout.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/



/*
 PlansPagerAdapter adapter = new PlansPagerAdapter
                (getSupportFragmentManager(),   mActivityCategoryl2Binding.categorytabs.getTabCount(),response);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(  mActivityCategoryl2Binding.categorytabs));


*/



        if (mActivityCategoryl2Binding.categorytabs.getTabCount() == 3) {
            mActivityCategoryl2Binding.categorytabs.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mActivityCategoryl2Binding.categorytabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        }



    }


    @Override
    public void onResume() {
        super.onResume();
        registerWifiReceiver();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        DailylocallyApp.getInstance().registerReceiver(mWifiReceiver, filter);
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
        DailylocallyApp.getInstance().unregisterReceiver(mWifiReceiver);
    }


    @Override
    public void canceled() {

    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}

