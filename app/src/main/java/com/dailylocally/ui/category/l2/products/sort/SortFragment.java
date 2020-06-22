package com.dailylocally.ui.category.l2.products.sort;

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


public class SortFragment extends BaseBottomSheetFragment<FragmentFilterBinding, SortViewModel> implements SortNavigator/*, FilterAdapter.FiltersAdapterListener*/ {


    public static final String TAG = SortFragment.class.getSimpleName();
    @Inject
    SortViewModel mSortViewModel;

    FragmentFilterBinding mFragmentFilterBinding;

    @Inject
    SortAdapter adapter;
    @Inject
    LinearLayoutManager mLayoutManager;
 //   StartFilter startFilter;
    Analytics analytics;
    String pageName= AppConstants.SCREEN_KITCHEN_FILTER;


    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StartFilter) {
            //init the listener
            startFilter = (StartFilter) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }

    }*/

    public static SortFragment newInstance() {
        Bundle args = new Bundle();
        SortFragment fragment = new SortFragment();
        fragment.setArguments(args);
        return fragment;
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
    public SortViewModel getViewModel() {
        return mSortViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSortViewModel.setNavigator(this);
     //   adapter.setListener(this);
        analytics=new Analytics(getActivity(),pageName);
        mSortViewModel.getSorts();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentFilterBinding = getViewDataBinding();

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentFilterBinding.recyclerviewFilters.setLayoutManager(mLayoutManager);
        mFragmentFilterBinding.recyclerviewFilters.setAdapter(adapter);
        subscribeToLiveData();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void handleError(Throwable throwable) {

    }
    private void subscribeToLiveData() {
        mSortViewModel.getFilterItemsLiveData().observe(this,
                filters -> mSortViewModel.addtoFilterList(filters));
    }
    @Override
    public void clearFilters() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_CLEAR);
        //startFilter.applyFilter();
        dismiss();

    }

    @Override
    public void applyFilter() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_APPLY);
        dismiss();
       // startFilter.applyFilter();

    }

    @Override
    public void close() {
        dismiss();
    }


   /* @Override
    public void onItemClickData(Integer id) {

    }

    @Override
    public void addToFilter(Integer id) {
        mFilterViewModel.addToFilter(id);
    }

    @Override
    public void removeFromFilter(Integer id) {
        mFilterViewModel.removeFromFilter(id);
    }

    @Override
    public Integer getSelectedOption() {
        return mFilterViewModel.getSelectedOptions();
    }*/
}
