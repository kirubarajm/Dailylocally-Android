package com.dailylocally.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentSearchBinding;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

import javax.inject.Inject;

public class SearchFragment extends BaseFragment<FragmentSearchBinding, SearchViewModel> implements
        SearchNavigator, SearchSuggestionAdapter.LiveProductsAdapterListener, SearchProductListAdapter.ProductsAdapterListener,
        SearchSubCategoryAdapter.LiveProductsAdapterListener {

    @Inject
    SearchViewModel mSearchViewModel;
    @Inject
    SearchSuggestionAdapter adapter;
    @Inject
    SearchProductListAdapter productListAdapter;
    @Inject
    SearchSubCategoryAdapter mSearchSubCategoryAdapter;
    FragmentSearchBinding mFragmentSearchBinding;
    String searchTerms="";

    public static SearchFragment newInstance(String fromPage, String toPage) {
        Bundle args = new Bundle();
        args.putString(AppConstants.FROM, fromPage);
        args.putString(AppConstants.PAGE, toPage);
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
    }

    @Override
    public void quickSearchSuccess() {
        mFragmentSearchBinding.recyclerviewSearchSuggestion.setVisibility(View.VISIBLE);
        mFragmentSearchBinding.recyclerviewProduct.setVisibility(View.GONE);
        mFragmentSearchBinding.searchNotFound.setVisibility(View.GONE);
    }

    @Override
    public void searchNotFound() {
        mFragmentSearchBinding.recyclerviewSearchSuggestion.setVisibility(View.GONE);
        mFragmentSearchBinding.recyclerviewProduct.setVisibility(View.GONE);
        mFragmentSearchBinding.searchNotFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchViewModel.setNavigator(this);
        adapter.setListener(this);
        productListAdapter.setListener(this);
        mSearchSubCategoryAdapter.setListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentSearchBinding = getViewDataBinding();

        mFragmentSearchBinding.before.setVisibility(View.VISIBLE);

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager mLayoutManagerProduct
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager mLayoutManagerSubCategory
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentSearchBinding.recyclerviewSearchSuggestion.setLayoutManager(mLayoutManager);
        mFragmentSearchBinding.recyclerviewSearchSuggestion.setAdapter(adapter);

        mLayoutManagerProduct.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentSearchBinding.recyclerviewProduct.setLayoutManager(mLayoutManagerProduct);
        mFragmentSearchBinding.recyclerviewProduct.setAdapter(productListAdapter);


        mLayoutManagerSubCategory.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentSearchBinding.recyclerviewSearchSubCategory.setLayoutManager(mLayoutManagerSubCategory);
        mFragmentSearchBinding.recyclerviewSearchSubCategory.setAdapter(mSearchSubCategoryAdapter);


        /*mFragmentSearchBinding.search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });*/
        mFragmentSearchBinding.search.requestFocus();
        mFragmentSearchBinding.search.setFocusable(true);

        InputMethodManager imm = (InputMethodManager)getActivity(). getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(  mFragmentSearchBinding.search, InputMethodManager.SHOW_IMPLICIT);

        mFragmentSearchBinding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    searchTerms = charSequence.toString();
                    if (charSequence.toString().length() > 1) {
                        mFragmentSearchBinding.recyclerviewProduct.setVisibility(View.GONE);
                        mFragmentSearchBinding.recyclerviewSearchSuggestion.setVisibility(View.VISIBLE);
                        mFragmentSearchBinding.before.setVisibility(View.GONE);
                        mSearchViewModel.quickSearch(charSequence.toString());
                    } else {
                        mFragmentSearchBinding.recyclerviewProduct.setVisibility(View.GONE);
                        mFragmentSearchBinding.recyclerviewSearchSuggestion.setVisibility(View.GONE);
                        mFragmentSearchBinding.before.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


       /* mFragmentSearchBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                try {
                    searchTerms = s;
                    if (s.length() > 1) {
                        mFragmentSearchBinding.recyclerviewProduct.setVisibility(View.GONE);
                        mFragmentSearchBinding.recyclerviewSearchSuggestion.setVisibility(View.VISIBLE);
                        mFragmentSearchBinding.before.setVisibility(View.GONE);
                        mSearchViewModel.quickSearch(s);
                    } else {
                        mFragmentSearchBinding.recyclerviewProduct.setVisibility(View.GONE);
                        mFragmentSearchBinding.recyclerviewSearchSuggestion.setVisibility(View.GONE);
                        mFragmentSearchBinding.before.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                try {
                    searchTerms = s;
                    if (s.length() > 1) {
                        mFragmentSearchBinding.recyclerviewProduct.setVisibility(View.GONE);
                        mFragmentSearchBinding.recyclerviewSearchSuggestion.setVisibility(View.VISIBLE);
                        mFragmentSearchBinding.before.setVisibility(View.GONE);
                        mSearchViewModel.quickSearch(s);
                    } else {
                        mFragmentSearchBinding.recyclerviewProduct.setVisibility(View.GONE);
                        mFragmentSearchBinding.recyclerviewSearchSuggestion.setVisibility(View.GONE);
                        mFragmentSearchBinding.before.setVisibility(View.VISIBLE);
                        mFragmentSearchBinding.searchNotFound.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });*/

        /*Bundle intent = getArguments();
        assert intent != null;
        new Analytics().eventPageOpens(getContext(), intent.getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_SEARCH);*/
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        mSearchViewModel.getSearchItemsLiveData().observe(this,
                catregoryItemViewModel -> mSearchViewModel.addSearchItemsToList(catregoryItemViewModel));

        mSearchViewModel.getSearchSubCategoryItemsLiveData().observe(this,
                catregoryItemViewModel -> mSearchViewModel.addSearchSubCategoryItemsToList(catregoryItemViewModel));

        mSearchViewModel.getSearchProductItemsLiveData().observe(this,
                catregoryItemViewModel -> mSearchViewModel.addSearchProductItemsToList(catregoryItemViewModel));
    }

    @Override
    public void onSuggestionItemClickData(QuickSearchResponse.Result.ProductsList result) {
        try {
            new Analytics().eventSearch(getContext(),searchTerms,mSearchViewModel.resultReturned.get(),"product",result.getName());
            Intent intent = ProductDetailsActivity.newIntent(getContext(),AppConstants.SCREEN_NAME_SEARCH,AppConstants.SCREEN_NAME_PRODUCT_DETAIL);
            intent.putExtra("vpid", String.valueOf(result.getVpid()));
            startActivity(intent);
            getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() {

    }

    @Override
    public void onProductItemClick(SearchProductResponse.Result products) {
        new Analytics().eventSearch(getContext(),searchTerms,mSearchViewModel.resultReturned.get(),"product",products.getProductname());
        Intent intent = CategoryL2Activity.newIntent(getContext(), AppConstants.SCREEN_NAME_SUB_CATEGORY_LI_LIST,AppConstants.SCREEN_NAME_SUB_CATEGORY_L2_PRODUCTS);
        intent.putExtra("scl1id", String.valueOf(products.getScl1Id()));
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onSuggestionItemClickData(QuickSearchResponse.Result.SubcategoryList result) {
        new Analytics().eventSearch(getContext(),searchTerms,mSearchViewModel.resultReturned.get(),"l1_sub_category",result.getSubCategory());
        Intent intent = CategoryL2Activity.newIntent(getContext(), AppConstants.SCREEN_NAME_SUB_CATEGORY_LI_LIST,AppConstants.SCREEN_NAME_SUB_CATEGORY_L2_PRODUCTS);
        intent.putExtra("scl1id", String.valueOf(result.getScl1Id()));
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
