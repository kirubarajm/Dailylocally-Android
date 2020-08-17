package com.dailylocally.ui.category.l1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityCategoryl1Binding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;


public class CategoryL1Activity extends BaseActivity<ActivityCategoryl1Binding, CategoryL1ViewModel> implements CategoryL1Navigator, L1CategoriesAdapter.CategoriesAdapterListener {

    @Inject
    CategoryL1ViewModel mCategoryL1ViewModel;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @Inject
    L1CategoriesAdapter l1CategoriesAdapter;

    ActivityCategoryl1Binding mActivityCategoryl1Binding;
    String categoryid;
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(CategoryL1Activity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    };

    private TextView[] dots;

    public static Intent newIntent(Context context) {

        return new Intent(context, CategoryL1Activity.class);
    }

    public void stopCartLoader() {
        mActivityCategoryl1Binding.pageLoader.stopShimmerAnimation();
        mActivityCategoryl1Binding.pageLoader.setVisibility(View.GONE);
    }

    public void startCartLoader() {
        mActivityCategoryl1Binding.pageLoader.setVisibility(View.VISIBLE);
        mActivityCategoryl1Binding.pageLoader.startShimmerAnimation();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCategoryL1ViewModel.setNavigator(this);
        mActivityCategoryl1Binding = getViewDataBinding();
        l1CategoriesAdapter.setListener(this);


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            categoryid = intent.getExtras().getString("catid");
            startCartLoader();
            mCategoryL1ViewModel.fetchSubCategoryList(categoryid);
        }
        subscribeLiveData();
        mActivityCategoryl1Binding.subcategories.setLayoutManager(linearLayoutManager);
        mActivityCategoryl1Binding.subcategories.setAdapter(l1CategoriesAdapter);
    }


    public void subscribeLiveData() {
        mCategoryL1ViewModel.getCategoryListLiveData().observe(this,
                categoryList -> mCategoryL1ViewModel.addDatatoList(categoryList));

    }

    @Override
    public int getBindingVariable() {
        return BR.categoryL1ViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_categoryl1;
    }

    @Override
    public CategoryL1ViewModel getViewModel() {
        return mCategoryL1ViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void cartLoaded() {
        stopCartLoader();
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
    public void categoryItemClicked(L1CategoryResponse.Result result) {

        Intent intent = CategoryL2Activity.newIntent(CategoryL1Activity.this);
        intent.putExtra("catid",categoryid);
        intent.putExtra("scl1id",String.valueOf(result.getScl1Id()));
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

}

