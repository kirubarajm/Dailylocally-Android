package com.dailylocally.ui.category.l2.products;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.databinding.ListItemL1CategoriesBinding;
import com.dailylocally.databinding.ListItemProductsBinding;
import com.dailylocally.ui.base.BaseViewHolder;
import com.dailylocally.ui.category.l1.L1CategoriesItemViewModel;


import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<ProductsResponse.Result> item_list;
    private ProductsAdapterListener mProductsAdapterListener;

    public ProductListAdapter(List<ProductsResponse.Result> item_list) {
        this.item_list = item_list;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ListItemProductsBinding productsBinding = ListItemProductsBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ProductsViewHolder(productsBinding);
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

    public void addItems(List<ProductsResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(ProductsAdapterListener listener) {
        this.mProductsAdapterListener = listener;
    }



    public interface ProductsAdapterListener {
        void refresh();
        void subscribeProduct(ProductsResponse.Result products);
    }


    public class ProductsViewHolder extends BaseViewHolder implements ProductsItemViewModel.ProductsItemViewModelListener {

        ListItemProductsBinding mListItemCategoriesBinding;
        ProductsItemViewModel mCategoriesItemViewModel;

        public ProductsViewHolder(ListItemProductsBinding binding) {
            super(binding.getRoot());
            this.mListItemCategoriesBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final ProductsResponse.Result blog = item_list.get(position);
            mCategoriesItemViewModel = new ProductsItemViewModel(this,blog);
            mListItemCategoriesBinding.setProductsItemViewModel(mCategoriesItemViewModel);
            mListItemCategoriesBinding.executePendingBindings();


        }


        @Override
        public void refresh() {
            mProductsAdapterListener.refresh();
        }

        @Override
        public void subscribeProduct(ProductsResponse.Result products) {
            mProductsAdapterListener.subscribeProduct(products);
        }
    }

}
