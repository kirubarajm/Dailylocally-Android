package com.dailylocally.ui.collection.l2;

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
import com.dailylocally.databinding.ActivityCollectionDetailsBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.category.l2.products.filter.FilterFragment;
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


public class CollectionDetailsActivity extends BaseActivity<ActivityCollectionDetailsBinding, CollectionDetailsViewModel> implements CollectionDetailsNavigator, HasSupportFragmentInjector, FilterListener {

    @Inject
    CollectionDetailsViewModel mCollectionDetailsViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    L2SliderAdapter adapter;


    ActivityCollectionDetailsBinding mActivityCollectionDetailsBinding;
    String cid;
    String scl1id;
    String vpid;
    ViewPager viewPager;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(CollectionDetailsActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
            }
        }
    };

    private TextView[] dots;

    public static Intent newIntent(Context context) {

        return new Intent(context, CollectionDetailsActivity.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCollectionDetailsViewModel.setNavigator(this);
        mActivityCollectionDetailsBinding = getViewDataBinding();
        subscribeLiveData();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            cid = intent.getExtras().getString("cid");

            mCollectionDetailsViewModel.fetchSubCategoryList(cid);
        }



       /* mActivityCategoryl2Binding.imageSlider.setSliderAdapter(adapter);

      //  mActivityCategoryl2Binding.imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        mActivityCategoryl2Binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        mActivityCategoryl2Binding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        mActivityCategoryl2Binding.imageSlider.setIndicatorSelectedColor(Color.WHITE);
        mActivityCategoryl2Binding.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        mActivityCategoryl2Binding.imageSlider.setScrollTimeInSec(4); //set scroll delay in seconds :
        mActivityCategoryl2Binding.imageSlider.startAutoCycle();*/

    }


    public void subscribeLiveData() {
        mCollectionDetailsViewModel.getCategoryListLiveData().observe(this,
                categoryList -> mCollectionDetailsViewModel.addDatatoList(categoryList));

 mCollectionDetailsViewModel.getSliderListLiveData().observe(this,
               sliderList -> mCollectionDetailsViewModel.addSlidertoList(sliderList));

    }

    public void refreshCart() {
        mCollectionDetailsViewModel.totalCart();

    }

    @Override
    public int getBindingVariable() {
        return BR.collectionDetailsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collection_details;
    }

    @Override
    public CollectionDetailsViewModel getViewModel() {
        return mCollectionDetailsViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    public void openFilter(String scl1id) {

        Bundle bundle=new Bundle();
        bundle.putString("scl1id",scl1id);
        bundle.putString(AppConstants.PAGE,AppConstants.NOTIFY_COLLECTION_ACTV);

        FilterFragment filterFragment = new FilterFragment();
        filterFragment.setArguments(bundle);
        filterFragment.show(getSupportFragmentManager(), filterFragment.getTag());

    }


    public void openSort(String scl1id) {
        Bundle bundle=new Bundle();
        bundle.putString("scl1id",scl1id);

        SortFragment sortFragment = new SortFragment();
        sortFragment.setArguments(bundle);
        sortFragment.show(getSupportFragmentManager(), sortFragment.getTag());
    }
    @Override
    public void viewCart() {
        Intent intent = MainActivity.newIntent(CollectionDetailsActivity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("page", AppConstants.SCREEN_CART_PAGE);
        startActivity(intent);
    }

    @Override
    public void createtabs(CollectionDetailsResponse response) {


        if (response.getResult() == null) {
            mActivityCollectionDetailsBinding.categorytabs.setVisibility(View.GONE);
        } else if (response.getResult() != null && response.getResult().size() == 0) {
            mActivityCollectionDetailsBinding.categorytabs.setVisibility(View.GONE);
        }


        mActivityCollectionDetailsBinding.categorytabs.addTab(mActivityCollectionDetailsBinding.categorytabs.newTab().setText("All"));

        if (response.getResult() != null) {
            for (int k = 0; k < response.getResult().size(); k++) {

                mActivityCollectionDetailsBinding.categorytabs.addTab(mActivityCollectionDetailsBinding.categorytabs.newTab().setText(response.getResult().get(k).getName()));
            }
        }

        CollectionTabPagerAdapter adapter = new CollectionTabPagerAdapter
                (getSupportFragmentManager(), mActivityCollectionDetailsBinding.categorytabs.getTabCount(), response,cid);
        mActivityCollectionDetailsBinding.frameLayout.setAdapter(adapter);
//        mActivityCategoryl2Binding.frameLayout.setOffscreenPageLimit(1);
        mActivityCollectionDetailsBinding.frameLayout.setCurrentItem(0);
        mActivityCollectionDetailsBinding.frameLayout.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mActivityCollectionDetailsBinding.categorytabs));



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


        if (mActivityCollectionDetailsBinding.categorytabs.getTabCount() == 3) {
            mActivityCollectionDetailsBinding.categorytabs.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mActivityCollectionDetailsBinding.categorytabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        }


        mActivityCollectionDetailsBinding.categorytabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mActivityCollectionDetailsBinding.frameLayout.setCurrentItem(tab.getPosition());
                mCollectionDetailsViewModel.getDataManager().saveFiletrSort(null);
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
        data.putExtra("scl2id",scl2id);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(1111, RESULT_OK, data);
        }
    }
}

