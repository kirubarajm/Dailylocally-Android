package com.dailylocally.ui.category.viewall;

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
import com.dailylocally.databinding.ActivityCategoryl12Binding;
import com.dailylocally.databinding.ActivityCatproductsBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.category.l1.L1CategoryResponse;
import com.dailylocally.ui.category.l2.products.filter.FilterFragment;
import com.dailylocally.ui.category.l2.products.filter.FilterListener;
import com.dailylocally.ui.category.l2.products.sort.SortFragment;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class CatProductActivity extends BaseActivity<ActivityCatproductsBinding, CatProductViewModel> implements CatProductNavigator, HasSupportFragmentInjector, FilterListener {

    @Inject
    CatProductViewModel mCatProductViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;



    ActivityCatproductsBinding mActivityCategoryl2Binding;
    String categoryid;
    String scl1id;
    String vpid;
    ViewPager viewPager;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(CatProductActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
            }
        }
    };

    private TextView[] dots;

    public static Intent newIntent(Context context) {

        return new Intent(context, CatProductActivity.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCatProductViewModel.setNavigator(this);
        mActivityCategoryl2Binding = getViewDataBinding();


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            categoryid = intent.getExtras().getString("catid");
            startLoader();
            mCatProductViewModel.fetchSubCategoryList(categoryid);
        }

      /*mActivityCategoryl2Binding.imageSlider.setSliderAdapter(adapter);
      //mActivityCategoryl2Binding.imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        mActivityCategoryl2Binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        mActivityCategoryl2Binding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        mActivityCategoryl2Binding.imageSlider.setIndicatorSelectedColor(Color.WHITE);
        mActivityCategoryl2Binding.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        mActivityCategoryl2Binding.imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        mActivityCategoryl2Binding.imageSlider.startAutoCycle();*/

    }



    public void refreshCart() {
        mCatProductViewModel.totalCart();

    }

    @Override
    public int getBindingVariable() {
        return BR.catProductsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_catproducts;
    }

    @Override
    public CatProductViewModel getViewModel() {
        return mCatProductViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    public void stopLoader() {
        mActivityCategoryl2Binding.pageLoader.stopShimmerAnimation();
        mActivityCategoryl2Binding.pageLoader.setVisibility(View.GONE);
    }

    public void startLoader() {
        mActivityCategoryl2Binding.pageLoader.setVisibility(View.VISIBLE);
        mActivityCategoryl2Binding.pageLoader.startShimmerAnimation();
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    public void openFilter(String catid,String scl1id) {

        Bundle bundle=new Bundle();
        bundle.putString("scl1id",scl1id);
        bundle.putString("catid",catid);
        bundle.putString(AppConstants.PAGE,AppConstants.NOTIFY_CATEGORY_L1_PROD_ACTV);

        FilterFragment filterFragment = new FilterFragment();
        filterFragment.setArguments(bundle);
        filterFragment.show(getSupportFragmentManager(), filterFragment.getTag());

    }


    public void openSort(String scl1id) {
        Bundle bundle=new Bundle();
        bundle.putString("scl1id",scl1id);
        bundle.putString("type","2");
        SortFragment sortFragment = new SortFragment();
        sortFragment.setArguments(bundle);
        sortFragment.show(getSupportFragmentManager(), sortFragment.getTag());
    }
    @Override
    public void viewCart() {
        Intent intent = MainActivity.newIntent(CatProductActivity.this,AppConstants.NOTIFY_CART_FRAG,AppConstants.NOTIFY_CATEGORY_L2_ACTV);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void createtabs(L1CategoryResponse response) {


        if (response.getResult() == null) {
            mActivityCategoryl2Binding.categorytabs.setVisibility(View.GONE);
           // mActivityCategoryl2Binding.htabToolbar.setVisibility(View.GONE);
        } else if (response.getResult() != null && response.getResult().size() == 0) {
            mActivityCategoryl2Binding.categorytabs.setVisibility(View.GONE);
         //   mActivityCategoryl2Binding.htabToolbar.setVisibility(View.GONE);
        }


        mActivityCategoryl2Binding.categorytabs.addTab(mActivityCategoryl2Binding.categorytabs.newTab().setText("All"));

        if (response.getResult() != null) {
            for (int k = 0; k < response.getResult().size(); k++) {

                mActivityCategoryl2Binding.categorytabs.addTab(mActivityCategoryl2Binding.categorytabs.newTab().setText(response.getResult().get(k).getName()));
            }
        }

        CatProductPlansPagerAdapter adapter = new CatProductPlansPagerAdapter
                (getSupportFragmentManager(), mActivityCategoryl2Binding.categorytabs.getTabCount(), response,categoryid);
        mActivityCategoryl2Binding.frameLayout.setAdapter(adapter);
//        mActivityCategoryl2Binding.frameLayout.setOffscreenPageLimit(1);
        mActivityCategoryl2Binding.frameLayout.setCurrentItem(0);
        mActivityCategoryl2Binding.frameLayout.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mActivityCategoryl2Binding.categorytabs));



        /*mActivityCategoryl2Binding.frameLayout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
             //   mActivityCategoryl2Binding.frameLayout.setCurrentItem(position);
               *//* for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    fragment.onActivityResult(AppConstants.SUBSCRIPTION_CODE, Activity.RESULT_OK, null);
                }*//*
            }

            @Override
            public void onPageSelected(int position) {
               // mActivityCategoryl2Binding.frameLayout.setCurrentItem(position);
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


       /* if (mActivityCategoryl2Binding.categorytabs.getTabCount() == 3) {
            mActivityCategoryl2Binding.categorytabs.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mActivityCategoryl2Binding.categorytabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        }*/

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(AppConstants.REFRESH_CODE, Activity.RESULT_OK, null);
        }
        mActivityCategoryl2Binding.categorytabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mActivityCategoryl2Binding.frameLayout.setCurrentItem(tab.getPosition());
                mCatProductViewModel.getDataManager().saveFiletrSort(null);
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


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void FilterRefresh(String scl2id) {
        Intent data=new Intent();
        data.putExtra("scl1id",scl2id);

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
        mCatProductViewModel.totalCart();
    }
}

