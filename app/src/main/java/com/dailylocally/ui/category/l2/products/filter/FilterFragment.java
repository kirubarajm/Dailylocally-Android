package com.dailylocally.ui.category.l2.products.filter;

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
    String scl2id;
    String page;

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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentFilterBinding = getViewDataBinding();

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentFilterBinding.recyclerviewFilters.setLayoutManager(mLayoutManager);
        mFragmentFilterBinding.recyclerviewFilters.setAdapter(adapter);
        subscribeToLiveData();

        page=getArguments().getString(AppConstants.PAGE,"");
        if (getArguments().getString(AppConstants.PAGE,"").equals(AppConstants.NOTIFY_CATEGORY_L2_ACTV)){
            mFilterViewModel.scl1id = getArguments().getString("scl1id",null);
            mFilterViewModel.scl2id = getArguments().getString("scl2id",null);
            mFilterViewModel.getL2Filters( mFilterViewModel.scl1id, mFilterViewModel.scl2id);
        }else   if (getArguments().getString(AppConstants.PAGE,"").equals(AppConstants.NOTIFY_CATEGORY_L1_PROD_ACTV)){
            mFilterViewModel.catid = getArguments().getString("catid",null);
            mFilterViewModel.scl1id = getArguments().getString("scl1id",null);
            mFilterViewModel.getCatFilters( mFilterViewModel.catid,mFilterViewModel.scl1id);
        }
        else {
            mFilterViewModel.cid = getArguments().getString("cid",null);
            mFilterViewModel.scl1id = getArguments().getString("scl1id",null);
            mFilterViewModel.getCollectionFilters( mFilterViewModel.cid,mFilterViewModel.scl1id);

        }

        Bundle intent = getArguments();
        if (intent != null) {
            new Analytics().eventPageOpens(getContext(), intent.getString(AppConstants.FROM, "nil"), AppConstants.SCREEN_NAME_FILTER);
        }

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
        if (page.equals(AppConstants.NOTIFY_CATEGORY_L2_ACTV)) {
            filterListener.FilterRefresh(mFilterViewModel.scl2id);
        }else {
            filterListener.FilterRefresh(mFilterViewModel.scl1id);
        }
        dismiss();

    }

    @Override
    public void applyFilter() {
        if (page.equals(AppConstants.NOTIFY_CATEGORY_L2_ACTV)) {
            filterListener.FilterRefresh(mFilterViewModel.scl2id);
        }else {
            filterListener.FilterRefresh(mFilterViewModel.scl1id);
        }
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
