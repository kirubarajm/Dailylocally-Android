package com.dailylocally.ui.search;

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
import javax.inject.Inject;

public class SearchFragment extends BaseFragment<FragmentSearchBinding, SearchViewModel> implements
        SearchNavigator,SearchAdapter.LiveProductsAdapterListener {

    @Inject
    SearchViewModel mSearchViewModel;
    @Inject
    SearchAdapter adapter;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchViewModel.setNavigator(this);
        adapter.setListener(this);
        subscribeToLiveData();
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


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentSearchBinding.recyclerviewSearchSuggestion.setLayoutManager(mLayoutManager);
        mFragmentSearchBinding.recyclerviewSearchSuggestion.setAdapter(adapter);

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

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void subscribeToLiveData() {
        mSearchViewModel.getSearchItemsLiveData().observe(this,
                catregoryItemViewModel -> mSearchViewModel.addSearchItemsToList(catregoryItemViewModel));
    }

    @Override
    public void onItemClickData(QuickSearchResponse.Datum result) {

    }
}
