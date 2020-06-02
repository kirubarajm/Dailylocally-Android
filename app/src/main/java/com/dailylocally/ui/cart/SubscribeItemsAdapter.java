package com.dailylocally.ui.cart;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.data.DataManager;
import com.dailylocally.databinding.ListItemOrdernowProductsBinding;
import com.dailylocally.databinding.ListItemSubscribeProductsBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;

public class SubscribeItemsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<CartResponse.SubscriptionItem> item_list;
    private SubscribeProductsAdapterListener mSubscribeProductsAdapterListener;

    private DataManager dataManager;


    public SubscribeItemsAdapter(List<CartResponse.SubscriptionItem> item_list) {
        this.item_list = item_list;
    }

    public SubscribeItemsAdapter(List<CartResponse.SubscriptionItem> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemSubscribeProductsBinding blogViewBinding = ListItemSubscribeProductsBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new SubscribeProductsViewHolder(blogViewBinding);

            default:
               return null;
             }

    }





    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return item_list.size();
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

    public void addItems(List<CartResponse.SubscriptionItem> blogList) {

            item_list.addAll(blogList);
            notifyDataSetChanged();

    }

    public void setListener(SubscribeProductsAdapterListener listener) {
        this.mSubscribeProductsAdapterListener = listener;
    }

    public interface SubscribeProductsAdapterListener {

        void reloadCart();
        void edit(CartResponse.SubscriptionItem product);
    }

    public class SubscribeProductsViewHolder extends BaseViewHolder implements SubscribeItemViewModel.SubscribeItemViewModelListener {

        ListItemSubscribeProductsBinding mListItemLiveProductsBinding;
        SubscribeItemViewModel mLiveProductsItemViewModel;

        public SubscribeProductsViewHolder(ListItemSubscribeProductsBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final CartResponse.SubscriptionItem blog = item_list.get(position);
            mLiveProductsItemViewModel = new SubscribeItemViewModel(this, blog);
            mListItemLiveProductsBinding.setSubscribeItemViewModel(mLiveProductsItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();

        }



        @Override
        public void reload() {
         mSubscribeProductsAdapterListener.reloadCart();
        }

        @Override
        public void edit(CartResponse.SubscriptionItem product) {
             mSubscribeProductsAdapterListener.edit(product);
        }

    }

}
