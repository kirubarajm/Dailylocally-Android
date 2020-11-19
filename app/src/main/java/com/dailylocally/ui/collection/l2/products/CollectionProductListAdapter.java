package com.dailylocally.ui.collection.l2.products;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.databinding.ListItemCollectionProductsBinding;
import com.dailylocally.databinding.ListItemProductsBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;

public class CollectionProductListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<CollectionProductsResponse.Result> item_list;
    private ProductsAdapterListener mProductsAdapterListener;

    public CollectionProductListAdapter(List<CollectionProductsResponse.Result> item_list) {
        this.item_list = item_list;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ListItemCollectionProductsBinding productsBinding = ListItemCollectionProductsBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<CollectionProductsResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(ProductsAdapterListener listener) {
        this.mProductsAdapterListener = listener;
    }
    public void refreshItem(String pid) {


        for (int i=0;i<item_list.size();i++){

            if (item_list.get(i).getPid().equals(pid)){

                notifyItemChanged(i);

            }

        }

    }

    public interface ProductsAdapterListener {
        void refresh();
        void   addOrRemoveQuantity(String name, String action, String category, String l1, String l2, String cost, int quantity, String tag);

        void subscribeProduct(CollectionProductsResponse.Result products);

        void productItemClick(CollectionProductsResponse.Result products);


        void showToast(String message);
    }


    public class ProductsViewHolder extends BaseViewHolder implements CollectionProductsItemViewModel.ProductsItemViewModelListener {

        ListItemCollectionProductsBinding mListItemCategoriesBinding;
        CollectionProductsItemViewModel mCategoriesItemViewModel;

        public ProductsViewHolder(ListItemCollectionProductsBinding binding) {
            super(binding.getRoot());
            this.mListItemCategoriesBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final CollectionProductsResponse.Result blog = item_list.get(position);
            mCategoriesItemViewModel = new CollectionProductsItemViewModel(this, blog);
            mListItemCategoriesBinding.setProductsItemViewModel(mCategoriesItemViewModel);
            mListItemCategoriesBinding.executePendingBindings();

            mListItemCategoriesBinding.mrp.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        }


        @Override
        public void addOrRemoveQuantity(String name, String action, String category, String l1, String l2, String cost, int quantity, String tag) {
            mProductsAdapterListener.addOrRemoveQuantity(name,action,category,l1,l2,cost,quantity,tag);
        }
        @Override
        public void refresh() {
            mProductsAdapterListener.refresh();
        }

        @Override
        public void subscribeProduct(CollectionProductsResponse.Result products) {
            mProductsAdapterListener.subscribeProduct(products);
        }

        @Override
        public void onItemClick(CollectionProductsResponse.Result products) {
            mProductsAdapterListener.productItemClick(products);
        }

        @Override
        public void showToast(String message) {
            mProductsAdapterListener.showToast(message);
        }


    }

}
