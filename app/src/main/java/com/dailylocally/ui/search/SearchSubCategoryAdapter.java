package com.dailylocally.ui.search;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.data.DataManager;
import com.dailylocally.databinding.ListItemEmptySearchBinding;
import com.dailylocally.databinding.ListItemSubCategoryBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;


public class SearchSubCategoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<QuickSearchResponse.Result.SubcategoryList> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;

    private DataManager dataManager;


    public SearchSubCategoryAdapter(List<QuickSearchResponse.Result.SubcategoryList> item_list) {
        this.item_list = item_list;
    }

    public SearchSubCategoryAdapter(List<QuickSearchResponse.Result.SubcategoryList> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemSubCategoryBinding blogViewBinding = ListItemSubCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<QuickSearchResponse.Result.SubcategoryList> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface LiveProductsAdapterListener {

        void onSuggestionItemClickData(QuickSearchResponse.Result.SubcategoryList result);

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

    public class LiveProductsViewHolder extends BaseViewHolder implements SearchSubCategorytemViewModel.SearchItemViewModelListener {
        ListItemSubCategoryBinding mListItemLiveProductsBinding;
        SearchSubCategorytemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemSubCategoryBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }


        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final QuickSearchResponse.Result.SubcategoryList blog = item_list.get(position);
            mLiveProductsItemViewModel = new SearchSubCategorytemViewModel(this, blog);
            mListItemLiveProductsBinding.setSearchSubCategoryItemViewModel(mLiveProductsItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();


        }

        @Override
        public void onItemClick(QuickSearchResponse.Result.SubcategoryList result) {
            mLiveProductsAdapterListener.onSuggestionItemClickData(result);
        }

    }

}
