package com.dailylocally.ui.fandsupport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityFeedbackSupportBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.fandsupport.help.HelpActivity;
import com.dailylocally.ui.fandsupport.support.SupportActivity;
import com.dailylocally.ui.signup.faqs.FaqActivity;
import com.dailylocally.ui.signup.tandc.TermsAndConditionActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

import javax.inject.Inject;


public class FeedbackSupportActivity extends BaseActivity<ActivityFeedbackSupportBinding, FeedbackSupportViewModel> implements
        FeedbackSupportNavigator {


    public ActivityFeedbackSupportBinding mActivityFeedbackSupportBinding;
    @Inject
    public FeedbackSupportViewModel mAddAddressViewModel;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_ADD_ADDRESS;

    public static Intent newIntent(Context context) {
        return new Intent(context, FeedbackSupportActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.feedbackSupportViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback_support;
    }

    @Override
    public FeedbackSupportViewModel getViewModel() {

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
    public void termsAndC() {

        Intent intent = TermsAndConditionActivity.newIntent(FeedbackSupportActivity.this);
        intent.putExtra(AppConstants.PAGE,AppConstants.NOTIFY_SUPPORT_ACTV);
        startActivity(intent);


    }

    @Override
    public void support() {
        Intent intent = HelpActivity.newIntent(FeedbackSupportActivity.this,AppConstants.NOTIFY_SUPPORT_ACTV,3,"0");
        startActivity(intent);
    }

    @Override
    public void faq() {
        Intent intent = FaqActivity.newIntent(FeedbackSupportActivity.this);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFeedbackSupportBinding = getViewDataBinding();
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