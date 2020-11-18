package com.dailylocally.ui.signup.opt;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityOtpBinding;
import com.dailylocally.ui.address.addAddress.AddressNewActivity;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.fandsupport.support.SupportActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.signup.SignUpActivity;
import com.dailylocally.ui.signup.registration.RegistrationActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.OtpEditText;
import com.dailylocally.utilities.SMSReceiver;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Objects;

import javax.inject.Inject;

public class OtpActivity extends BaseActivity<ActivityOtpBinding, OtpActivityViewModel> implements OtpActivityNavigator, SMSReceiver.OTPReceiveListener {

    @Inject
    OtpActivityViewModel mLoginViewModelMain;
    String strPhoneNumber = "";
    String strOtpId = "";
    String strOtp = "0";
    String UserId = "";

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
    private ActivityOtpBinding mActivityOtpBinding;
    private EditText[] editTexts;
    private SMSReceiver smsReceiver;

    public static Intent newIntent(Context context,String ToPage,String fromPage) {
        Intent intent = new Intent(context, OtpActivity.class);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);
        return intent;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    private void startSMSListener() {
        try {
            smsReceiver = new SMSReceiver();
            smsReceiver.setOTPListener(this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            this.registerReceiver(smsReceiver, intentFilter);

            SmsRetrieverClient client = SmsRetriever.getClient(this);

            Task<Void> task = client.startSmsRetriever();
            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // API successfully started
                }
            });

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Fail to start API
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void continueClick() {

        if (mActivityOtpBinding.otpText.getText() != null && mActivityOtpBinding.otpText.getText().length() == 5) {
            if (strOtp != null)
                mLoginViewModelMain.userContinueClick(strPhoneNumber, Integer.parseInt(strOtp));
        }
    }

    @Override
    public void openHomeActivity(boolean trueOrFalse) {


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //   Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        mLoginViewModelMain.saveToken(token);
                    }
                });

        if (trueOrFalse) {

            unregisterReceiver(smsReceiver);

            Toast.makeText(getApplicationContext(), AppConstants.TOAST_LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
            Intent intent = MainActivity.newIntent(OtpActivity.this, AppConstants.NOTIFY_HOME_FRAG, AppConstants.NOTIFY_OTP_ACTV,AppConstants.SCREEN_NAME_OTP,AppConstants.SCREEN_NAME_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_LOGIN_FAILED, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addAddressActivity(String aid) {
        Intent intents = new Intent();
        Intent intent = AddressNewActivity.newIntent(this,intents.getExtras().getString(AppConstants.FROM),AppConstants.SCREEN_EDIT_ADDRESS);
        intent.putExtra("aid", aid);
        intent.putExtra("newuser", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void nameGenderScreen() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //   Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        mLoginViewModelMain.saveToken(token);


                    }
                });

        unregisterReceiver(smsReceiver);

        Intent intent = RegistrationActivity.newIntent(OtpActivity.this,AppConstants.SCREEN_NAME_OTP,AppConstants.SCREEN_NAME_REGISTRATION);
        intent.putExtra("edit","0");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void login() {

    }

    @Override
    public void forgotPassword() {
        mActivityOtpBinding.edtPassword.setText("");
    }

    @Override
    public void loginSuccess() {


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //   Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        mLoginViewModelMain.saveToken(token);


                    }
                });

        Toast.makeText(getApplicationContext(), AppConstants.TOAST_LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
        Intent intent = MainActivity.newIntent(OtpActivity.this, AppConstants.NOTIFY_HOME_FRAG, AppConstants.NOTIFY_OTP_ACTV,AppConstants.SCREEN_NAME_OTP,AppConstants.SCREEN_NAME_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void loginFailure() {
        Toast.makeText(getApplicationContext(), AppConstants.TOAST_LOGIN_FAILED, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void resend() {

    }

    @Override
    public void addNewAddress() {
        Intent intents = new Intent();
        Intent intent = AddressNewActivity.newIntent(this,intents.getExtras().getString(AppConstants.FROM),AppConstants.SCREEN_EDIT_ADDRESS);
        intent.putExtra("newuser", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public int getBindingVariable() {
        return BR.otpViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_otp;
    }

    @Override
    public OtpActivityViewModel getViewModel() {
        return mLoginViewModelMain;
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void startTimer() {


        mActivityOtpBinding.resend.setVisibility(View.GONE);
        mActivityOtpBinding.timer.setVisibility(View.VISIBLE);

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                mLoginViewModelMain.timer.set(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                mActivityOtpBinding.timer.setVisibility(View.GONE);
                mActivityOtpBinding.resend.setVisibility(View.VISIBLE);

            }

        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOtpBinding = getViewDataBinding();
        mLoginViewModelMain.setNavigator(this);
        startSMSListener();

        startTimer();




        /*AppSignatureHashHelper appSignatureHashHelper = new AppSignatureHashHelper(this);

        // This code requires one time to get Hash keys do comment and share key
        Log.e("OTP", "Apps Hash Key: " + appSignatureHashHelper.getAppSignatures().get(0));*/


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //  String booleanOtp = bundle.getString("booleanOpt");
            strOtpId = bundle.getString("optId");
            mLoginViewModelMain.OtpId = Integer.parseInt(strOtpId);

            strPhoneNumber = bundle.getString("strPhoneNumber");

            mLoginViewModelMain.number.set(strPhoneNumber);


            mLoginViewModelMain.otp.set(true);
            mLoginViewModelMain.otp.set(false);
            mLoginViewModelMain.title.set("OTP");



            mActivityOtpBinding.txtMessageSent.setText("(OTP) Sent to " + strPhoneNumber);

        }

        mActivityOtpBinding.otpText.setOnPinEnteredListener(new OtpEditText.OnPinEnteredListener() {
            @Override
            public void onPinEntered(CharSequence str) {

                strOtp = str.toString();

                mLoginViewModelMain.userContinueClick(strPhoneNumber, Integer.parseInt(str.toString()));

            }
        });

        mActivityOtpBinding.resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSMSListener();
                startTimer();
                Toast.makeText(OtpActivity.this, "OTP has been sent again.", Toast.LENGTH_SHORT).show();
                mLoginViewModelMain.resendOtp();
            }
        });

        Intent intent = getIntent();
        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_OTP);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = SignUpActivity.newIntent(OtpActivity.this,AppConstants.SCREEN_NAME_OTP,AppConstants.SCREEN_NAME_SIGN_UP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        return true;
    }

    private boolean validForOtp() {
        if (mActivityOtpBinding.edt1.getText().toString().equals("") && mActivityOtpBinding.edt2.getText().toString().equals("") && mActivityOtpBinding.edt4.getText().toString().equals("")
                && mActivityOtpBinding.edt4.getText().toString().equals("") && mActivityOtpBinding.edt5.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_OTP, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validForPassword() {
        if (mActivityOtpBinding.edtPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_PASSWORD, Toast.LENGTH_SHORT).show();
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
        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
        }
        unregisterWifiReceiver();
    }

    @Override
    public void onOTPReceived(String message) {
        // String otp=message.substring(0, 1)+message.substring(1, 2)+message.substring(2, 3)+message.substring(3, 4)+message.substring(4, 5);
        mActivityOtpBinding.otpText.setText(message);
        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
        }
    }

    @Override
    public void onOTPTimeOut() {

    }

    @Override
    public void onOTPReceivedError(String error) {

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

    public class PinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }

    }

    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;
            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                editTexts[currentIndex].clearFocus();
                hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }

        private void hideKeyboard() {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

    }


}
