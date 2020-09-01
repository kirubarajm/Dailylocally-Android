package com.dailylocally.ui.joinCommunity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.databinding.ListItemCommunityBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private List<CommunityResponse.Result> item_list;
    private TransactionHistoryInfoListener mBilldetailsInfoListener;

    public CommunityAdapter(List<CommunityResponse.Result> item_list) {
        this.item_list = item_list;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ListItemCommunityBinding blogViewBinding = ListItemCommunityBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new TransactionHistoryViewHolder(blogViewBinding);

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

    public void addItems(List<CommunityResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(TransactionHistoryInfoListener listener) {
        this.mBilldetailsInfoListener = listener;
    }

    public interface TransactionHistoryInfoListener {

        void onItemClick(CommunityResponse.Result cartdetail);

    }

    public class TransactionHistoryViewHolder extends BaseViewHolder implements CommunityItemViewModel.TransactionHistoryViewModelListener {

        ListItemCommunityBinding mListItemLiveProductsBinding;
        CommunityItemViewModel mLiveProductsItemViewModel;

        public TransactionHistoryViewHolder(ListItemCommunityBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final CommunityResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new CommunityItemViewModel(blog,this);
            mListItemLiveProductsBinding.setCommunityItemViewModel(mLiveProductsItemViewModel);
            mListItemLiveProductsBinding.executePendingBindings();

        }

        @Override
        public void onItemClick(CommunityResponse.Result cartdetail) {
            mBilldetailsInfoListener.onItemClick(cartdetail);
        }
    }
}
