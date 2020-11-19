package com.dailylocally.ui.category.viewall.products;

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

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentCatproductsBinding;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.category.l2.products.ProductListAdapter;
import com.dailylocally.ui.category.l2.products.ProductsNavigator;
import com.dailylocally.ui.category.l2.products.ProductsResponse;
import com.dailylocally.ui.category.viewall.CatProductActivity;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.ui.subscription.SubscriptionActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.google.gson.Gson;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class CatProductFragment extends BaseFragment<FragmentCatproductsBinding, CatProductFragViewModel> implements ProductsNavigator, ProductListAdapter.ProductsAdapterListener {

    @Inject
    CatProductFragViewModel mCatProductFragViewModel;
    @Inject
    ProductListAdapter productListAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;

    int subsPosition = 0;
    int pastVisiblesItems, visibleItemCount, totalItemCount, firstVisibleItem, previousTotal;
    boolean loading;
    FragmentCatproductsBinding mFragmentProductsBinding;

    public static CatProductFragment newInstance(String catid, String scl1id,String fromPage, String toPage) {
        Bundle args = new Bundle();
        args.putString("catid", catid);
        args.putString("scl1id", scl1id);
        args.putString(AppConstants.FROM, fromPage);
        args.putString(AppConstants.PAGE, toPage);
        CatProductFragment fragment = new CatProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.productsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_catproducts;
    }

    @Override
    public CatProductFragViewModel getViewModel() {
        return mCatProductFragViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void openFilter() {


        Gson gson = new Gson();
        String request = gson.toJson(mCatProductFragViewModel.productsRequest);

        ((CatProductActivity) getActivity()).openFilter(mCatProductFragViewModel.catid, mCatProductFragViewModel.scl1id);

    }

    @Override
    public void moreDataLoaded() {
        stopLoader();
        productListAdapter.loadingFalse();
    }

    @Override
    public void openSort() {
        ((CatProductActivity) getActivity()).openSort(mCatProductFragViewModel.scl1id);
    }

    @Override
    public void dataLoaded() {
        stopLoader();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCatProductFragViewModel.setNavigator(this);
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

        mCatProductFragViewModel.catid = getArguments().getString("catid", null);
        mCatProductFragViewModel.scl1id = getArguments().getString("scl1id", null);

        mCatProductFragViewModel.title.set(String.valueOf(mCatProductFragViewModel.catid));
       // startLoader();

      //  mCatProductFragViewModel.fetchProducts();


        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentProductsBinding.productList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentProductsBinding.productList.setAdapter(productListAdapter);

        mFragmentProductsBinding.productList.setNestedScrollingEnabled(true);


        Bundle intent = getArguments();
        assert intent != null;
        new Analytics().eventPageOpens(getContext(), intent.getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_SUB_CATEGORY_LI_LIST);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCatProductFragViewModel.updateAddressTitle();
    }

    private void subscribeToLiveData() {
        mCatProductFragViewModel.getProductsListLiveData().observe(this,
                productsItemViewModel -> mCatProductFragViewModel.addCategoryToList(productsItemViewModel));
    }

    @Override
    public void refresh() {
        ((CatProductActivity) getActivity()).refreshCart();
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void addOrRemoveQuantity(String name, String action, String category, String l1, String l2, String cost, int quantity, String tag) {

        new Analytics().eventAddButton(getContext(),name,action,category,l1,l2,cost,quantity,tag,mCatProductFragViewModel.totalCart(),"listing_page");

    }
    @Override
    public void subscribeProduct(ProductsResponse.Result products, int position) {

        Intent intent = SubscriptionActivity.newIntent(getContext(),AppConstants.SCREEN_NAME_SUB_CATEGORY_LI_LIST,AppConstants.SCREEN_NAME_SUBSCRIPTION);
        intent.putExtra("pid", String.valueOf(products.getPid()));
        startActivityForResult(intent, AppConstants.SUBSCRIPTION_CODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        subsPosition = position;
    }

    @Override
    public void productItemClick(ProductsResponse.Result products, int position) {

        Intent intent = ProductDetailsActivity.newIntent(getContext(),AppConstants.SCREEN_NAME_CATEGORY_PAGE,AppConstants.SCREEN_NAME_PRODUCT_DETAIL);
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
                //   productListAdapter.refreshPosition(subsPosition);
                productListAdapter.refreshItem(data.getStringExtra("pid"));
                //  productListAdapter.notifyDataSetChanged();
                String sl2 = mCatProductFragViewModel.catid;


            }

        } else if (requestCode == AppConstants.REFRESH_CODE) {
            if (resultCode == RESULT_OK) {
               /* mCatProductFragViewModel.productsList.clear();
                subscribeToLiveData();*/
                startLoader();
                mCatProductFragViewModel.fetchProducts();
            }
        } else if (requestCode == 1111) {
            if (resultCode == RESULT_OK) {
                startLoader();
                mCatProductFragViewModel.checkScl2Filter(data.getStringExtra("scl1id"));
            }
        }
    }


}
