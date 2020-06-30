package com.dailylocally.ui.favourites.products;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.databinding.ListItemCollectionProductsBinding;
import com.dailylocally.databinding.ListItemFavProductsBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;

public class FavProductListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<FavProductsResponse.Result> item_list;
    private ProductsAdapterListener mProductsAdapterListener;

    public FavProductListAdapter(List<FavProductsResponse.Result> item_list) {
        this.item_list = item_list;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ListItemFavProductsBinding productsBinding = ListItemFavProductsBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<FavProductsResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(ProductsAdapterListener listener) {
        this.mProductsAdapterListener = listener;
    }

    public interface ProductsAdapterListener {
        void refresh();

        void subscribeProduct(FavProductsResponse.Result products);

        void productItemClick(FavProductsResponse.Result products);


        void showToast(String message);
    }


    public class ProductsViewHolder extends BaseViewHolder implements FavProductsItemViewModel.ProductsItemViewModelListener {

        ListItemFavProductsBinding mListItemCategoriesBinding;
        FavProductsItemViewModel mCategoriesItemViewModel;

        public ProductsViewHolder(ListItemFavProductsBinding binding) {
            super(binding.getRoot());
            this.mListItemCategoriesBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final FavProductsResponse.Result blog = item_list.get(position);
            mCategoriesItemViewModel = new FavProductsItemViewModel(this, blog);
            mListItemCategoriesBinding.setProductsItemViewModel(mCategoriesItemViewModel);
            mListItemCategoriesBinding.executePendingBindings();

            mListItemCategoriesBinding.mrp.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        }


        @Override
        public void refresh() {
            mProductsAdapterListener.refresh();
        }

        @Override
        public void subscribeProduct(FavProductsResponse.Result products) {
            mProductsAdapterListener.subscribeProduct(products);
        }

        @Override
        public void onItemClick(FavProductsResponse.Result products) {
            mProductsAdapterListener.productItemClick(products);
        }

        @Override
        public void showToast(String message) {
            mProductsAdapterListener.showToast(message);
        }


    }

}
