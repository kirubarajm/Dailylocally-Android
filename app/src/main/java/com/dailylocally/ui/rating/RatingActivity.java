package com.dailylocally.ui.rating;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityRatingBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.calendarView.CalendarDayWiseResponse;
import com.dailylocally.ui.fandsupport.help.HelpActivity;
import com.dailylocally.utilities.AppConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;


public class RatingActivity extends BaseActivity<ActivityRatingBinding, RatingViewModel> implements
        RatingNavigator, RatingDayWiseAdapter.CategoriesAdapterListener {


    public ActivityRatingBinding mActivityRatingBinding;
    @Inject
    public RatingViewModel mRatingViewModel;
    @Inject
    public RatingDayWiseAdapter mRatingProductAdapter;
    Date date = null;
    int rateDelivery = 0, rateProduct = 0, productReceived,packageSealed;
    List<CalendarDayWiseResponse.Result.Item> productList;
    ArrayList<Integer> productIdList;
    String doid = "";

    public static Intent newIntent(Context context) {
        return new Intent(context, RatingActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.ratingViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_rating;
    }

    @Override
    public RatingViewModel getViewModel() {

        return mRatingViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void helpClick() {
        Intent intent = HelpActivity.newIntent(RatingActivity.this, AppConstants.NOTIFY_SUPPORT_ACTV,AppConstants.CHAT_PAGE_TYPE_COMPLETED_ORDER,doid);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void getProductList(List<CalendarDayWiseResponse.Result.Item> resultList) {
        this.productList = new ArrayList<>();
        this.productList = resultList;
    }

    @Override
    public void submit() {
        try {
        if (validation()){
            String comments = mActivityRatingBinding.edtComments.getText().toString();
            if (mActivityRatingBinding.chkPackageSealedYes.isChecked()){
                packageSealed = 1;
            }else {
                packageSealed = 0;
            }
            if (mActivityRatingBinding.chkProductYes.isChecked()){
                productReceived = 1;
            }else {
                productReceived = 0;
            }
            mRatingViewModel.ratingAPICall(rateProduct,rateDelivery,productReceived,Integer.parseInt(doid),productIdList,comments,packageSealed);
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void ratingSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        finish();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("date",date);
        setResult(Activity.RESULT_OK, returnIntent);
    }

    @Override
    public void ratingFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRatingBinding = getViewDataBinding();
        mRatingViewModel.setNavigator(this);
        mRatingProductAdapter.setListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
             date = new Date(bundle.getLong("date"));
             doid = bundle.getString("doid");
             mRatingViewModel.orderId.set("Order #"+doid +" | ");
        }

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityRatingBinding.recyclerProduct.setLayoutManager(mLayoutManager);
        mActivityRatingBinding.recyclerProduct.setAdapter(mRatingProductAdapter);
        subscribeToLiveData();

        mActivityRatingBinding.relProductDelivery.setVisibility(View.GONE);

        mActivityRatingBinding.chkPackageSealedNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mActivityRatingBinding.chkPackageSealedNo.isChecked()){
                    mActivityRatingBinding.chkPackageSealedYes.setChecked(false);
                }
            }
        });

        mActivityRatingBinding.chkPackageSealedYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mActivityRatingBinding.chkPackageSealedYes.isChecked()){
                    mActivityRatingBinding.chkPackageSealedNo.setChecked(false);
                }
            }
        });

        mActivityRatingBinding.chkProductNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mActivityRatingBinding.chkProductNo.isChecked()){
                    mActivityRatingBinding.chkProductYes.setChecked(false);
                    mActivityRatingBinding.relProductDelivery.setVisibility(View.VISIBLE);
                }
            }
        });

        mActivityRatingBinding.chkProductYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mActivityRatingBinding.chkProductYes.isChecked()){
                    mActivityRatingBinding.chkProductNo.setChecked(false);
                    mActivityRatingBinding.relProductDelivery.setVisibility(View.GONE);
                }
            }
        });

    }

    private void subscribeToLiveData() {
        mRatingViewModel.getOrdernowLiveData().observe(this,
                ordernowItemViewModel -> mRatingViewModel.addOrderNowItemsToList(ordernowItemViewModel));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRatingViewModel.getDayWiseOrderDetails(date);
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

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public void canceled() {

    }

    @Override
    public void onItemClick(CalendarDayWiseResponse.Result.Item result) {

        if (productList!=null){
        for (int i=0;i<productList.size();i++){
            if (result.getDayorderpid().equals(productList.get(i).getDayorderpid())){
                if (productList.get(i).getTrueOrFalse()==null){
                    productList.get(i).setTrueOrFalse(true);
                }else {
                    if (productList.get(i).getTrueOrFalse()){
                        productList.get(i).setTrueOrFalse(false);
                    }else {
                        productList.get(i).setTrueOrFalse(true);
                    }
                }
            }
        }

            productIdList = new ArrayList<>();
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getTrueOrFalse()!=null && productList.get(i).getTrueOrFalse()) {
                    productIdList.add(productList.get(i).getDayorderpid());
                }
            }
        }

    }

    public boolean validation(){
        rateDelivery = (int) mActivityRatingBinding.ratingBarDelivery.getRating();
        rateProduct = (int) mActivityRatingBinding.ratingBarProduct.getRating();
        if (rateDelivery == 0) {
            Toast.makeText(getApplicationContext(), "Please give delivery rating", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!mActivityRatingBinding.chkPackageSealedYes.isChecked() && !mActivityRatingBinding.chkPackageSealedNo.isChecked()){
            Toast.makeText(getApplicationContext(), "Please check package sealed", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (rateProduct == 0) {
            Toast.makeText(getApplicationContext(), "Please give Product rating", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!mActivityRatingBinding.chkProductYes.isChecked() && !mActivityRatingBinding.chkProductNo.isChecked()){
            Toast.makeText(getApplicationContext(), "Please check receive all products", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(mActivityRatingBinding.chkProductNo.isChecked()){
            if(productIdList==null || productIdList.size()==0){
                Toast.makeText(getApplicationContext(), "Please check at least one product", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        /*if (mActivityRatingBinding.edtComments.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please give comment", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        return true;
    }

}