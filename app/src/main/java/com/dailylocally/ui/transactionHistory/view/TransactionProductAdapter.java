package com.dailylocally.ui.transactionHistory.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dailylocally.databinding.ListItemTransactionProductBinding;
import com.dailylocally.ui.base.BaseViewHolder;
import java.util.List;

public class TransactionProductAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private List<TransactionViewResponse.Result.Item> item_list;
    private TransactionHistoryInfoListener mBilldetailsInfoListener;

    public TransactionProductAdapter(List<TransactionViewResponse.Result.Item> item_list) {
        this.item_list = item_list;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ListItemTransactionProductBinding blogViewBinding = ListItemTransactionProductBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<TransactionViewResponse.Result.Item> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(TransactionHistoryInfoListener listener) {
        this.mBilldetailsInfoListener = listener;
    }

    public interface TransactionHistoryInfoListener {

        void onViewCalendarClick(TransactionViewResponse.Result.Item cartdetail);

    }

    public class TransactionHistoryViewHolder extends BaseViewHolder implements TransactionProductItemViewModel.TransactionHistoryViewModelListener {

        ListItemTransactionProductBinding mListItemLiveProductsBinding;
        TransactionProductItemViewModel mLiveProductsItemViewModel;

        public TransactionHistoryViewHolder(ListItemTransactionProductBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final TransactionViewResponse.Result.Item blog = item_list.get(position);
            mLiveProductsItemViewModel = new TransactionProductItemViewModel(blog,this);
            mListItemLiveProductsBinding.setTransactionProductItemViewModel(mLiveProductsItemViewModel);
            mListItemLiveProductsBinding.executePendingBindings();

        }

        @Override
        public void onViewCalendarClick(TransactionViewResponse.Result.Item cartdetail) {
            mBilldetailsInfoListener.onViewCalendarClick(cartdetail);
        }
    }
}
