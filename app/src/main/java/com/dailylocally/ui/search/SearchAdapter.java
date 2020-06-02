package com.dailylocally.ui.search;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.data.DataManager;

import com.dailylocally.databinding.ListItemEmptySearchBinding;
import com.dailylocally.databinding.ListItemSearchBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<QuickSearchResponse.Datum> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;

    private DataManager dataManager;


    public SearchAdapter(List<QuickSearchResponse.Datum> item_list) {
        this.item_list = item_list;
    }

    public SearchAdapter(List<QuickSearchResponse.Datum> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemSearchBinding blogViewBinding = ListItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new LiveProductsViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:


            default:
                ListItemEmptySearchBinding blogViewBinding1 = ListItemEmptySearchBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(blogViewBinding1);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        if (item_list != null && item_list.size() > 0) {
            return item_list.size();
        } else {
            return 1;
        }
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

    public void addItems(List<QuickSearchResponse.Datum> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface LiveProductsAdapterListener {

        void onItemClickData(QuickSearchResponse.Datum result);

    }


    public class EmptyViewHolder extends BaseViewHolder {

        private final ListItemEmptySearchBinding mBinding;


        EmptySearchItemViewModel emptyItemViewModel;

        public EmptyViewHolder(ListItemEmptySearchBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            emptyItemViewModel = new EmptySearchItemViewModel("No search results found");
            mBinding.setEmptyItemViewModel(emptyItemViewModel);
        }

    }

    public class LiveProductsViewHolder extends BaseViewHolder implements SearchItemViewModel.SearchItemViewModelListener {
        ListItemSearchBinding mListItemLiveProductsBinding;
        SearchItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemSearchBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }


        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final QuickSearchResponse.Datum blog = item_list.get(position);
            mLiveProductsItemViewModel = new SearchItemViewModel(this, blog);
            mListItemLiveProductsBinding.setSearchItemViewModel(mLiveProductsItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();


        }

        @Override
        public void onItemClick(QuickSearchResponse.Datum result) {
            mLiveProductsAdapterListener.onItemClickData(result);
        }

    }

}
