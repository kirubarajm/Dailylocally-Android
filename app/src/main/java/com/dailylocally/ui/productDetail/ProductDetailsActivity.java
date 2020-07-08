package com.dailylocally.ui.productDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityProductDetailsBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import javax.inject.Inject;


public class ProductDetailsActivity extends BaseActivity<ActivityProductDetailsBinding, ProductDetailsViewModel> implements
        ProductDetailsNavigator {


    public ActivityProductDetailsBinding mActivityProductDetailsBinding;
    @Inject
    public ProductDetailsViewModel mAddAddressViewModel;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_ADD_ADDRESS;

    public static Intent newIntent(Context context) {
        return new Intent(context, ProductDetailsActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.productDetailsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_details;
    }

    @Override
    public ProductDetailsViewModel getViewModel() {

        return mAddAddressViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void productsDetailsSuccess(ProductDetailsResponse.Result result) {
        mAddAddressViewModel.mrp.set(""+result.getMrp());
        mAddAddressViewModel.offerCost.set(result.getMrp() + " OFF on " + result.getProductname());
    }

    @Override
    public void viewCart() {
        Intent intent = MainActivity.newIntent(ProductDetailsActivity.this,AppConstants.NOTIFY_CART_FRAG,AppConstants.NOTIFY_PRODUCT_DETAILS_ACTV);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityProductDetailsBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);

        analytics = new Analytics(this, pageName);


        mAddAddressViewModel.getProductDetails(getIntent().getExtras().getString("vpid"));

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void canceled() {

    }
}