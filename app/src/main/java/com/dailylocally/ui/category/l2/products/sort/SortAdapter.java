package com.dailylocally.ui.category.l2.products.sort;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.data.DataManager;
import com.dailylocally.data.prefs.AppPreferencesHelper;
import com.dailylocally.databinding.ListItemFiltersBinding;
import com.dailylocally.databinding.ListItemSortBinding;
import com.dailylocally.ui.base.BaseViewHolder;
import com.dailylocally.ui.category.l2.products.ProductsRequest;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class SortAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    public Integer type;
    private List<SortItems.Result> item_list;
    private FiltersAdapterListener mFiltersAdapterListener;

    private DataManager dataManager;
    private static int sSelected = -1;

    public SortAdapter(List<SortItems.Result> item_list) {
        this.item_list = item_list;
    }

    public SortAdapter(List<SortItems.Result> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
        sSelected = -1;
    }

    public void selectedItem() {
        notifyDataSetChanged();
    }

    public void selectedItemClear() {
        sSelected=-1;
        notifyDataSetChanged();
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_EMPTY:
            default:
                ListItemSortBinding blogViewBinding1 = ListItemSortBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new LiveProductsViewHolder(blogViewBinding1);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (item_list != null && !item_list.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public void clearItems() {
        item_list.clear();
    }

    public void addItems(List<SortItems.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(FiltersAdapterListener listener) {
        this.mFiltersAdapterListener = listener;
    }

    public interface FiltersAdapterListener {


        void addToFilter(String id);

        void removeFromFilter(String id);


    }

    public class LiveProductsViewHolder extends BaseViewHolder implements SortItemViewModel.FilterItemViewModelListener {
        ListItemSortBinding mListItemLiveProductsBinding;
        SortItemViewModel mSortItemViewModel;

        public LiveProductsViewHolder(ListItemSortBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final SortItems.Result blog = item_list.get(position);
            mSortItemViewModel = new SortItemViewModel(this, blog);
            mListItemLiveProductsBinding.setSortItemViewModel(mSortItemViewModel);
            mListItemLiveProductsBinding.rButton.setVisibility(View.GONE);

            AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(DailylocallyApp.getInstance(), AppConstants.PREF_NAME);
            Gson sGson = new GsonBuilder().create();
            ProductsRequest productsRequest = sGson.fromJson(appPreferencesHelper.getFilterSort(), ProductsRequest.class);
            if (productsRequest != null) {
                if (productsRequest.getSortid()== item_list.get(position).getSortid()){
                   sSelected=position;
                }

            }


            if (sSelected == position) {
                mListItemLiveProductsBinding.rButton.setVisibility(View.VISIBLE);
            } else {
                mListItemLiveProductsBinding.rButton.setVisibility(View.GONE);
            }

            mListItemLiveProductsBinding.executePendingBindings();

        }


        @Override
        public void itemClick() {

            if (sSelected==getAdapterPosition()){
                sSelected=-1;
                mListItemLiveProductsBinding.rButton.setVisibility(View.GONE);
            }else {
                sSelected = getAdapterPosition();
                mListItemLiveProductsBinding.rButton.setVisibility(View.VISIBLE);
            }

          notifyDataSetChanged();
        }
    }

}

