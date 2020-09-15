package com.dailylocally.ui.community.details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.databinding.ListItemEventCommentsBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;
import java.util.Map;

import im.getsocial.sdk.communities.GetSocialActivity;

public class CommentsListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    boolean loading;
    int TYPE_PRODUCT = 1;
    int TYPE_LODER = 2;
    private List<GetSocialActivity> item_list;
    private ProductsAdapterListener mProductsAdapterListener;


    public CommentsListAdapter(List<GetSocialActivity> item_list) {
        this.item_list = item_list;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ListItemEventCommentsBinding productsBinding = ListItemEventCommentsBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addOneItems(GetSocialActivity blogList) {

        if (item_list.size() > 0) {
            item_list.add(0, blogList);
        } else {
            item_list.add(blogList);
        }
        notifyDataSetChanged();
    }


    public void addItems(List<GetSocialActivity> blogList) {

        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(ProductsAdapterListener listener) {
        this.mProductsAdapterListener = listener;
    }

    public interface ProductsAdapterListener {

        void refresh();

        void commentClick(GetSocialActivity posts);

        void actionData(Map<String, String> actionDatas);
    }


    public class ProductsViewHolder extends BaseViewHolder implements CommentsListItemViewModel.PostItemViewModelListener {

        ListItemEventCommentsBinding mListItemCategoriesBinding;
        CommentsListItemViewModel mCategoriesItemViewModel;

        public ProductsViewHolder(ListItemEventCommentsBinding binding) {
            super(binding.getRoot());
            this.mListItemCategoriesBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final GetSocialActivity blog = item_list.get(position);
            mCategoriesItemViewModel = new CommentsListItemViewModel(this, blog);
            mListItemCategoriesBinding.setPostListItemViewModel(mCategoriesItemViewModel);
            mListItemCategoriesBinding.executePendingBindings();

            //    mListItemCategoriesBinding.mrp.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


            mListItemCategoriesBinding.comment.setText(blog.getText());
            mListItemCategoriesBinding.comment.initViews();
        }


        @Override
        public void refresh() {
            mProductsAdapterListener.refresh();
        }

        @Override
        public void actionClick() {

        }

        @Override
        public void commentClick(GetSocialActivity posts) {
            mProductsAdapterListener.commentClick(posts);
        }

        @Override
        public void actionData(Map<String, String> actionDatas) {
            mProductsAdapterListener.actionData(actionDatas);
        }


    }


}
