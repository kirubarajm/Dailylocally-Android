package com.dailylocally.ui.coupons;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityCouponsBinding;
import com.dailylocally.ui.aboutus.AboutUsActivity;
import com.dailylocally.ui.account.referrals.ReferralsActivity;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.Objects;

import javax.inject.Inject;

public class CouponsActivity extends BaseActivity<ActivityCouponsBinding, CouponsViewModel>
        implements CouponsNavigator, CouponsAdapter.CouponsListener {

    @Inject
    CouponsViewModel mCouponsViewModel;
    @Inject
    CouponsAdapter mCouponsAdapter;
    String pageId;
    private ActivityCouponsBinding mActivityCouponsBinding;
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
    public static Intent newIntent(Context context,String ToPage,String fromPage) {
        Intent intent = new Intent(context, CouponsActivity.class);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);
        return intent;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void validateCouponClick() {
        String couponName = mActivityCouponsBinding.code.getText().toString();
        if (!couponName.equals("")) {
            mCouponsViewModel.validateCoupon(couponName);
        } else {
            Toast.makeText(this, "Enter coupon code", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void validateCouponSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void validateCouponFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void refer() {
        Intent intent = ReferralsActivity.newIntent(this,AppConstants.SCREEN_COUPON_LIST,AppConstants.SCREEN_REFERRAL);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public int getBindingVariable() {
        return BR.couponsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupons;
    }

    @Override
    public CouponsViewModel getViewModel() {
        return mCouponsViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCouponsBinding = getViewDataBinding();
        mCouponsViewModel.setNavigator(this);
        mCouponsAdapter.setListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pageId = bundle.getString(AppConstants.PAGE);
            if (pageId != null) {
                if (pageId.equals(AppConstants.NOTIFY_MYACCOUNT_FRAG)) {
                    mCouponsViewModel.flagApplyVisibility.set(false);
                    mCouponsAdapter.forApplyView(false);
                } else {
                    mCouponsViewModel.flagApplyVisibility.set(true);
                    mCouponsAdapter.forApplyView(true);
                }
            }
        }

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityCouponsBinding.recyclerCoupons.setLayoutManager(mLayoutManager);
        mActivityCouponsBinding.recyclerCoupons.setAdapter(mCouponsAdapter);

        subscribeToLiveData();

        Intent intent = getIntent();
        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_COUPON_LIST);
    }

    private void subscribeToLiveData() {
        mCouponsViewModel.getCouponsItemsLiveData().observe(this,
                catregoryItemViewModel -> mCouponsViewModel.addCouponsItemsToList(catregoryItemViewModel));

    }

    @Override
    public void onFragmentDetached(String tag) {

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
        mCouponsViewModel.getCouponsList();
    }

    @Override
    public void canceled() {

    }

    @Override
    public void onApplyClick(CouponsResponse.Result cartdetail) {

        if (pageId != null) {
            if (pageId.equals(AppConstants.NOTIFY_CART_FRAG)) {
                mCouponsViewModel.validateCoupon(cartdetail.getCouponName());
            }
        }
    }
}
