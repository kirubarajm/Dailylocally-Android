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
import com.dailylocally.databinding.ActivityRegistrationBinding;
import com.dailylocally.ui.address.addAddress.AddressNewActivity;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.signup.SignUpActivity;
import com.dailylocally.ui.signup.privacy.PrivacyActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;

import java.util.Objects;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class RegistrationActivity extends BaseActivity<ActivityRegistrationBinding, RegistrationViewModel>
        implements RegistrationNavigator {

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );
    @Inject
    RegistrationViewModel mLoginViewModelMain;
    int gender;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_USER_REGISTRATION;
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent = InternetErrorFragment.newIntent(DailylocallyApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
            }
        }
    };
    private ActivityRegistrationBinding mActivityRegistrationBinding;

    public static Intent newIntent(Context context,String ToPage,String fromPage) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);
        return intent;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void proceedClick() {
        String name = mActivityRegistrationBinding.edtName.getText().toString();
        String emailText = mActivityRegistrationBinding.email.getText().toString();
        String referral = mActivityRegistrationBinding.referral.getText().toString();

        String email = "";

        if (emailText.length() > 0 && emailText.contains(" ")) {
            email = emailText.replaceAll(" ", "");
            mActivityRegistrationBinding.email.setText(email);
        }

        if (validForProceed()) {
            mLoginViewModelMain.insertNameGenderServiceCall(name, mActivityRegistrationBinding.email.getText().toString(), referral);
        }
    }

    @Override
    public void genderSuccess(String strMessage, Boolean flagEdit) {
        if (!flagEdit) {
            Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();

            //Intent inIntent = AddressNewActivity.newIntent(RegistrationActivity.this);
            Intent intents = new Intent();
            Intent inIntent = AddressNewActivity.newIntent(this,intents.getExtras().getString(AppConstants.FROM),AppConstants.SCREEN_EDIT_ADDRESS);
            inIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            inIntent.putExtra("newuser", true);
            startActivity(inIntent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


        } else {
            Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void genderFailure(String strMessage) {
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getBindingVariable() {
        return BR.nameGenderViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_registration;
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
        mActivityRegistrationBinding = getViewDataBinding();
        mLoginViewModelMain.setNavigator(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String edit = bundle.getString("edit");
            assert edit != null;
            if (edit.equals("1")) {
                mLoginViewModelMain.flagEdit.set(true);
                mActivityRegistrationBinding.btnSignIn.setText("Apply changes");
                String name = mLoginViewModelMain.getDataManager().getCurrentUserName();
                String email = mLoginViewModelMain.getDataManager().getCurrentUserEmail();
                mActivityRegistrationBinding.edtName.setText(name);
                mActivityRegistrationBinding.email.setText(email);
                mActivityRegistrationBinding.txtNameAvator.setText("Edit Info");
                mLoginViewModelMain.flagReferral.set(true);
            }
        }
        analytics = new Analytics(this, pageName);

        Intent intent = getIntent();
        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_REGISTRATION);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = SignUpActivity.newIntent(RegistrationActivity.this,AppConstants.SCREEN_NAME_REGISTRATION,AppConstants.SCREEN_NAME_SIGN_UP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        return true;
    }

    private boolean validForProceed() {
        if (mActivityRegistrationBinding.edtName.getText().toString().trim().isEmpty() || mActivityRegistrationBinding.email.getText().toString().isEmpty()) {

            if ((mActivityRegistrationBinding.edtName.getText().toString().trim().isEmpty())) {
                mActivityRegistrationBinding.inputName.setError("Enter your name");

            }

            if (!EMAIL_ADDRESS_PATTERN.matcher(mActivityRegistrationBinding.email.getText().toString()).matches()) {
                mActivityRegistrationBinding.emailInput.setError("Enter valid email");
            }

            return false;
        }
        if (mLoginViewModelMain.referral.get()) {
            if (mActivityRegistrationBinding.referral.getText().toString().trim().isEmpty()) {
                mActivityRegistrationBinding.tlReferral.setError("Enter referral code");
                return false;
            }
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
}
