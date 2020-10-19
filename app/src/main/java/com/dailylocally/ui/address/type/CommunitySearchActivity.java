package com.dailylocally.ui.address.type;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityCommunitySearchBinding;
import com.dailylocally.ui.address.addAddress.AddressNewActivity;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.joinCommunity.CommunityActivity;
import com.dailylocally.ui.joinCommunity.CommunityAdapter;
import com.dailylocally.ui.joinCommunity.CommunityResponse;
import com.dailylocally.ui.onboarding.PrefManager;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class CommunitySearchActivity extends BaseActivity<ActivityCommunitySearchBinding, CommunitySearchViewModel>
        implements CommunitySearchNavigator,CommunityAdapter.TransactionHistoryInfoListener {

    @Inject
    CommunitySearchViewModel mCommunitySearchViewModel;
    @Inject
    CommunityAdapter mCommunityAdapter;
    Analytics analytics;
    String pageName = "Splash screen";
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
               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                InternetErrorFragment fragment = new InternetErrorFragment();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                internetCheck = true;*/
            }
        }
    };
    private ActivityCommunitySearchBinding mActivityCommunitySearchBinding;
    private PrefManager prefManager;

    public static Intent newIntent(Context context) {
        return new Intent(context, CommunitySearchActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        setResult(Activity.RESULT_CANCELED);
        onBackPressed();
    }

    @Override
    public void addManualy() {
        Intent intent=new Intent();
        intent.putExtra("text",mActivityCommunitySearchBinding.searchCommunity.getQuery().toString());
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public int getBindingVariable() {
        return BR.communitySearchViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_community_search;
    }

    @Override
    public CommunitySearchViewModel getViewModel() {
        return mCommunitySearchViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCommunitySearchBinding = getViewDataBinding();
        mCommunitySearchViewModel.setNavigator(this);
        mCommunityAdapter.setListener(this);


        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
      //  mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityCommunitySearchBinding.recyclerCommunity.setLayoutManager(mLayoutManager);
        mActivityCommunitySearchBinding.recyclerCommunity.setAdapter(mCommunityAdapter);



        if (getIntent().getExtras() != null)
            mCommunitySearchViewModel.newUser.set( getIntent().getExtras().getBoolean("newuser", false));


        subscribeToLiveData();
        mCommunitySearchViewModel.getCommunityList();
        mActivityCommunitySearchBinding.searchCommunity.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                try {
                    mCommunitySearchViewModel.quickSearch(s);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                try {

                    mCommunitySearchViewModel.quickSearch(s);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

    }
    private void subscribeToLiveData() {
        mCommunitySearchViewModel.getCommunityListItemsLiveData().observe(this,
                catregoryItemViewModel -> mCommunitySearchViewModel.addCommunityListItemsToList(catregoryItemViewModel));
    }
    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();




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

    }

    @Override
    public void onItemClick(CommunityResponse.Result cartdetail) {
        Intent inIntent = CommunityActivity.newIntent(CommunitySearchActivity.this);
        inIntent.putExtra("comid",String.valueOf(cartdetail.getComid()));
        inIntent.putExtra("name",String.valueOf(cartdetail.getCommunityname()));
        inIntent.putExtra("residency",String.valueOf(cartdetail.getNoOfApartments()+" Residences"));
        inIntent.putExtra("lat",cartdetail.getLat());
        inIntent.putExtra("lng",cartdetail.get_long());
        startActivity(inIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
