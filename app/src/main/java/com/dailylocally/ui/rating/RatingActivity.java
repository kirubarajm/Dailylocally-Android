package com.dailylocally.ui.rating;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityRatingBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

import java.time.DayOfWeek;
import java.util.Date;

import javax.inject.Inject;


public class RatingActivity extends BaseActivity<ActivityRatingBinding, RatingViewModel> implements
        RatingNavigator {


    public ActivityRatingBinding mActivityRatingBinding;
    @Inject
    public RatingViewModel mAddAddressViewModel;
    Date date = null;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_ADD_ADDRESS;

    public static Intent newIntent(Context context) {
        return new Intent(context, RatingActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.ratingViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_rating;
    }

    @Override
    public RatingViewModel getViewModel() {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRatingBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
             date = new Date(bundle.getLong("date"));
        }
        analytics = new Analytics(this, pageName);

        mActivityRatingBinding.relProductDelivery.setVisibility(View.GONE);

        mActivityRatingBinding.chkDeliveryNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mActivityRatingBinding.chkDeliveryNo.isChecked()){
                    mActivityRatingBinding.chkDeliveryYes.setChecked(false);
                }
            }
        });

        mActivityRatingBinding.chkDeliveryYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mActivityRatingBinding.chkDeliveryYes.isChecked()){
                    mActivityRatingBinding.chkDeliveryNo.setChecked(false);
                }
            }
        });

        mActivityRatingBinding.chkProductNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mActivityRatingBinding.chkProductNo.isChecked()){
                    mActivityRatingBinding.chkProductYes.setChecked(false);
                    mActivityRatingBinding.relProductDelivery.setVisibility(View.GONE);
                }
            }
        });

        mActivityRatingBinding.chkProductYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mActivityRatingBinding.chkProductYes.isChecked()){
                    mActivityRatingBinding.chkProductNo.setChecked(false);
                    mActivityRatingBinding.relProductDelivery.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAddAddressViewModel.getDayWiseOrderDetails(date);
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