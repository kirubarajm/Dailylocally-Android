package com.dailylocally.ui.collection.l2.products;

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
import com.dailylocally.databinding.FragmentCollectionProductsBinding;
import com.dailylocally.databinding.FragmentProductsBinding;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.collection.l2.CollectionDetailsActivity;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.ui.subscription.SubscriptionActivity;
import com.google.gson.Gson;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class CollectionProductFragment extends BaseFragment<FragmentCollectionProductsBinding, CollectionProductsViewModel> implements CollectionProductsNavigator, CollectionProductListAdapter.ProductsAdapterListener {

    @Inject
    CollectionProductsViewModel mCollectionProductsViewModel;
    @Inject
    CollectionProductListAdapter collectionProductListAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;


    private FragmentCollectionProductsBinding mFragmentProductsBinding;

    public static CollectionProductFragment newInstance(String id,String cid) {
        Bundle args = new Bundle();
        args.putString("scl1id", id);
        args.putString("cid", cid);
        CollectionProductFragment fragment = new CollectionProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.productsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_collection_products;
    }

    @Override
    public CollectionProductsViewModel getViewModel() {
        return mCollectionProductsViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void openFilter() {


        Gson gson = new Gson();
        String request = gson.toJson(mCollectionProductsViewModel.collectionProductsRequest);

        ((CollectionDetailsActivity) getActivity()).openFilter(mCollectionProductsViewModel.scl1id);

       /* FilterFragment filterFragment = new FilterFragment();
        filterFragment.show(getFragmentManager(), filterFragment.getTag());*/

    }

    @Override
    public void openSort() {
        ((CollectionDetailsActivity) getActivity()).openSort(mCollectionProductsViewModel.scl1id);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCollectionProductsViewModel.setNavigator(this);
        collectionProductListAdapter.setListener(this);
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

        mCollectionProductsViewModel.scl1id = getArguments().getString("scl1id", null);
        mCollectionProductsViewModel.cid = getArguments().getString("cid", null);

        mCollectionProductsViewModel.title.set(String.valueOf(mCollectionProductsViewModel.scl1id));

        mCollectionProductsViewModel.fetchProducts();


       /* mFragmentProductsBinding.productList.setLayoutManager(linearLayoutManager);
        mFragmentProductsBinding.productList.setAdapter(productListAdapter);*/


        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentProductsBinding.productList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentProductsBinding.productList.setAdapter(collectionProductListAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        mCollectionProductsViewModel.updateAddressTitle();
    }

    private void subscribeToLiveData() {
        mCollectionProductsViewModel.getProductsListLiveData().observe(this,
                productsItemViewModel -> mCollectionProductsViewModel.addCategoryToList(productsItemViewModel));
    }


    @Override
    public void refresh() {
        ((CollectionDetailsActivity) getActivity()).refreshCart();
    }

    @Override
    public void subscribeProduct(CollectionProductsResponse.Result products) {

        Intent intent = SubscriptionActivity.newIntent(getContext());
        intent.putExtra("pid", String.valueOf(products.getPid()));
        startActivity(intent);
    }

    @Override
    public void productItemClick(CollectionProductsResponse.Result products) {

        Intent intent = ProductDetailsActivity.newIntent(getContext());
        intent.putExtra("vpid",String.valueOf(products.getPid()));
        startActivity(intent);


    }

    @Override
    public void showToast(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1111)
            if (resultCode == RESULT_OK) {


                mCollectionProductsViewModel.checkScl1Filter(data.getStringExtra("scl1id"));
            }
    }
}
