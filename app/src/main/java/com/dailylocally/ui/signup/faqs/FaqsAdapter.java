package com.dailylocally.ui.signup.faqs;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.databinding.ListItemFaqsBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;

public class FaqsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    List<FaqResponse.ProductList> item_list;
    FaqsAdapterListener mFaqsAdapterListener;

    public FaqsAdapter(List<FaqResponse.ProductList> item_list) {
        this.item_list = item_list;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemFaqsBinding blogViewBinding = ListItemFaqsBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new FaqsViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ListItemFaqsBinding blogViewBinding1 = ListItemFaqsBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new FaqsViewHolder(blogViewBinding1);
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

    public class FaqsViewHolder extends BaseViewHolder implements FaqsItemViewModel.FaqItemViewModelListener {

        private ListItemFaqsBinding mBinding;

        private FaqsItemViewModel mFaqsItemViewModel;

        public FaqsViewHolder(ListItemFaqsBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public FaqsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            if(item_list.isEmpty()) return;
            final FaqResponse.ProductList blog = item_list.get(position);
            mFaqsItemViewModel = new FaqsItemViewModel(blog,this);
            mBinding.setFaqsItemViewModel(mFaqsItemViewModel);

            mBinding.executePendingBindings();
        }
        @Override
        public void onItemClick(String blogUrl) {
            if (blogUrl != null) {
                try {
                    mFaqsAdapterListener.onRetryClick();
                } catch (Exception e) {
                    Log.e("url error","");
                }
            }else {
                mFaqsAdapterListener.onRetryClick();
            }
        }
    }

    public void clearItems() {
        item_list.clear();
    }

    public void addItems(List<FaqResponse.ProductList> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(FaqsAdapterListener listener) {
        this.mFaqsAdapterListener = listener;
    }

    public interface FaqsAdapterListener {

        void onRetryClick();
    }

}
