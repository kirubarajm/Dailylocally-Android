package com.dailylocally.ui.transactionHistory;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dailylocally.databinding.ListItemTransactionHistoryBinding;
import com.dailylocally.ui.base.BaseViewHolder;
import java.util.List;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private List<TransactionHistoryResponse.Result> item_list;
    private TransactionHistoryInfoListener mBilldetailsInfoListener;

    public TransactionHistoryAdapter(List<TransactionHistoryResponse.Result> item_list) {
        this.item_list = item_list;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ListItemTransactionHistoryBinding blogViewBinding = ListItemTransactionHistoryBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<TransactionHistoryResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(TransactionHistoryInfoListener listener) {
        this.mBilldetailsInfoListener = listener;
    }

    public interface TransactionHistoryInfoListener {

        void viewClick(TransactionHistoryResponse.Result cartdetail);

    }

    public class TransactionHistoryViewHolder extends BaseViewHolder implements TransactionHistoryItemViewModel.TransactionHistoryViewModelListener {

        ListItemTransactionHistoryBinding mListItemLiveProductsBinding;
        TransactionHistoryItemViewModel mLiveProductsItemViewModel;

        public TransactionHistoryViewHolder(ListItemTransactionHistoryBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final TransactionHistoryResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new TransactionHistoryItemViewModel(blog,this);
            mListItemLiveProductsBinding.setTransactionHistoryItemViewModel(mLiveProductsItemViewModel);
            mListItemLiveProductsBinding.executePendingBindings();

        }

        @Override
        public void onItemClick(TransactionHistoryResponse.Result cartdetail) {
            mBilldetailsInfoListener.viewClick(cartdetail);
        }
    }
}
