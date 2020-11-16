package com.dailylocally.ui.community.event;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.databinding.ListItemCommunityPostBinding;
import com.dailylocally.databinding.ListItemEventPostBinding;
import com.dailylocally.ui.base.BaseViewHolder;
import com.dailylocally.ui.community.details.CommentsListAdapter;
import com.dailylocally.ui.community.details.EventDetailsActivity;
import com.dailylocally.utilities.DailylocallyApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import im.getsocial.sdk.communities.GetSocialActivity;

public class EventListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    boolean loading;
    int TYPE_PRODUCT = 1;
    int TYPE_LODER = 2;
    private List<GetSocialActivity> item_list;
    private ProductsAdapterListener mProductsAdapterListener;
    CommentsListAdapter commentsListAdapter;

    public EventListAdapter(List<GetSocialActivity> item_list) {
        this.item_list = item_list;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ListItemEventPostBinding productsBinding = ListItemEventPostBinding.inflate(LayoutInflater.from(parent.getContext()),
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

        notifyDataSetChanged();
    }

    public void setListener(ProductsAdapterListener listener) {
        this.mProductsAdapterListener = listener;
    }

    public interface ProductsAdapterListener {

        void hideKBoard();
        void refresh();
        void commentClick(GetSocialActivity posts);
        void actionData(Map<String, String> actionDatas);
        void like();
        void dislike();
        void comment();

        void viewAllComment(GetSocialActivity posts);
    }


    public class ProductsViewHolder extends BaseViewHolder implements EventListItemViewModel.PostItemViewModelListener {

        ListItemEventPostBinding mListItemCategoriesBinding;
        EventListItemViewModel mCategoriesItemViewModel;

        public ProductsViewHolder(ListItemEventPostBinding binding) {
            super(binding.getRoot());
            this.mListItemCategoriesBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final GetSocialActivity blog = item_list.get(position);
            mCategoriesItemViewModel = new EventListItemViewModel(this, blog);
            mListItemCategoriesBinding.setPostListItemViewModel(mCategoriesItemViewModel);
            mListItemCategoriesBinding.executePendingBindings();

        //    mListItemCategoriesBinding.mrp.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


            /*mCategoriesItemViewModel.getsocialActivitiesListLiveData().observe(EventListAdapter.this,
                    postItemViewModel -> mCategoriesItemViewModel.addCommunityPostToList(postItemViewModel));*/



        }


        @Override
        public void refresh() {
            mProductsAdapterListener.refresh();
        }

        @Override
        public void like() {
            mProductsAdapterListener.like();
        }

        @Override
        public void dislike() {
            mProductsAdapterListener.dislike();
        }

        @Override
        public void comment() {
            mProductsAdapterListener.comment();
        }

        @Override
        public void actionClick() {

        }

        @Override
        public void viewAllComment(GetSocialActivity posts) {
            mProductsAdapterListener.viewAllComment(posts);
        }

        @Override
        public void commentClick(GetSocialActivity posts) {
            mProductsAdapterListener.commentClick(posts);
        }

        @Override
        public void addOneComment(GetSocialActivity comments) {
           /* mListItemCategoriesBinding.commentText.setText("");
            commentsListAdapter.addOneItems(comments);
            mProductsAdapterListener.hideKBoard();*/



        }

        @Override
        public void addComments(List<GetSocialActivity> comments) {

          //  List<GetSocialActivity> commentList=new ArrayList<>();
            commentsListAdapter=new CommentsListAdapter(comments);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DailylocallyApp.getInstance());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            //  linearLayoutManager.setReverseLayout(true);
            mListItemCategoriesBinding.recyclerPost.setLayoutManager(new LinearLayoutManager(DailylocallyApp.getInstance()));
            mListItemCategoriesBinding.recyclerPost.setAdapter(commentsListAdapter);

            mListItemCategoriesBinding.commentText.setText("");
            mProductsAdapterListener.hideKBoard();
         
           // commentsListAdapter.addItems(comments);
        }

        @Override
        public void actionData(Map<String, String> actionDatas) {
            mProductsAdapterListener.actionData(actionDatas);
        }


    }


}
