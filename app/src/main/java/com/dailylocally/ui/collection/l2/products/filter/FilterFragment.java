package com.dailylocally.ui.collection.l2.products.filter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentFilterBinding;
import com.dailylocally.ui.base.BaseBottomSheetFragment;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

import javax.inject.Inject;


public class FilterFragment extends BaseBottomSheetFragment<FragmentFilterBinding, FilterViewModel> implements FilterNavigator, FilterAdapter.FiltersAdapterListener {


    public static final String TAG = FilterFragment.class.getSimpleName();
    @Inject
    FilterViewModel mFilterViewModel;

    FragmentFilterBinding mFragmentFilterBinding;

    @Inject
    FilterAdapter adapter;
    @Inject
    LinearLayoutManager mLayoutManager;
    FilterListener filterListener;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_KITCHEN_FILTER;

    String scl2id;

    public static FilterFragment newInstance() {
        Bundle args = new Bundle();
        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FilterListener) {
            //init the listener
            filterListener = (FilterListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }

    }

    @Override
    public int getBindingVariable() {
        return BR.filterViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_filter;
    }

    @Override
    public FilterViewModel getViewModel() {
        return mFilterViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilterViewModel.setNavigator(this);
        //   adapter.setListener(this);
        analytics = new Analytics(getActivity(), pageName);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentFilterBinding = getViewDataBinding();

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentFilterBinding.recyclerviewFilters.setLayoutManager(mLayoutManager);
        mFragmentFilterBinding.recyclerviewFilters.setAdapter(adapter);
        subscribeToLiveData();


        mFilterViewModel.scl2id = getArguments().getString("scl2id",null);

        mFilterViewModel.getFilters( mFilterViewModel.scl2id);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    private void subscribeToLiveData() {
        mFilterViewModel.getFilterItemsLiveData().observe(this,
                filters -> mFilterViewModel.addtoFilterList(filters));
    }

    @Override
    public void clearFilters() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_CLEAR);
        //startFilter.applyFilter();
        filterListener.FilterRefresh( mFilterViewModel.scl2id);
        dismiss();

    }

    @Override
    public void applyFilter() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_APPLY);
        filterListener.FilterRefresh( mFilterViewModel.scl2id);
        dismiss();
    }

    @Override
    public void close() {
        dismiss();
    }


    @Override
    public void addToFilter(String id) {
        mFilterViewModel.addToFilter(id);
    }

    @Override
    public void removeFromFilter(String id) {
        mFilterViewModel.removeFromFilter(id);
    }




}
