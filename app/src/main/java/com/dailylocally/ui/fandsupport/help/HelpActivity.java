package com.dailylocally.ui.fandsupport.help;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityHelpBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.chat.IssuesAdapter;
import com.dailylocally.utilities.chat.IssuesListResponse;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.zopim.android.sdk.api.ChatApi;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.api.ZopimChatApi;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.PreChatForm;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class HelpActivity extends BaseActivity<ActivityHelpBinding, HelpViewModel> implements HelpNavigator, HasSupportFragmentInjector, IssuesAdapter.IssuesAdapterListener {

    @Inject
    HelpViewModel mHelpViewModel;
    ActivityHelpBinding mActivityOrderHelpBinding;
    String strOrderId;
    String message = null;
    ProgressDialog dialog;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    IssuesAdapter issuesAdapter;

    @Inject
    LinearLayoutManager layoutManager;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent = InternetErrorFragment.newIntent(DailylocallyApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    };

    public static Intent newIntent(Context context, String page, Integer type, String orderid,String ToPage,String fromPage) {
        Intent intent = new Intent(context, HelpActivity.class);
        intent.putExtra(AppConstants.PAGE, page);
        intent.putExtra("type", type);
        intent.putExtra("orderid", orderid);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);
        return intent;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createChat(String department, String tag, String note) {

        if (mHelpViewModel.getDataManager().getChatOrderid() != null) {

            if (mHelpViewModel.getDataManager().getChatOrderid().equals(String.valueOf(mHelpViewModel.orderid))) {
                openChat(department, tag, note);

            } else {

                ChatApi chatApi = ZopimChatApi.resume(this);
                chatApi.endChat();
                chatApi.endChat();
                openChat(department, tag, note);
            }

        } else {
            openChat(department, tag, note);
        }
    }

    @Override
    public void mapChat(String department, String tag, String note, int issueid, int tid) {

        mHelpViewModel.mapTicketidToOrderid(issueid, tid, tag, department, note);
    }


    public void openChat(String department, String tag, String note) {

        ZopimChat.init(getString(R.string.zopim_account_id));

        final VisitorInfo.Builder build = new VisitorInfo.Builder()
                .email(mHelpViewModel.getDataManager().getCurrentUserEmail())
                .name(mHelpViewModel.getDataManager().getCurrentUserName())
                .note(note)
                .phoneNumber(mHelpViewModel.getDataManager().getCurrentUserPhNo());
        ZopimChat.setVisitorInfo(build.build());

// build pre chat form config
        PreChatForm preChatForm = new PreChatForm.Builder()
                .name(PreChatForm.Field.REQUIRED)
                .email(PreChatForm.Field.NOT_REQUIRED)
                .phoneNumber(PreChatForm.Field.REQUIRED)
                .department(PreChatForm.Field.OPTIONAL)
                .message(PreChatForm.Field.NOT_REQUIRED)
                .build();
// build session config
        ZopimChat.SessionConfig config = new ZopimChat.SessionConfig()
                .preChatForm(preChatForm)
                .department(department);
// start chat activity with config
        mHelpViewModel.getDataManager().saveChatOrderID(String.valueOf(mHelpViewModel.orderid));
        ZopimChatActivity.startActivity(this, config);

    }

    @Override
    public int getBindingVariable() {
        return BR.helpViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    public HelpViewModel getViewModel() {
        return mHelpViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOrderHelpBinding = getViewDataBinding();
        mHelpViewModel.setNavigator(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");

        issuesAdapter.setListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mHelpViewModel.orderid = getIntent().getExtras().getString("orderid");
            mHelpViewModel.stype = getIntent().getExtras().getInt("type");
            mHelpViewModel.getIssuesList(mHelpViewModel.stype);
        }

        subscribeToLiveData();

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityOrderHelpBinding.recyclerviewIssue.setLayoutManager(layoutManager);
        mActivityOrderHelpBinding.recyclerviewIssue.setAdapter(issuesAdapter);


        Intent intent = getIntent();
        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_HELP);
    }

    private void subscribeToLiveData() {
        mHelpViewModel.getIssuesLiveData().observe(this,
                issuesItemViewModel -> mHelpViewModel.addIssuesListItemsToList(issuesItemViewModel));

    }


    public void showAlert() {

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

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
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
        unregisterReceiver(mWifiReceiver);
    }

    @Override
    public void canceled() {
        finish();
    }

    @Override
    public void issueItemClick(IssuesListResponse.Result issues) {
           mHelpViewModel.getIssuesNote(issues.getType(), issues.getId());

    }
}

