package com.dailylocally.ui.productDetail.productCancel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityProductCancelBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.category.l2.products.filter.FilterFragment;
import com.dailylocally.ui.productDetail.dialogProductCancel.DialogProductCancel;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class ProductCancelActivity extends BaseActivity<ActivityProductCancelBinding, ProductCancelViewModel> implements
        ProductCancelNavigator, HasSupportFragmentInjector {


    public ActivityProductCancelBinding mActivityProductCancelBinding;
    @Inject
    public ProductCancelViewModel mAddAddressViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;


    Analytics analytics;
    String pageName = AppConstants.SCREEN_ADD_ADDRESS;

    public static Intent newIntent(Context context) {
        return new Intent(context, ProductCancelActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.productCancelViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_cancel;
    }

    @Override
    public ProductCancelViewModel getViewModel() {

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
    public void cancelProductClick() {
        Bundle bundle=new Bundle();
        bundle.putString("doid","1");
        bundle.putString("vpid","1");

        DialogProductCancel filterFragment = new DialogProductCancel();
        filterFragment.setArguments(bundle);
        filterFragment.show(getSupportFragmentManager(), filterFragment.getTag());
    }

    @Override
    public void success(int isCancelable,String date) {
        try {
            SimpleDateFormat dateDayFormat = new SimpleDateFormat("EEE, MMM dd, YYYY");
            String datesdf = dateDayFormat.format(date);

            mAddAddressViewModel.productDate.set(datesdf);
            if (isCancelable==0){
                mAddAddressViewModel.isCancel.set(true);
            }else {
                mAddAddressViewModel.isCancel.set(false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityProductCancelBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);

        analytics = new Analytics(this, pageName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAddAddressViewModel.getProductCancelDetails("","");
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

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}