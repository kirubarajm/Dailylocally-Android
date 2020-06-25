package com.dailylocally.ui.coupons;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityCouponsBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.analytics.Analytics;

import javax.inject.Inject;

public class CouponsActivity extends BaseActivity<ActivityCouponsBinding, CouponsViewModel>
        implements CouponsNavigator,CouponsAdapter.CouponsListener {

    @Inject
    CouponsViewModel mCouponsViewModel;
    @Inject
    CouponsAdapter mCouponsAdapter;
    Analytics analytics;
    String pageName = "Coupons";
    private ActivityCouponsBinding mActivityCouponsBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, CouponsActivity.class);
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
            mCouponsViewModel.validateCoupon();
        }else {
            Toast.makeText(this, "Enter coupon code", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void validateCouponSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void validateCouponFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
        analytics = new Analytics(this, pageName);


        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            String cartCoupon = bundle.getString("cartcoupon");
            if (cartCoupon!=null){
                mCouponsViewModel.cartCoupon.set(true);
            }
        }

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityCouponsBinding.recyclerCoupons.setLayoutManager(mLayoutManager);
        mActivityCouponsBinding.recyclerCoupons.setAdapter(mCouponsAdapter);

        subscribeToLiveData();
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCouponsViewModel.getCouponsList();
    }

    @Override
    public void canceled() {

    }

    @Override
    public void infoClick(CouponsResponse.Result cartdetail) {

    }
}
