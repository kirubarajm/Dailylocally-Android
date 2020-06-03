package com.dailylocally.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentSearchBinding;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.ui.signup.SignUpActivity;
import com.dailylocally.ui.signup.fagsandsupport.FaqsAndSupportActivity;
import com.dailylocally.utilities.AppConstants;

import javax.inject.Inject;

public class SearchFragment extends BaseFragment<FragmentSearchBinding, SearchViewModel> implements
        SearchNavigator, SearchSuggestionAdapter.LiveProductsAdapterListener,SearchProductListAdapter.ProductsAdapterListener {

    @Inject
    SearchViewModel mSearchViewModel;
    @Inject
    SearchSuggestionAdapter adapter;
    @Inject
    SearchProductListAdapter productListAdapter;
    FragmentSearchBinding mFragmentSearchBinding;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.searchViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public SearchViewModel getViewModel() {
        return mSearchViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void suggestionProductSuccess() {
        mFragmentSearchBinding.recyclerviewSearchSuggestion.setVisibility(View.GONE);
        mFragmentSearchBinding.recyclerviewProduct.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchViewModel.setNavigator(this);
        adapter.setListener(this);
        productListAdapter.setListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentSearchBinding = getViewDataBinding();

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager mLayoutManagerProduct
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentSearchBinding.recyclerviewSearchSuggestion.setLayoutManager(mLayoutManager);
        mFragmentSearchBinding.recyclerviewSearchSuggestion.setAdapter(adapter);

        mLayoutManagerProduct.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentSearchBinding.recyclerviewProduct.setLayoutManager(mLayoutManagerProduct);
        mFragmentSearchBinding.recyclerviewProduct.setAdapter(productListAdapter);

        mFragmentSearchBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (s.length()>1){
                    mSearchViewModel.quickSearch();
                }

                return true;
            }

        });

        subscribeToLiveData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void subscribeToLiveData() {
        mSearchViewModel.getSearchItemsLiveData().observe(this,
                catregoryItemViewModel -> mSearchViewModel.addSearchItemsToList(catregoryItemViewModel));

        mSearchViewModel.getSearchProductItemsLiveData().observe(this,
                catregoryItemViewModel -> mSearchViewModel.addSearchProductItemsToList(catregoryItemViewModel));
    }

    @Override
    public void onSuggestionItemClickData(QuickSearchResponse.Datum result) {
        try {
            if (result!=null){
                int type = Integer.parseInt(result.getType());
                if (type== AppConstants.SEARCH_CATEGORY){
                    Intent intent = CategoryL1Activity.newIntent(getContext());
                    startActivity(intent);
                } else if (type== AppConstants.SEARCH_L1_CATEGORY){
                    Intent intent = CategoryL2Activity.newIntent(getContext());
                    startActivity(intent);
                } else if (type== AppConstants.SEARCH_L2_CATEGORY){
                    Intent intent = CategoryL2Activity.newIntent(getContext());
                    startActivity(intent);
                } else if (type== AppConstants.SEARCH_PRODUCT){
                    mSearchViewModel.SearchProduct();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() {

    }

    @Override
    public void onProductItemClick(SearchProductResponse.Product products) {
        Intent intent = CategoryL2Activity.newIntent(getContext());
        startActivity(intent);
    }
}
