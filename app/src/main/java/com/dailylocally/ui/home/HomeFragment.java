package com.dailylocally.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentHomeBinding;
import com.dailylocally.ui.base.BaseFragment;

import javax.inject.Inject;

public class HomeFragment  extends BaseFragment<FragmentHomeBinding,HomeViewModel> implements HomeNavigator{
    @Inject
    CategoriesAdapter categoriesAdapter;
    @Inject
    HomeViewModel mHomeViewModel;
     FragmentHomeBinding mFragmentHomeBinding;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.homeViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        return mHomeViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void kitchenLoaded() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getBaseActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                for (int i = 0; i < mHomeViewModel.categoryList.size(); i++) {

                    if (position == 3) {
                        return 1;
                    }

                }
                return 2;
            }
        });
        mFragmentHomeBinding.categoryList.setLayoutManager(gridLayoutManager);
         categoriesAdapter = new CategoriesAdapter(mHomeViewModel.categoryList);
        mFragmentHomeBinding.categoryList.setAdapter(categoriesAdapter);
    }

    @Override
    public void changeHeaderText(String headerContent) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeViewModel.setNavigator(this);
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
        mFragmentHomeBinding = getViewDataBinding();
    }

    @Override
    public void onResume() {
        super.onResume();
        mHomeViewModel.updateAddressTitle();
    }

    private void subscribeToLiveData() {
        mHomeViewModel.getCategoryListLiveData().observe(this,
                catregoryItemViewModel -> mHomeViewModel.addCategoryToList(catregoryItemViewModel));
    }
}
