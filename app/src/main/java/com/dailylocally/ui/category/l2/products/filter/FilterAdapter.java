package com.dailylocally.ui.category.l2.products.filter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;
/*
public class FilterAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    public Integer type;
    private List<FilterItems> item_list;
    private FiltersAdapterListener mFiltersAdapterListener;

    private DataManager dataManager;


    public FilterAdapter(List<FilterItems> item_list) {
        this.item_list = item_list;
    }

    public FilterAdapter(List<FilterItems> item_list, DataManager dataManager) {
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

    public void addItems(List<FilterItems> blogList, Integer type) {
        item_list.addAll(blogList);
        this.type = type;
        notifyDataSetChanged();
    }

    public void setListener(FiltersAdapterListener listener) {
        this.mFiltersAdapterListener = listener;
    }

    public interface FiltersAdapterListener {

        void onItemClickData(Integer id);

        void addToFilter(Integer id);

        void removeFromFilter(Integer id);

        Integer getSelectedOption();


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
            final FilterItems blog = item_list.get(position);
            mFilterItemViewModel = new FilterItemViewModel(this, blog);
            mListItemLiveProductsBinding.setFilterItemViewModel(mFilterItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();
        }


        @Override
        public void onItemClick(Integer id) {
            mFiltersAdapterListener.onItemClickData(id);
        }

        @Override
        public void addfilter(Integer id) {
            mFiltersAdapterListener.addToFilter(id);

        }

        @Override
        public void removeFilter(Integer id) {
            mFiltersAdapterListener.removeFromFilter(id);

        }

        @Override
        public Integer getSelectedOptions() {
            return mFiltersAdapterListener.getSelectedOption();
        }

        @Override
        public String getFilters() {
            return dataManager.getFilterSort();
        }
    }

}
*/
