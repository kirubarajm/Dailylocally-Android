package com.dailylocally.ui.rating;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dailylocally.databinding.ListItemRatingProductBinding;
import com.dailylocally.ui.base.BaseViewHolder;
import java.util.List;

public class RatingProductAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private List<RatingResponse.Result> item_list;
    private CouponsListener mBilldetailsInfoListener;
    boolean flagApplyButton;

    public RatingProductAdapter(List<RatingResponse.Result> item_list) {
        this.item_list = item_list;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ListItemRatingProductBinding blogViewBinding = ListItemRatingProductBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<RatingResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(CouponsListener listener) {
        this.mBilldetailsInfoListener = listener;
    }

    public interface CouponsListener {

        void onApplyClick(RatingResponse.Result cartdetail);

    }

    public class CouponsViewHolder extends BaseViewHolder implements RatingProductItemViewModel.FavoritesViewModelListener {

        ListItemRatingProductBinding mListItemLiveProductsBinding;
        RatingProductItemViewModel mLiveProductsItemViewModel;

        public CouponsViewHolder(ListItemRatingProductBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final RatingResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new RatingProductItemViewModel(blog,this);
            mListItemLiveProductsBinding.setRatingProductsItemViewModel(mLiveProductsItemViewModel);
            mListItemLiveProductsBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(RatingResponse.Result cartdetail) {
            mBilldetailsInfoListener.onApplyClick(cartdetail);
        }
    }
}
