package com.dailylocally.ui.fandsupport;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityFeedbackSupportBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.fandsupport.help.HelpActivity;
import com.dailylocally.ui.signup.faqs.FaqActivity;
import com.dailylocally.ui.signup.tandc.TermsAndConditionActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.zendesk.service.ErrorResponse;
import com.zendesk.service.ZendeskCallback;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import zendesk.chat.Chat;
import zendesk.chat.ChatEngine;
import zendesk.chat.ChatMenuAction;
import zendesk.chat.ChatProvider;
import zendesk.chat.ProfileProvider;
import zendesk.chat.VisitorInfo;
import zendesk.messaging.MessagingActivity;


public class FeedbackSupportActivity extends BaseActivity<ActivityFeedbackSupportBinding, FeedbackSupportViewModel> implements
        FeedbackSupportNavigator {


    public ActivityFeedbackSupportBinding mActivityFeedbackSupportBinding;
    @Inject
    public FeedbackSupportViewModel mAddAddressViewModel;
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

    /*public static Intent newIntent(Context context, String ToPage, String fromPage) {
        Intent intent = new Intent(context, FeedbackSupportActivity.class);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);
        return intent;
    }*/

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

    public static Intent newIntent(Context context,String fromPage,String ToPage) {
        Intent intent = new Intent(context, FeedbackSupportActivity.class);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);
        return intent;
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


        Intent intent = TermsAndConditionActivity.newIntent(FeedbackSupportActivity.this,AppConstants.SCREEN_NAME_FEEDBACK_SUPPORT,AppConstants.SCREEN_NAME_TERMS_AND_CONDITION);
        intent.putExtra(AppConstants.PAGE,AppConstants.NOTIFY_SUPPORT_ACTV);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void support() {

        if (mAddAddressViewModel.getDataManager().getCurrentUserId() != null) {
            Intent intent = HelpActivity.newIntent(FeedbackSupportActivity.this, AppConstants.NOTIFY_SUPPORT_ACTV, AppConstants.CHAT_PAGE_TYPE_SUPPORT, "0"
                    ,AppConstants.SCREEN_NAME_FEEDBACK_SUPPORT,AppConstants.SCREEN_NAME_HELP);

            startActivity(intent);
        } else {

            // ZopimChat.init(getString(R.string.zopim_account_id));



            ProfileProvider profileProvider = Chat.INSTANCE.providers().profileProvider();
            profileProvider.setVisitorNote("New User", new ZendeskCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }

                @Override
                public void onError(ErrorResponse errorResponse) {

                }
            });
            ArrayList<String> tag=new ArrayList<>();
            tag.add("login_issue");
            profileProvider.addVisitorTags(tag, new ZendeskCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }

                @Override
                public void onError(ErrorResponse errorResponse) {

                }
            });
            VisitorInfo visitorInfo = VisitorInfo.builder()
                    .build();

            profileProvider.setVisitorInfo(visitorInfo, new ZendeskCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }

                @Override
                public void onError(ErrorResponse errorResponse) {

                }
            });


            ChatProvider chatProvider = Chat.INSTANCE.providers().chatProvider();

            chatProvider.setDepartment("Daily locally", new ZendeskCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }

                @Override
                public void onError(ErrorResponse errorResponse) {

                }
            });

            MessagingActivity.builder()
                    .withEngines(ChatEngine.engine())
                    .show(FeedbackSupportActivity.this);


           /* PreChatForm preChatForm = new PreChatForm.Builder()
                    .name(PreChatForm.Field.REQUIRED)
                    .email(PreChatForm.Field.NOT_REQUIRED)
                    .phoneNumber(PreChatForm.Field.REQUIRED)
                    .department(PreChatForm.Field.OPTIONAL)
                    .message(PreChatForm.Field.NOT_REQUIRED)
                    .build();*/


        }
    }

    @Override
    public void faq() {

        Intent intent = FaqActivity.newIntent(FeedbackSupportActivity.this,AppConstants.SCREEN_NAME_FEEDBACK_SUPPORT,AppConstants.SCREEN_NAME_FAQ);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFeedbackSupportBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);

        Intent intent = getIntent();
        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_FEEDBACK_SUPPORT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
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
        super.onBackPressed();
    }

    @Override
    public void canceled() {

    }
}