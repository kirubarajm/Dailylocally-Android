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
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.PreChatForm;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

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

        if (mAddAddressViewModel.getDataManager().getCurrentUserId()!=null) {
            Intent intent = HelpActivity.newIntent(FeedbackSupportActivity.this, AppConstants.NOTIFY_SUPPORT_ACTV, AppConstants.CHAT_PAGE_TYPE_SUPPORT, "0");
            startActivity(intent);
        }else {

            ZopimChat.init(getString(R.string.zopim_account_id));

            PreChatForm preChatForm = new PreChatForm.Builder()
                    .name(PreChatForm.Field.REQUIRED)
                    .email(PreChatForm.Field.NOT_REQUIRED)
                    .phoneNumber(PreChatForm.Field.REQUIRED)
                    .department(PreChatForm.Field.OPTIONAL)
                    .message(PreChatForm.Field.NOT_REQUIRED)
                    .build();
            final VisitorInfo.Builder build = new VisitorInfo.Builder()
                    .note("New User");
            ZopimChat.setVisitorInfo(build.build());
            ZopimChat.SessionConfig config = new ZopimChat.SessionConfig()
                    .preChatForm(preChatForm)
                    .tags("login_issue")
                    .department("Daily locally");
            ZopimChatActivity.startActivity(this, config);

        }
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