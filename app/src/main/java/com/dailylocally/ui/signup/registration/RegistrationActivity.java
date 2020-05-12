package com.dailylocally.ui.signup.registration;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityNameGenderBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.home.MainActivity;
import com.dailylocally.ui.home.region.RegionSearchModel;
import com.dailylocally.ui.signup.SignUpActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.MvvmApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class RegistrationActivity extends BaseActivity<ActivityNameGenderBinding, RegistrationViewModel>
        implements RegistrationNavigator {

    @Inject
    RegistrationViewModel mLoginViewModelMain;
    int gender;
    RegionListAdapter regionListAdapter;
    RegionSearchModel.Result result;

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );
    Analytics analytics;
    String pageName= AppConstants.SCREEN_USER_REGISTRATION;
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
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
    private ActivityNameGenderBinding mActivityNameGenderBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void proceedClick() {
        String name = mActivityNameGenderBinding.edtName.getText().toString();
        String emailText = mActivityNameGenderBinding.email.getText().toString();
        String referral = mActivityNameGenderBinding.referral.getText().toString();

        String email = "";

        if (emailText.length() > 0 && emailText.contains(" ")) {
            email= emailText.replaceAll(" ", "");
            mActivityNameGenderBinding.email.setText(email);
        }

        if (validForProceed()) {
            mLoginViewModelMain.insertNameGenderServiceCall(name,mActivityNameGenderBinding.email.getText().toString(),referral);
            new Analytics().sendClickData(AppConstants.SCREEN_USER_REGISTRATION, AppConstants.CLICK_PROCEED);
        }
    }

    @Override
    public void genderSuccess(String strMessage) {
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
        //mLoginViewModelMain.fetchUserDetails();
        Intent intent = MainActivity.newIntent(RegistrationActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void genderFailure(String strMessage) {
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void regionListLoaded(List<RegionSearchModel.Result> regionList) {

       /* regionListAdapter = new RegionListAdapter(this, android.R.layout.simple_list_item_1);
        mActivityNameGenderBinding.region.setThreshold(1);
        mActivityNameGenderBinding.region.setAdapter(regionListAdapter);
        regionListAdapter.setData(regionList);*/

    }

    @Override
    public int getBindingVariable() {
        return BR.nameGenderViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_name_gender;
    }

    @Override
    public RegistrationViewModel getViewModel() {
        return mLoginViewModelMain;
    }

    @Override
    public void onFragmentDetached(String tag) {

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNameGenderBinding = getViewDataBinding();
        mLoginViewModelMain.setNavigator(this);

        analytics=new Analytics(this, pageName);

    }

    @Override
    public void onBackPressed() {
       new Analytics().sendClickData(AppConstants.SCREEN_USER_REGISTRATION, AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = SignUpActivity.newIntent(RegistrationActivity.this);
        startActivity(intent);
        finish();
        return true;
    }

    private boolean validForProceed() {
        if (mActivityNameGenderBinding.edtName.getText().toString().equals("") || mActivityNameGenderBinding.email.getText().toString().equals("")) {

            if ((mActivityNameGenderBinding.edtName.getText().toString().equals(""))) {
                mActivityNameGenderBinding.inputName.setError("Enter your name");

            }

            if (!EMAIL_ADDRESS_PATTERN.matcher(mActivityNameGenderBinding.email.getText().toString()).matches()) {
                mActivityNameGenderBinding.emailInput.setError("Enter valid email");
            }

            return false;
        }

        return true;
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
        ConnectivityManager manager = (ConnectivityManager) MvvmApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) MvvmApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

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
}
