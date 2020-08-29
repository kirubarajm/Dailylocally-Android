package com.dailylocally.ui.community;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.databinding.ListItemCommunityPostBinding;
import com.dailylocally.databinding.ListItemLoaderBinding;
import com.dailylocally.databinding.ListItemProductsBinding;
import com.dailylocally.ui.base.BaseViewHolder;
import com.dailylocally.ui.category.l2.products.ProductsItemViewModel;
import com.dailylocally.ui.category.l2.products.ProductsResponse;

import java.util.List;

import im.getsocial.sdk.communities.GetSocialActivity;

public class CommunityPostListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    boolean loading;
    int TYPE_PRODUCT = 1;
    int TYPE_LODER = 2;
    private List<GetSocialActivity> item_list;
    private ProductsAdapterListener mProductsAdapterListener;


    public CommunityPostListAdapter(List<GetSocialActivity> item_list) {
        this.item_list = item_list;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ListItemCommunityPostBinding productsBinding = ListItemCommunityPostBinding.inflate(LayoutInflater.from(parent.getContext()),
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





    public void addItems(List<GetSocialActivity> blogList) {


        item_list.addAll(blogList);

        if (item_list.size()>0){
            item_list.remove(0);
        }

        notifyDataSetChanged();
    }

    public void setListener(ProductsAdapterListener listener) {
        this.mProductsAdapterListener = listener;
    }

    public interface ProductsAdapterListener {

        void refresh();

        void loadMore();

        void subscribeProduct(ProductsResponse.Result products, int position);

        void productItemClick(ProductsResponse.Result products, int position);

        void showToast(String message);
    }


    public class ProductsViewHolder extends BaseViewHolder implements PostListItemViewModel.PostItemViewModelListener {

        ListItemCommunityPostBinding mListItemCategoriesBinding;
        PostListItemViewModel mCategoriesItemViewModel;

        public ProductsViewHolder(ListItemCommunityPostBinding binding) {
            super(binding.getRoot());
            this.mListItemCategoriesBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final GetSocialActivity blog = item_list.get(position);
            mCategoriesItemViewModel = new PostListItemViewModel(this, blog);
            mListItemCategoriesBinding.setPostListItemViewModel(mCategoriesItemViewModel);
            mListItemCategoriesBinding.executePendingBindings();

        //    mListItemCategoriesBinding.mrp.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        }


        @Override
        public void refresh() {
            mProductsAdapterListener.refresh();
        }


    }


}
