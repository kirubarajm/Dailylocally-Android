package com.dailylocally.ui.cart;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.data.DataManager;


import com.dailylocally.databinding.ListItemOrdernowProductsBinding;
import com.dailylocally.ui.base.BaseViewHolder;


import java.util.List;

public class OrderNowAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<CartResponse.Item> item_list;
    private OrderNowProductsAdapterListener mLiveProductsAdapterListener;

    private DataManager dataManager;


    public OrderNowAdapter(List<CartResponse.Item> item_list) {
        this.item_list = item_list;
    }

    public OrderNowAdapter(List<CartResponse.Item> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemOrdernowProductsBinding blogViewBinding = ListItemOrdernowProductsBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new OrderNowProductsViewHolder(blogViewBinding);

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

    public void addItems(List<CartResponse.Item> blogList) {

        if (blogList.size()!=0){
            item_list.addAll(blogList);
            notifyDataSetChanged();
        }

    }

    public void setListener(OrderNowProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface OrderNowProductsAdapterListener {

        void reloadCart();
    }

    public class OrderNowProductsViewHolder extends BaseViewHolder implements OrderNowItemViewModel.DishItemViewModelListener {

        ListItemOrdernowProductsBinding mListItemLiveProductsBinding;
        OrderNowItemViewModel mLiveProductsItemViewModel;

        public OrderNowProductsViewHolder(ListItemOrdernowProductsBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final CartResponse.Item blog = item_list.get(position);
            mLiveProductsItemViewModel = new OrderNowItemViewModel(this, blog);
            mListItemLiveProductsBinding.setOrderNowItemViewModel(mLiveProductsItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();

        }



        @Override
        public void reload() {
         mLiveProductsAdapterListener.reloadCart();
        }

    }

}
