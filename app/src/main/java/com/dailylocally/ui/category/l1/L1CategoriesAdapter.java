package com.dailylocally.ui.category.l1;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.databinding.ListItemL1CategoriesBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;

public class L1CategoriesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<L1CategoryResponse.Result> item_list;
    private CategoriesAdapterListener mCategoriesAdapterListener;

    public L1CategoriesAdapter(List<L1CategoryResponse.Result> item_list) {
        this.item_list = item_list;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ListItemL1CategoriesBinding collectionBinding = ListItemL1CategoriesBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new CategoriesViewHolder(collectionBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }


    public void clearItems() {
        item_list.clear();
    }

    public void addItems(List<L1CategoryResponse.Result> blogList) {


        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(CategoriesAdapterListener listener) {
        this.mCategoriesAdapterListener = listener;
    }


    public interface CategoriesAdapterListener {
        void categoryItemClicked(L1CategoryResponse.Result result,int pos);
    }


    public class CategoriesViewHolder extends BaseViewHolder implements L1CategoriesItemViewModel.L1CategoriesItemViewModelListener {

        ListItemL1CategoriesBinding mListItemCategoriesBinding;
        L1CategoriesItemViewModel mCategoriesItemViewModel;

        public CategoriesViewHolder(ListItemL1CategoriesBinding binding) {
            super(binding.getRoot());
            this.mListItemCategoriesBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final L1CategoryResponse.Result blog = item_list.get(position);
            mCategoriesItemViewModel = new L1CategoriesItemViewModel(this, blog);
            mListItemCategoriesBinding.setL1CategoriesItemViewModel(mCategoriesItemViewModel);
            mListItemCategoriesBinding.executePendingBindings();


        }


        @Override
        public void onItemClick(L1CategoryResponse.Result result) {
            mCategoriesAdapterListener.categoryItemClicked(result,getAdapterPosition());

        }

    }

}
