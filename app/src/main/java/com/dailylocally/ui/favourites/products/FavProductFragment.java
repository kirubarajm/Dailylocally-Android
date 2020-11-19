package com.dailylocally.ui.favourites.products;

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
import com.dailylocally.databinding.FragmentFavProductsBinding;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.favourites.FavActivity;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.ui.subscription.SubscriptionActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class FavProductFragment extends BaseFragment<FragmentFavProductsBinding, FavProductsViewModel> implements FavProductsNavigator, FavProductListAdapter.ProductsAdapterListener {

    @Inject
    FavProductsViewModel mFavProductsViewModel;
    @Inject
    FavProductListAdapter favProductListAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;


    private FragmentFavProductsBinding mFragmentProductsBinding;

    public static FavProductFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("catid", id);
        FavProductFragment fragment = new FavProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.productsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fav_products;
    }

    @Override
    public FavProductsViewModel getViewModel() {
        return mFavProductsViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void openFilter() {



    }

    @Override
    public void openSort() {
        ((FavActivity) getActivity()).openSort(mFavProductsViewModel.catid);
    }

    @Override
    public void DataLoaded(FavProductsResponse response) {
        if (response != null) {
            if (response.getResult() != null && response.getResult().size() > 0) {
                ((FavActivity)getActivity()).emptyFav(false);
            } else {
                ((FavActivity)getActivity()).emptyFav(true);
            }
        } else {
            ((FavActivity)getActivity()).emptyFav(false);
        }
stopLoader();

    }

    @Override
    public void dataLoaded() {

        stopLoader();

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavProductsViewModel.setNavigator(this);
        favProductListAdapter.setListener(this);


    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentProductsBinding = getViewDataBinding();

        subscribeToLiveData();
        mFavProductsViewModel.catid = getArguments().getString("catid", "0");

        mFavProductsViewModel.title.set(String.valueOf(mFavProductsViewModel.catid));
        startLoader();
        mFavProductsViewModel.fetchProducts(getArguments().getString("catid", "0"));

       /* mFragmentProductsBinding.productList.setLayoutManager(linearLayoutManager);
        mFragmentProductsBinding.productList.setAdapter(productListAdapter);*/


        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentProductsBinding.productList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentProductsBinding.productList.setAdapter(favProductListAdapter);

        /*Bundle intent = getArguments();
        assert intent != null;
        new Analytics().eventPageOpens(getContext(), intent.getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_FAVORITES);*/

    }

    @Override
    public void onResume() {
        super.onResume();
        mFavProductsViewModel.updateAddressTitle();
    }

    private void subscribeToLiveData() {
        mFavProductsViewModel.getProductsListLiveData().observe(this,
                productsItemViewModel -> mFavProductsViewModel.addCategoryToList(productsItemViewModel));
    }


    @Override
    public void refresh() {
        ((FavActivity) getActivity()).refreshCart();
    }

    @Override
    public void subscribeProduct(FavProductsResponse.Result products) {

        Intent intent = SubscriptionActivity.newIntent(getContext(),AppConstants.SCREEN_NAME_FAVORITES,AppConstants.SCREEN_NAME_SUBSCRIPTION);
        intent.putExtra("pid", String.valueOf(products.getPid()));
        startActivityForResult(intent,AppConstants.SUBSCRIPTION_CODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void productItemClick(FavProductsResponse.Result products) {

        Intent intent = ProductDetailsActivity.newIntent(getContext(),AppConstants.SCREEN_NAME_FAVORITES,AppConstants.SCREEN_NAME_PRODUCT_DETAIL);
        intent.putExtra("vpid",String.valueOf(products.getPid()));
        startActivityForResult(intent, AppConstants.SUBSCRIPTION_CODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void reloadProducts() {
        startLoader();
        mFavProductsViewModel.fetchProducts(mFavProductsViewModel.catid);
    }

    @Override
    public void showToast(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.SUBSCRIPTION_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //   productListAdapter.refreshPosition(subsPosition);
                favProductListAdapter.refreshItem(data.getStringExtra("pid"));
                //  productListAdapter.notifyDataSetChanged();


            }

        }else  if (requestCode == AppConstants.REFRESH_CODE) {
            if (resultCode == RESULT_OK) {
                mFavProductsViewModel.productsList.clear();
                subscribeToLiveData();

            }
        }else  if (requestCode == 1111) {
            if (resultCode == RESULT_OK) {
                startLoader();
                mFavProductsViewModel.checkScl1Filter(data.getStringExtra("catid"));
            }
        }
    }



}
