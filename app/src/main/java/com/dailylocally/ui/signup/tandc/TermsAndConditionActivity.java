package com.dailylocally.ui.signup.tandc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityTermsAndConditionBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

import javax.inject.Inject;

public class TermsAndConditionActivity extends BaseActivity<ActivityTermsAndConditionBinding, TermsAndConditionViewModel> implements TermsAndConditionNavigator {

    @Inject
    TermsAndConditionViewModel mTermsAndConditionModel;
    ActivityTermsAndConditionBinding mActivityTermsAndConditionBinding;


    Analytics analytics;
    String pageName= AppConstants.SCREEN_TERMS_CONDITION;


    ProgressDialog pd;

    public static Intent newIntent(Context context) {
        return new Intent(context, TermsAndConditionActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.termsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_terms_and_condition;
    }

    @Override
    public TermsAndConditionViewModel getViewModel() {
        return mTermsAndConditionModel;
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    public void handleError(Throwable throwable) {

    }


    @Override
    public void goBack() {
      onBackPressed();
    }



    @Override
    public void accept() {
        new Analytics().sendClickData(AppConstants.SCREEN_OTP, AppConstants.CLICK_ACCEPT_AND_CONTINUE);
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityTermsAndConditionBinding = getViewDataBinding();
        mTermsAndConditionModel.setNavigator(this);



        analytics=new Analytics(this,pageName);

        mActivityTermsAndConditionBinding.webview.getSettings().setJavaScriptEnabled(true);

        pd = new ProgressDialog(TermsAndConditionActivity.this);
        pd.setMessage("Please wait Loading...");
        pd.show();
        mActivityTermsAndConditionBinding.webview.setWebViewClient(new MyWebViewClient());

        mActivityTermsAndConditionBinding.webview.getSettings().setJavaScriptEnabled(true);

    }

    @Override
    public void onBackPressed() {

        new Analytics().sendClickData(AppConstants.SCREEN_TERMS_CONDITION, AppConstants.CLICK_BACK_BUTTON);

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivityTermsAndConditionBinding.webview.loadUrl("http://www.dailylocally.co.in/dl_terms.html");
    }

    @Override
    public void canceled() {

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            if (!pd.isShowing()) {
                pd.show();
            }

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (pd.isShowing()) {
                pd.dismiss();
            }

        }
    }
}
