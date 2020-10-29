package com.dailylocally.ui.category.l2.products.sort;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentSortBinding;
import com.dailylocally.ui.base.BaseBottomSheetFragment;
import com.dailylocally.ui.category.l2.products.filter.FilterListener;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;

import javax.inject.Inject;


public class SortFragment extends BaseBottomSheetFragment<FragmentSortBinding, SortViewModel> implements SortNavigator, SortAdapter.FiltersAdapterListener {


    public static final String TAG = SortFragment.class.getSimpleName();
    @Inject
    SortViewModel mSortViewModel;

    FragmentSortBinding mFragmentSortBinding;

    @Inject
    SortAdapter adapter;
    @Inject
    LinearLayoutManager mLayoutManager;
    FilterListener filterListener;

    String scl2id;

    public static SortFragment newInstance() {
        Bundle args = new Bundle();
        SortFragment fragment = new SortFragment();
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
        return BR.sortViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sort;
    }

    @Override
    public SortViewModel getViewModel() {
        return mSortViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSortViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentSortBinding = getViewDataBinding();

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentSortBinding.recyclerviewFilters.setLayoutManager(mLayoutManager);
        mFragmentSortBinding.recyclerviewFilters.setAdapter(adapter);
        subscribeToLiveData();

        if (getArguments().getString("type", "1").equals("2")) {

            mSortViewModel.scl2id = getArguments().getString("scl1id", "0");
            mSortViewModel.getCollectionSortItemList(mSortViewModel.scl2id);
        }else {
            mSortViewModel.scl2id = getArguments().getString("scl2id", "0");
            mSortViewModel.getSortItemList(mSortViewModel.scl2id);
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
        mSortViewModel.getSortItemsLiveData().observe(this,
                filters -> mSortViewModel.addtoFilterList(filters));
    }

    @Override
    public void clearFilters() {
        filterListener.FilterRefresh(mSortViewModel.scl2id);
        dismiss();

    }

    @Override
    public void applyFilter() {
        filterListener.FilterRefresh(mSortViewModel.scl2id);
        dismiss();
    }

    @Override
    public void close() {
        dismiss();
    }


    @Override
    public void addToFilter(String id) {
        mSortViewModel.addToFilter(id);
    }

    @Override
    public void removeFromFilter(String id) {
        mSortViewModel.removeFromFilter(id);
    }

}
