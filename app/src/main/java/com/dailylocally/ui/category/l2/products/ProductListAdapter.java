package com.dailylocally.ui.category.l2.products;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.databinding.ListItemProductsBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    boolean loading;
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

    public void loadingFalse() {
        loading = false;
    }

    public void addItems(List<ProductsResponse.Result> blogList, RecyclerView recyclerView) {
        if (item_list.size() > 3)
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int totalItemCount = linearLayoutManager.getItemCount();
                        int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                        int visibleThreshold = 10;
                        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                            loading = true;
                            mProductsAdapterListener.loadMore();

                        }
                    }
                });
            }


        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(ProductsAdapterListener listener) {
        this.mProductsAdapterListener = listener;
    }

    public interface ProductsAdapterListener {

        void refresh();

        void loadMore();

        void subscribeProduct(ProductsResponse.Result products);

        void productItemClick(ProductsResponse.Result products);

        void showToast(String message);
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
            mCategoriesItemViewModel = new ProductsItemViewModel(this, blog);
            mListItemCategoriesBinding.setProductsItemViewModel(mCategoriesItemViewModel);
            mListItemCategoriesBinding.executePendingBindings();

            mListItemCategoriesBinding.mrp.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        }


        @Override
        public void refresh() {
            mProductsAdapterListener.refresh();
        }

        @Override
        public void subscribeProduct(ProductsResponse.Result products) {
            mProductsAdapterListener.subscribeProduct(products);
        }

        @Override
        public void onItemClick(ProductsResponse.Result products) {
            mProductsAdapterListener.productItemClick(products);
        }

        @Override
        public void showToast(String message) {
            mProductsAdapterListener.showToast(message);
        }


    }

}
