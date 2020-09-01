package com.dailylocally.ui.aboutus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityFaqsBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class AboutUsActivity extends BaseActivity<ActivityFaqsBinding, AboutUsViewModel> implements AboutUsNavigator {

    public static final String TAG = AboutUsActivity.class.getSimpleName();
    @Inject
    AboutUsViewModel mFaqViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    AboutUsAdapter mAboutUsAdapter;
    private ActivityFaqsBinding mActivityFaqsBinding;

    Analytics analytics;
    String pageName= AppConstants.SCREEN_FAQS;


    public static Intent newIntent(Context context) {
        return new Intent(context, AboutUsActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.faqsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_faqs;
    }

    @Override
    public AboutUsViewModel getViewModel() {
        return mFaqViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void backClick() {
       onBackPressed();
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(AppConstants.SCREEN_FAQS, AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFaqViewModel.setNavigator(this);
        mActivityFaqsBinding = getViewDataBinding();


              analytics=new Analytics(this, pageName);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityFaqsBinding.recyclerFaqs.setLayoutManager(mLayoutManager);
        mActivityFaqsBinding.recyclerFaqs.setAdapter(mAboutUsAdapter);
        subscribeToLiveData();
    }


    private void subscribeToLiveData() {
        mFaqViewModel.getFaqs().observe(this,
                FaqFragmentViewModel -> mFaqViewModel.addFaqsItemsToList(FaqFragmentViewModel));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
        mFaqViewModel.fetchRepos();

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


    private  boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) DailylocallyApp.getInstance(). getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) DailylocallyApp.getInstance() .getSystemService(Context.CONNECTIVITY_SERVICE);

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

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent= InternetErrorFragment.newIntent(DailylocallyApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                InternetErrorFragment fragment = new InternetErrorFragment();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                internetCheck = true;*/
            }
        }
    };
    private  void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }


    @Override
    public void canceled() {

    }
}
