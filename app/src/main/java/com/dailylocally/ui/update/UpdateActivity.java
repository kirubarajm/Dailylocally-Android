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

import javax.inject.Inject;

public class UpdateActivity extends BaseActivity<ActivityUpdateBinding, UpdateViewModel>
        implements UpdateNavigator {

    @Inject
    UpdateViewModel mUpdateViewModel;

    private ActivityUpdateBinding mActivityUpdateBinding;
    private PrefManager prefManager;


    Analytics analytics;
    String pageName= AppConstants.SCREEN_FORCE_UPDATE;


    public static Intent newIntent(Context context) {
        return new Intent(context, UpdateActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void checkForUserLoginMode(boolean trueOrFlase) {
        if (trueOrFlase) {
            Intent intent = MainActivity.newIntent(UpdateActivity.this,AppConstants.NOTIFY_HOME_FRAG,AppConstants.NOTIFY_UPDATE_ACTV);
            startActivity(intent);
            finish();
        } else {
            Intent intent = SignUpActivity.newIntent(UpdateActivity.this);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void checkForUserGenderStatus(boolean trueOrFalse) {
        Intent intent = RegistrationActivity.newIntent(UpdateActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void update() {

        new Analytics().sendClickData(AppConstants.SCREEN_FORCE_UPDATE, AppConstants.CLICK_UPDATE);


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
        new Analytics().sendClickData(AppConstants.SCREEN_FORCE_UPDATE, AppConstants.CLICK_BACK_BUTTON);
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



        analytics=new Analytics(this,pageName);
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    public void canceled() {

    }
}
