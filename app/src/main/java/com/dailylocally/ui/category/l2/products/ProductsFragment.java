package com.dailylocally.ui.category.l2.products;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentProductsBinding;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.ui.subscription.SubscriptionActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.google.gson.Gson;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class ProductsFragment extends BaseFragment<FragmentProductsBinding, ProductsViewModel> implements ProductsNavigator, ProductListAdapter.ProductsAdapterListener {

    @Inject
    ProductsViewModel mProductsViewModel;
    @Inject
    ProductListAdapter productListAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;

    int subsPosition = 0;
    int pastVisiblesItems, visibleItemCount, totalItemCount, firstVisibleItem, previousTotal;
    boolean loading;
    FragmentProductsBinding mFragmentProductsBinding;

    public static ProductsFragment newInstance(String scl2id, String scl1id,String fromPage,String toPage) {
        Bundle args = new Bundle();
        args.putString("scl2id", scl2id);
        args.putString("scl1id", scl1id);
        args.putString(AppConstants.FROM, fromPage);
        args.putString(AppConstants.PAGE, toPage);
        ProductsFragment fragment = new ProductsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.productsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_products;
    }

    @Override
    public ProductsViewModel getViewModel() {
        return mProductsViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void openFilter() {


        Gson gson = new Gson();
        String request = gson.toJson(mProductsViewModel.productsRequest);

        ((CategoryL2Activity) getActivity()).openFilter(mProductsViewModel.scl1id, mProductsViewModel.scl2id);

    }

    @Override
    public void moreDataLoaded() {
        stopLoader();
        productListAdapter.loadingFalse();
    }

    @Override
    public void openSort() {
        ((CategoryL2Activity) getActivity()).openSort(mProductsViewModel.scl2id);
    }

    @Override
    public void dataLoaded() {
        stopLoader();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductsViewModel.setNavigator(this);
        productListAdapter.setListener(this);
        subscribeToLiveData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentProductsBinding = getViewDataBinding();

        mProductsViewModel.scl2id = getArguments().getString("scl2id", null);
        mProductsViewModel.scl1id = getArguments().getString("scl1id", null);

        mProductsViewModel.title.set(String.valueOf(mProductsViewModel.scl2id));

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentProductsBinding.productList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentProductsBinding.productList.setAdapter(productListAdapter);

        mFragmentProductsBinding.productList.setNestedScrollingEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mProductsViewModel.updateAddressTitle();
    }

    private void subscribeToLiveData() {
        mProductsViewModel.getProductsListLiveData().observe(this,
                productsItemViewModel -> mProductsViewModel.addCategoryToList(productsItemViewModel));
    }

    @Override
    public void refresh() {
        ((CategoryL2Activity) getActivity()).refreshCart();
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void subscribeProduct(ProductsResponse.Result products, int position) {

        Intent intent = SubscriptionActivity.newIntent(getContext(),AppConstants.SCREEN_NAME_PRODUCTS,AppConstants.SCREEN_NAME_SUBSCRIPTION);
        intent.putExtra("pid", String.valueOf(products.getPid()));
        startActivityForResult(intent, AppConstants.SUBSCRIPTION_CODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        subsPosition = position;
    }

    @Override
    public void productItemClick(ProductsResponse.Result products, int position) {

        Intent intent = ProductDetailsActivity.newIntent(getContext(),AppConstants.SCREEN_NAME_PRODUCTS,AppConstants.SCREEN_NAME_PRODUCT_DETAIL);
        intent.putExtra("vpid", String.valueOf(products.getPid()));
        startActivityForResult(intent, AppConstants.SUBSCRIPTION_CODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        subsPosition = position;
    }

    @Override
    public void showToast(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }

    public void stopLoader() {
        mFragmentProductsBinding.pageLoader.stopShimmerAnimation();
        mFragmentProductsBinding.pageLoader.setVisibility(View.GONE);
    }

    public void startLoader() {
        mFragmentProductsBinding.pageLoader.setVisibility(View.VISIBLE);
        mFragmentProductsBinding.pageLoader.startShimmerAnimation();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.SUBSCRIPTION_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                productListAdapter.refreshItem(data.getStringExtra("pid"));
                String sl2 = mProductsViewModel.scl2id;
            }

        } else if (requestCode == AppConstants.REFRESH_CODE) {
            if (resultCode == RESULT_OK) {

                startLoader();
                mProductsViewModel.fetchProducts();

            }
        } else if (requestCode == 1111) {
            if (resultCode == RESULT_OK) {
                startLoader();
                mProductsViewModel.checkScl2Filter(data.getStringExtra("scl2id"));
            }
        }
    }


}
