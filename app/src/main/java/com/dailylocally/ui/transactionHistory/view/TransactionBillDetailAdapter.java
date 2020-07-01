package com.dailylocally.ui.transactionHistory.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dailylocally.databinding.ListItemTransactionBillDetailBinding;
import com.dailylocally.ui.base.BaseViewHolder;
import java.util.List;

public class TransactionBillDetailAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<TransactionViewResponse.Result.Cartdetail> item_list;
    private TransactionHistoryInfoListener mBilldetailsInfoListener;

    public TransactionBillDetailAdapter(List<TransactionViewResponse.Result.Cartdetail> item_list) {
        this.item_list = item_list;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ListItemTransactionBillDetailBinding blogViewBinding = ListItemTransactionBillDetailBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<TransactionViewResponse.Result.Cartdetail> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(TransactionHistoryInfoListener listener) {
        this.mBilldetailsInfoListener = listener;
    }

    public interface TransactionHistoryInfoListener {

        //void onViewCalendarClick(TransactionViewResponse.Result.Cartdetail cartdetail);

    }

    public class TransactionHistoryViewHolder extends BaseViewHolder implements TransactionBillDetailItemViewModel.TransactionHistoryViewModelListener {

        ListItemTransactionBillDetailBinding mListItemLiveProductsBinding;
        TransactionBillDetailItemViewModel mLiveProductsItemViewModel;

        public TransactionHistoryViewHolder(ListItemTransactionBillDetailBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final TransactionViewResponse.Result.Cartdetail blog = item_list.get(position);
            mLiveProductsItemViewModel = new TransactionBillDetailItemViewModel(blog,this);
            mListItemLiveProductsBinding.setTransactionBillDetailItemViewModel(mLiveProductsItemViewModel);
            mListItemLiveProductsBinding.executePendingBindings();

        }

        @Override
        public void onViewCalendarClick(TransactionViewResponse.Result.Cartdetail cartdetail) {
            //mBilldetailsInfoListener.onViewCalendarClick(cartdetail);
        }
    }
}
