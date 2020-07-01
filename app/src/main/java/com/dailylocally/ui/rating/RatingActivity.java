package com.dailylocally.ui.rating;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityRatingBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.calendarView.CalendarDayWiseResponse;

import java.util.Date;

import javax.inject.Inject;


public class RatingActivity extends BaseActivity<ActivityRatingBinding, RatingViewModel> implements
        RatingNavigator, RatingDayWiseAdapter.CategoriesAdapterListener {


    public ActivityRatingBinding mActivityRatingBinding;
    @Inject
    public RatingViewModel mRatingViewModel;
    @Inject
    public RatingDayWiseAdapter mRatingProductAdapter;
    Date date = null;

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

        return mRatingViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void helpClick() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRatingBinding = getViewDataBinding();
        mRatingViewModel.setNavigator(this);
        mRatingProductAdapter.setListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
             date = new Date(bundle.getLong("date"));
        }

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityRatingBinding.recyclerProduct.setLayoutManager(mLayoutManager);
        mActivityRatingBinding.recyclerProduct.setAdapter(mRatingProductAdapter);
        subscribeToLiveData();

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
                    mActivityRatingBinding.relProductDelivery.setVisibility(View.VISIBLE);
                }
            }
        });

        mActivityRatingBinding.chkProductYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mActivityRatingBinding.chkProductYes.isChecked()){
                    mActivityRatingBinding.chkProductNo.setChecked(false);
                    mActivityRatingBinding.relProductDelivery.setVisibility(View.GONE);
                }
            }
        });

    }

    private void subscribeToLiveData() {
        mRatingViewModel.getOrdernowLiveData().observe(this,
                ordernowItemViewModel -> mRatingViewModel.addOrderNowItemsToList(ordernowItemViewModel));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRatingViewModel.getDayWiseOrderDetails(date);
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

    @Override
    public void onItemClick(CalendarDayWiseResponse.Result.Item result) {

    }
}