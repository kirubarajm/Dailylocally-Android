package com.dailylocally.ui.category.l2.products.filter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.data.DataManager;
import com.dailylocally.databinding.ListItemFiltersBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    public Integer type;
    private List<FilterItems.Result> item_list;
    private FiltersAdapterListener mFiltersAdapterListener;

    private DataManager dataManager;


    public FilterAdapter(List<FilterItems.Result> item_list) {
        this.item_list = item_list;
    }

    public FilterAdapter(List<FilterItems.Result> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_EMPTY:
            default:
                ListItemFiltersBinding blogViewBinding1 = ListItemFiltersBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<FilterItems.Result> blogList) {
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

    public class LiveProductsViewHolder extends BaseViewHolder implements FilterItemViewModel.FilterItemViewModelListener {
        ListItemFiltersBinding mListItemLiveProductsBinding;
        FilterItemViewModel mFilterItemViewModel;

        public LiveProductsViewHolder(ListItemFiltersBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final FilterItems.Result blog = item_list.get(position);
            mFilterItemViewModel = new FilterItemViewModel(this, blog);
            mListItemLiveProductsBinding.setFilterItemViewModel(mFilterItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();
        }



        @Override
        public void addfilter(String id) {
            mFiltersAdapterListener.addToFilter(id);

        }

        @Override
        public void removeFilter(String id) {
            mFiltersAdapterListener.removeFromFilter(id);

        }


    }

}

