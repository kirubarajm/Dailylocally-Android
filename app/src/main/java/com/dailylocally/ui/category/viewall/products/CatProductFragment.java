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
import androidx.recyclerview.widget.RecyclerView;

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

    public static CatProductFragment newInstance(String catid, String scl1id) {
        Bundle args = new Bundle();
        args.putString("catid", catid);
        args.putString("scl1id", scl1id);
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

       /* FilterFragment filterFragment = new FilterFragment();
        filterFragment.show(getFragmentManager(), filterFragment.getTag());*/

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


        /*mFragmentProductsBinding.productList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View visi = recyclerView.getChildAt(recyclerView.getChildCount() - 1);

                int lVV = recyclerView.getChildAdapterPosition(visi);
                int itemCount = productListAdapter.getItemCount();

                if (itemCount > 2) {
                    if (dy > 0) {
                        if (lVV >= itemCount - 2) {
                            if (!mCatProductFragViewModel.loadingMore&&!mCatProductFragViewModel.loading.get()) {
                              //  productListAdapter.addLoader();
                                loadMore();
                            }
                        }

                    } else {
                        // Scrolling down
                        // Toast.makeText(getContext(),"Loading more", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });*/


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

       // mCatProductFragViewModel.loadMoreProducts(productListAdapter);

    }

    @Override
    public void subscribeProduct(ProductsResponse.Result products, int position) {

        Intent intent = SubscriptionActivity.newIntent(getContext());
        intent.putExtra("pid", String.valueOf(products.getPid()));
        startActivityForResult(intent, AppConstants.SUBSCRIPTION_CODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        subsPosition = position;
    }

    @Override
    public void productItemClick(ProductsResponse.Result products, int position) {

        Intent intent = ProductDetailsActivity.newIntent(getContext());
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
