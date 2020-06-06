package com.dailylocally.ui.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dailylocally.databinding.ListItemSearchProductListBinding;
import com.dailylocally.ui.base.BaseViewHolder;


import java.util.List;

public class SearchProductListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<SearchProductResponse.Result> item_list;
    private ProductsAdapterListener mProductsAdapterListener;

    public SearchProductListAdapter(List<SearchProductResponse.Result> item_list) {
        this.item_list = item_list;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ListItemSearchProductListBinding productsBinding = ListItemSearchProductListBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<SearchProductResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(ProductsAdapterListener listener) {
        this.mProductsAdapterListener = listener;
    }

    public interface ProductsAdapterListener {
        void refresh();

        void onProductItemClick(SearchProductResponse.Result products);
    }


    public class ProductsViewHolder extends BaseViewHolder implements SearchProductsItemViewModel.ProductsItemViewModelListener {

        ListItemSearchProductListBinding mListItemCategoriesBinding;
        SearchProductsItemViewModel mCategoriesItemViewModel;

        public ProductsViewHolder(ListItemSearchProductListBinding binding) {
            super(binding.getRoot());
            this.mListItemCategoriesBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final SearchProductResponse.Result blog = item_list.get(position);
            mCategoriesItemViewModel = new SearchProductsItemViewModel(this,blog);
            mListItemCategoriesBinding.setSearchProductsItemViewModel(mCategoriesItemViewModel);
            mListItemCategoriesBinding.executePendingBindings();


        }


        @Override
        public void refresh() {
            mProductsAdapterListener.refresh();
        }

        @Override
        public void subscribeProduct(SearchProductResponse.Result products) {
            //mProductsAdapterListener.onProductItemClick(products);
        }

        @Override
        public void onProductItemClick(SearchProductResponse.Result products) {
            mProductsAdapterListener.onProductItemClick(products);
        }
    }

}
