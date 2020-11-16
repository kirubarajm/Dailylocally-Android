package com.dailylocally.ui.update;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import com.dailylocally.BR;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityUpdateBinding;
import com.dailylocally.ui.base.BaseActivity;

import com.dailylocally.ui.onboarding.PrefManager;
import com.dailylocally.ui.signup.SignUpActivity;

import com.dailylocally.ui.signup.registration.RegistrationActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

import java.util.Objects;

import javax.inject.Inject;

public class UpdateActivity extends BaseActivity<ActivityUpdateBinding, UpdateViewModel>
        implements UpdateNavigator {

    @Inject
    UpdateViewModel mUpdateViewModel;

    private ActivityUpdateBinding mActivityUpdateBinding;
    private PrefManager prefManager;

    public static Intent newIntent(Context context,String ToPage,String fromPage) {
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra(AppConstants.FROM, fromPage);
        return intent;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void checkForUserLoginMode(boolean trueOrFlase) {
        if (trueOrFlase) {
            Intent intent = MainActivity.newIntent(UpdateActivity.this,AppConstants.NOTIFY_HOME_FRAG,AppConstants.NOTIFY_UPDATE_ACTV);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            Intent intent = SignUpActivity.newIntent(UpdateActivity.this,AppConstants.SCREEN_NAME_UPDATE,AppConstants.SCREEN_NAME_SIGN_UP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void checkForUserGenderStatus(boolean trueOrFalse) {
        Intent intent = RegistrationActivity.newIntent(UpdateActivity.this,AppConstants.SCREEN_NAME_UPDATE,AppConstants.SCREEN_NAME_REGISTRATION);
        intent.putExtra("edit","0");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void update() {

        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
        finish();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public int getBindingVariable() {
        return BR.updateViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_update;
    }

    @Override
    public UpdateViewModel getViewModel() {
        return mUpdateViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityUpdateBinding = getViewDataBinding();
        mUpdateViewModel.setNavigator(this);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {

            mUpdateViewModel.forceUpdate.set(intent.getExtras().getBoolean("forceUpdate"));

        }

        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_UPDATE);
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    public void canceled() {

    }
}
