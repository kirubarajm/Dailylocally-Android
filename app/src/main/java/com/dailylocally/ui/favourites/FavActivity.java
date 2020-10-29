package com.dailylocally.ui.favourites;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityFavDetailsBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.category.l2.products.filter.FilterListener;
import com.dailylocally.ui.category.l2.products.sort.SortFragment;
import com.dailylocally.ui.category.l2.slider.L2SliderAdapter;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class FavActivity extends BaseActivity<ActivityFavDetailsBinding, FavViewModel> implements FavNavigator, HasSupportFragmentInjector, FilterListener {

    @Inject
    FavViewModel mFavViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    L2SliderAdapter adapter;


    ActivityFavDetailsBinding mActivityFavDetailsBinding;
    String cid;
    String scl1id;
    String vpid;
    ViewPager viewPager;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(FavActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    };

    private TextView[] dots;

    public static Intent newIntent(Context context) {

        return new Intent(context, FavActivity.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavViewModel.setNavigator(this);
        mActivityFavDetailsBinding = getViewDataBinding();
        subscribeLiveData();
        startLoader();

    }


    public void subscribeLiveData() {
        mFavViewModel.getCategoryListLiveData().observe(this,
                categoryList -> mFavViewModel.addDatatoList(categoryList));


    }

    public void refreshCart() {
        mFavViewModel.totalCart();

    }

    @Override
    public int getBindingVariable() {
        return BR.favViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fav_details;
    }

    @Override
    public FavViewModel getViewModel() {
        return mFavViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }


    public void openSort(String catid) {
        Bundle bundle = new Bundle();
        bundle.putString("catid", catid);

        SortFragment sortFragment = new SortFragment();
        sortFragment.setArguments(bundle);
        sortFragment.show(getSupportFragmentManager(), sortFragment.getTag());
    }

    @Override
    public void viewCart() {
        Intent intent = MainActivity.newIntent(FavActivity.this, AppConstants.NOTIFY_CART_FRAG, AppConstants.NOTIFY_FAVORITES_ACTV);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void createtabs(FavResponse response) {


        if (response.getResult() == null) {
            mActivityFavDetailsBinding.categorytabs.setVisibility(View.GONE);
            //  mActivityFavDetailsBinding.htabToolbar.setVisibility(View.GONE);
        } else if (response.getResult() != null && response.getResult().size() == 0) {
            mActivityFavDetailsBinding.categorytabs.setVisibility(View.GONE);
            //     mActivityFavDetailsBinding.htabToolbar.setVisibility(View.GONE);
        }


        mActivityFavDetailsBinding.categorytabs.addTab(mActivityFavDetailsBinding.categorytabs.newTab().setText("All"));

        if (response.getResult() != null) {
            for (int k = 0; k < response.getResult().size(); k++) {

                mActivityFavDetailsBinding.categorytabs.addTab(mActivityFavDetailsBinding.categorytabs.newTab().setText(response.getResult().get(k).getName()));
            }
        }

        FavTabPagerAdapter adapter = new FavTabPagerAdapter
                (getSupportFragmentManager(), mActivityFavDetailsBinding.categorytabs.getTabCount(), response);
        mActivityFavDetailsBinding.frameLayout.setAdapter(adapter);
//        mActivityCategoryl2Binding.frameLayout.setOffscreenPageLimit(1);
        mActivityFavDetailsBinding.frameLayout.setCurrentItem(0);
        mActivityFavDetailsBinding.frameLayout.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mActivityFavDetailsBinding.categorytabs));

        mActivityFavDetailsBinding.categorytabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mActivityFavDetailsBinding.frameLayout.setCurrentItem(tab.getPosition());
                mFavViewModel.getDataManager().saveFiletrSort(null);

                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    fragment.onActivityResult(AppConstants.REFRESH_CODE, Activity.RESULT_OK, null);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    public void stopLoader() {
        mActivityFavDetailsBinding.pageLoader.stopShimmerAnimation();
        mActivityFavDetailsBinding.pageLoader.setVisibility(View.GONE);
    }

    public void startLoader() {
        mActivityFavDetailsBinding.pageLoader.setVisibility(View.VISIBLE);
        mActivityFavDetailsBinding.pageLoader.startShimmerAnimation();
    }

    @Override
    public void dataLoaded() {
        stopLoader();
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

    public void emptyFav(Boolean ststus) {
        mFavViewModel.emptyFav(ststus);
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void FilterRefresh(String catid) {
        Intent data = new Intent();
        data.putExtra("catid", catid);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(1111, RESULT_OK, data);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        mFavViewModel.totalCart();
    }
}

