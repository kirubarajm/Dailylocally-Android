package com.dailylocally.ui.category.l2.products;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentProductsBinding;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.ui.subscription.SubscriptionActivity;

import javax.inject.Inject;

public class ProductsFragment extends BaseFragment<FragmentProductsBinding, ProductsViewModel> implements ProductsNavigator, ProductListAdapter.ProductsAdapterListener {

    @Inject
    ProductsViewModel mProductsViewModel;
    @Inject
    ProductListAdapter productListAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;


    FragmentProductsBinding mFragmentProductsBinding;

    public static ProductsFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("scl2id", id);
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

        int scl2id = getArguments().getInt("scl2id", 0);

        mProductsViewModel.title.set(String.valueOf(scl2id));

        mProductsViewModel.fetchProducts(scl2id);


       /* mFragmentProductsBinding.productList.setLayoutManager(linearLayoutManager);
        mFragmentProductsBinding.productList.setAdapter(productListAdapter);*/


        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentProductsBinding.productList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentProductsBinding.productList.setAdapter(productListAdapter);


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
    public void subscribeProduct(ProductsResponse.Result products) {

        Intent intent = SubscriptionActivity.newIntent(getContext());
        intent.putExtra("pid",String.valueOf(products.getPid()));
        startActivity(intent);
    }
}
