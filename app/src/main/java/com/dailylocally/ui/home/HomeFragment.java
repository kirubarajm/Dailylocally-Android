package com.dailylocally.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentHomeBinding;
import com.dailylocally.ui.address.viewAddress.ViewAddressActivity;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.collection.l2.CollectionDetailsActivity;
import com.dailylocally.ui.main.MainActivity;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeNavigator, CategoriesAdapter.CategoriesAdapterListener {
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
    public void dataLoaded() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getBaseActivity(), 2);


        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                for (int i = 0; i < mHomeViewModel.categoryList.size(); i++) {

                    if (mHomeViewModel.categoryList.get(position).getCid() != null) {
                        return 2;

                    } else return 1;

                }
                return 1;
            }
        });

        /*gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                for (int i = 0; i < mHomeViewModel.categoryList.size(); i++) {

                    if (position == 2 || position == 5 || position == 8 || position == 11) {
                        *//*  if  (position % 2 == 0) {*//*
                        return 2;
                    } else {
                        return 1;
                    }

                }
                return 1;
            }
        });*/

        mFragmentHomeBinding.categoryList.setLayoutManager(gridLayoutManager);
        categoriesAdapter = new CategoriesAdapter(mHomeViewModel.categoryList);
        mFragmentHomeBinding.categoryList.setAdapter(categoriesAdapter);
        categoriesAdapter.setListener(this);

        mFragmentHomeBinding.loader.stop();
    }

    @Override
    public void dataLoading() {

        mFragmentHomeBinding.loader.start();

    }

    @Override
    public void gotoOrders() {

        ((MainActivity) getActivity()).openOrders();


    }

    @Override
    public void changeAddress() {
        /*Intent intent = GoogleAddressActivity.newIntent(getContext());
        intent.putExtra("edit", "1");
        startActivity(intent);*/
        Intent intent = ViewAddressActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void changeHeaderText(String headerContent) {
        mFragmentHomeBinding.welcomeText.setText(Html.fromHtml(headerContent));
    }

    @Override
    public void searchClick() {
        /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
        SearchFragment fragment = new SearchFragment();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();*/
        ((MainActivity) getActivity()).openExplore();
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
        mFragmentHomeBinding.loader.start();
        mHomeViewModel.fetchCategoryList();
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

    @Override
    public void categoryItemClicked(HomepageResponse.Result result) {


        if (result.getCid() != null) {

            Intent intent = CollectionDetailsActivity.newIntent(getContext());
            intent.putExtra("cid", result.getCid());
            startActivity(intent);

        } else {
            Intent intent = CategoryL1Activity.newIntent(getBaseActivity());
            intent.putExtra("catid", String.valueOf(result.getCatid()));
            startActivity(intent);
        }

    }
}
