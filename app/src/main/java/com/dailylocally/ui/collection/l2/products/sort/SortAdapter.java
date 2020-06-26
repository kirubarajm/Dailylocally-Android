package com.dailylocally.ui.collection.l2.products.sort;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.data.DataManager;
import com.dailylocally.databinding.ListItemSortBinding;
import com.dailylocally.ui.base.BaseViewHolder;

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
           // mListItemLiveProductsBinding.setSortItemViewModel(mSortItemViewModel);


            if (sSelected == position) {
                mListItemLiveProductsBinding.rButton.setChecked(true);
            } else {
                mListItemLiveProductsBinding.rButton.setChecked(false);
            }

            mListItemLiveProductsBinding.executePendingBindings();
        }


        @Override
        public void itemClick() {

            if (sSelected==getAdapterPosition()){
                sSelected=-1;

            }else {
                sSelected = getAdapterPosition();

            }
            notifyDataSetChanged();
        }
    }

}

