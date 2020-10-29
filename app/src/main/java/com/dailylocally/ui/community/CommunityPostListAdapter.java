package com.dailylocally.ui.community;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.databinding.ListItemCommunityHeaderBinding;
import com.dailylocally.databinding.ListItemCommunityPostBinding;
import com.dailylocally.databinding.ListItemCommunityTilesBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;
import java.util.Map;

import im.getsocial.sdk.communities.GetSocialActivity;

public class CommunityPostListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    boolean loading;
    int TYPE_HEADER = 1;
    int TYPE_POST = 2;
    int TYPE_TILES = 3;


    private List<GetSocialActivity> item_list;
    private ProductsAdapterListener mProductsAdapterListener;


    public CommunityPostListAdapter(List<GetSocialActivity> item_list) {
        this.item_list = item_list;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        if (i == TYPE_HEADER) {
            ListItemCommunityHeaderBinding productsBinding = ListItemCommunityHeaderBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new HeaderViewHolder(productsBinding);
        } else if (i == TYPE_TILES) {
            ListItemCommunityTilesBinding productsBinding = ListItemCommunityTilesBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new TilesViewHolder(productsBinding);
        } else {
            ListItemCommunityPostBinding productsBinding = ListItemCommunityPostBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new ProductsViewHolder(productsBinding);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemViewType(int position) {

        if (item_list.size() > 0) {
            if (position == 0) {
                return TYPE_HEADER;
            } else if (position == 2) {
                return TYPE_TILES;
            } else {
                return TYPE_POST;
            }
        }
        return TYPE_POST;
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

        if (item_list.size() <= 25) {
            notifyDataSetChanged();
        } else if (item_list.size() >= 27) {
            notifyItemChanged((item_list.size() - blogList.size()) + 1);

        } else {
            notifyDataSetChanged();
        }

    }

    public void setListener(ProductsAdapterListener listener) {
        this.mProductsAdapterListener = listener;
    }

    public interface ProductsAdapterListener {

        void refresh();

        void actionData(Map<String, String> actionDatas);

        void gotoCommunityHome();

        void whatsAppGroup();

        void sneakPeak();

        void aboutUs();

        void communityEvent();
         void changeProfile();
         void searchClick();

        void creditInfoClick(TextView infoImageView);


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

        @Override
        public void actionData(Map<String, String> actionDatas) {
            mProductsAdapterListener.actionData(actionDatas);
        }


    }

    public class HeaderViewHolder extends BaseViewHolder implements CommunityHeaderListItemViewModel.PostItemViewModelListener {

        ListItemCommunityHeaderBinding mListItemCategoriesBinding;
        CommunityHeaderListItemViewModel mCategoriesItemViewModel;

        public HeaderViewHolder(ListItemCommunityHeaderBinding binding) {
            super(binding.getRoot());
            this.mListItemCategoriesBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final GetSocialActivity blog = item_list.get(position);
            mCategoriesItemViewModel = new CommunityHeaderListItemViewModel(this);
            mListItemCategoriesBinding.setCommunityViewModel(mCategoriesItemViewModel);
            mListItemCategoriesBinding.executePendingBindings();

            //    mListItemCategoriesBinding.mrp.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        }


        @Override
        public void refresh() {
            mProductsAdapterListener.refresh();
        }

        @Override
        public void creditInfoClick() {
        //    mProductsAdapterListener.creditInfoClick(mListItemCategoriesBinding.creditInfo);
        }
        @Override
        public void changeProfile() {
            mProductsAdapterListener. changeProfile();
        }

        @Override
        public void searchClick() {

            mProductsAdapterListener. searchClick();

        }


    }

    public class TilesViewHolder extends BaseViewHolder implements CommunityTileListItemViewModel.PostItemViewModelListener {

        ListItemCommunityTilesBinding mListItemCategoriesBinding;
        CommunityTileListItemViewModel mCategoriesItemViewModel;

        public TilesViewHolder(ListItemCommunityTilesBinding binding) {
            super(binding.getRoot());
            this.mListItemCategoriesBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final GetSocialActivity blog = item_list.get(position);
            mCategoriesItemViewModel = new CommunityTileListItemViewModel(this);
            mListItemCategoriesBinding.setCommunityViewModel(mCategoriesItemViewModel);
            mListItemCategoriesBinding.executePendingBindings();

            //    mListItemCategoriesBinding.mrp.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        }


        @Override
        public void gotoCommunityHome() {
            mProductsAdapterListener.gotoCommunityHome();
        }

        @Override
        public void whatsAppGroup() {
            mProductsAdapterListener.whatsAppGroup();
        }

        @Override
        public void sneakPeak() {
            mProductsAdapterListener.sneakPeak();
        }

        @Override
        public void aboutUs() {
            mProductsAdapterListener.aboutUs();
        }

        @Override
        public void communityEvent() {
            mProductsAdapterListener.communityEvent();
        }

        @Override
        public void actionData(Map<String, String> actionDatas) {
            mProductsAdapterListener.actionData(actionDatas);
        }


    }


}
