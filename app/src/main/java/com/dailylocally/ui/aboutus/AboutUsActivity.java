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
import com.dailylocally.databinding.ActivityAboutusBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.Objects;

import javax.inject.Inject;

public class AboutUsActivity extends BaseActivity<ActivityAboutusBinding, AboutUsViewModel> implements AboutUsNavigator {

    public static final String TAG = AboutUsActivity.class.getSimpleName();
    @Inject
    AboutUsViewModel mAboutUsViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    AboutUsAdapter mAboutUsAdapter;
    private ActivityAboutusBinding mActivityAboutusBinding;


    public static Intent newIntent(Context context,String ToPage,String fromPage) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.aboutUsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_aboutus;
    }

    @Override
    public AboutUsViewModel getViewModel() {
        return mAboutUsViewModel;
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
        super.onBackPressed();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAboutUsViewModel.setNavigator(this);
        mActivityAboutusBinding = getViewDataBinding();

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityAboutusBinding.recyclerFaqs.setLayoutManager(mLayoutManager);
        mActivityAboutusBinding.recyclerFaqs.setAdapter(mAboutUsAdapter);
        subscribeToLiveData();

        Intent intent = getIntent();
        new Analytics().eventPageOpens(this,Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_ABOUT_US);
    }

    private void subscribeToLiveData() {
        mAboutUsViewModel.getFaqs().observe(this,
                FaqFragmentViewModel -> mAboutUsViewModel.addFaqsItemsToList(FaqFragmentViewModel));
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
        mAboutUsViewModel.fetchRepos();

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
