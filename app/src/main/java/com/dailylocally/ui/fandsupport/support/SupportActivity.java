package com.dailylocally.ui.fandsupport.support;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivitySupportBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.signup.faqs.FaqActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

import javax.inject.Inject;


public class SupportActivity extends BaseActivity<ActivitySupportBinding, SupportViewModel> implements
        SupportNavigator {


    public ActivitySupportBinding mActivitySupportBinding;
    @Inject
    public SupportViewModel mAddAddressViewModel;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_ADD_ADDRESS;

    public static Intent newIntent(Context context) {
        return new Intent(context, SupportActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.supportViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_support;
    }

    @Override
    public SupportViewModel getViewModel() {

        return mAddAddressViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }


    @Override
    public void feedback() {

    }

    @Override
    public void support() {

    }

    @Override
    public void faq() {
        Intent intent = FaqActivity.newIntent(SupportActivity.this);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySupportBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);

        analytics = new Analytics(this, pageName);



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void canceled() {

    }
}