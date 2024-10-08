package com.dailylocally.ui.productDetail;

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

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityProductDetailsBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.subscription.SubscriptionActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.Objects;

import javax.inject.Inject;


public class ProductDetailsActivity extends BaseActivity<ActivityProductDetailsBinding, ProductDetailsViewModel> implements
        ProductDetailsNavigator {


    public ActivityProductDetailsBinding mActivityProductDetailsBinding;
    @Inject
    public ProductDetailsViewModel mAddAddressViewModel;

    String vpid = "0";
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(getApplicationContext());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

        }
    };

    public static Intent newIntent(Context context, String fromPage, String ToPage) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra(AppConstants.FROM, fromPage);
        intent.putExtra(AppConstants.PAGE, ToPage);
        return intent;
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private void unregisterWifiReceiver() {
        if (mWifiReceiver != null)
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

    @Override
    public int getBindingVariable() {
        return BR.productDetailsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_details;
    }

    @Override
    public ProductDetailsViewModel getViewModel() {

        return mAddAddressViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void addOrRemoveQuantity(String name, String action, String category, String l1, String l2, String cost, int quantity, String tag) {

        new Analytics().eventAddButton(ProductDetailsActivity.this, name, action, category, l1, l2, cost, quantity, tag, mAddAddressViewModel.totalCart(), "product_detail_page");

    }

    @Override
    public void goBack() {
        Intent intent = new Intent();
        intent.putExtra("pid", vpid);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void subscribe() {
        Intent intent = SubscriptionActivity.newIntent(ProductDetailsActivity.this, AppConstants.SCREEN_NAME_PRODUCT_DETAIL, AppConstants.SCREEN_NAME_SUBSCRIPTION);
        intent.putExtra("pid", vpid);
        startActivityForResult(intent, AppConstants.SUBSCRIPTION_CODE);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void productsDetailsSuccess(ProductDetailsResponse.Result result) {
        String price = "";
        if (result.getDiscountCostStatus()) {
            price = result.getMrpDiscountAmount();
        } else {
            price = result.getMrp();
        }

        new Analytics().eventItemDetailPage(this, result.getCatName(), result.getSubCat1(), result.getSubCat2(), price, AppConstants.SCREEN_NAME_PRODUCT_DETAIL);
        mAddAddressViewModel.offerCost.set(result.getDiscountCost() + " OFF on " + result.getProductname());
    }

    @Override
    public void viewCart() {
        Intent intent = MainActivity.newIntent(ProductDetailsActivity.this, AppConstants.NOTIFY_CART_FRAG, AppConstants.NOTIFY_PRODUCT_DETAILS_ACTV, AppConstants.SCREEN_NAME_PRODUCT_DETAIL, AppConstants.SCREEN_NAME_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void dataLoaded() {
        stopLoader();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityProductDetailsBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);

        vpid = getIntent().getExtras().getString("vpid");
        mAddAddressViewModel.vpid = vpid;

        startLoader();
        mAddAddressViewModel.getProductDetails(vpid);

        Intent intent = getIntent();
        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_PRODUCT_DETAIL);
    }


    public void stopLoader() {
        mActivityProductDetailsBinding.pageLoader.stopShimmerAnimation();
        mActivityProductDetailsBinding.pageLoader.setVisibility(View.GONE);
    }

    public void startLoader() {
        mActivityProductDetailsBinding.pageLoader.setVisibility(View.VISIBLE);
        mActivityProductDetailsBinding.pageLoader.startShimmerAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
        mAddAddressViewModel.checkCart();
        mAddAddressViewModel.totalCart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("pid", vpid);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public void canceled() {

    }
}