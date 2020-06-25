package com.dailylocally.ui.coupons;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.databinding.ListItemCouponsBinding;
import com.dailylocally.ui.base.BaseViewHolder;
import java.util.List;

public class CouponsAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private List<CouponsResponse.Result> item_list;
    private CouponsListener mBilldetailsInfoListener;
    boolean flagApplyButton;

    public CouponsAdapter(List<CouponsResponse.Result> item_list) {
        this.item_list = item_list;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ListItemCouponsBinding blogViewBinding = ListItemCouponsBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new CouponsViewHolder(blogViewBinding);

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

    public void forApplyView(boolean flagApplyButton) {
        this.flagApplyButton = flagApplyButton;
    }

    public void addItems(List<CouponsResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(CouponsListener listener) {
        this.mBilldetailsInfoListener = listener;
    }

    public interface CouponsListener {

        void onApplyClick(CouponsResponse.Result cartdetail);

    }

    public class CouponsViewHolder extends BaseViewHolder implements CouponsItemViewModel.FavoritesViewModelListener {

        ListItemCouponsBinding mListItemLiveProductsBinding;
        CouponsItemViewModel mLiveProductsItemViewModel;

        public CouponsViewHolder(ListItemCouponsBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final CouponsResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new CouponsItemViewModel(blog,this);
            mListItemLiveProductsBinding.setCouponsItemViewModel(mLiveProductsItemViewModel);
            mListItemLiveProductsBinding.executePendingBindings();

            if (flagApplyButton){
                mListItemLiveProductsBinding.txtApply.setVisibility(View.VISIBLE);
            }else {
                mListItemLiveProductsBinding.txtApply.setVisibility(View.GONE);
            }
        }

        @Override
        public void onItemClick(CouponsResponse.Result cartdetail) {
            mBilldetailsInfoListener.onApplyClick(cartdetail);
        }
    }
}
