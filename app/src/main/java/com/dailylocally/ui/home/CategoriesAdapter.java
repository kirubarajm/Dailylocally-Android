package com.dailylocally.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.databinding.ListItemCategoriesBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<HomepageResponse.Result> item_list;
    private CategoriesAdapterListener mCategoriesAdapterListener;

    public CategoriesAdapter(List<HomepageResponse.Result> item_list) {
        this.item_list = item_list;
    }

    @Override
    public int getItemViewType(int position) {

        if (item_list.get(position).getTileType().equals("2")){
            return 2;
        }else {return 1;}
     //   return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ListItemCategoriesBinding collectionBinding = ListItemCategoriesBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new CategoriesViewHolder(collectionBinding);
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

    public void addItems(List<HomepageResponse.Result> blogList) {


        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(CategoriesAdapterListener listener) {
        this.mCategoriesAdapterListener = listener;
    }



    public interface CategoriesAdapterListener {
        void categoryItemClicked(HomepageResponse.Result result, TextView view, VideoView videoView,int pos);
        void updateVideoView(VideoView videoView);
    }


    public class CategoriesViewHolder extends BaseViewHolder implements CategoriesItemViewModel.CategoriesItemViewModelListener {

        ListItemCategoriesBinding mListItemCategoriesBinding;
        CategoriesItemViewModel mCategoriesItemViewModel;

        public CategoriesViewHolder(ListItemCategoriesBinding binding) {
            super(binding.getRoot());
            this.mListItemCategoriesBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final HomepageResponse.Result blog = item_list.get(position);
            mCategoriesItemViewModel = new CategoriesItemViewModel(this, blog);
            mListItemCategoriesBinding.setCategoriesItemViewModel(mCategoriesItemViewModel);
            mListItemCategoriesBinding.executePendingBindings();


        }


        @Override
        public void onItemClick(HomepageResponse.Result result) {
            mCategoriesAdapterListener.categoryItemClicked(result,mListItemCategoriesBinding.name,mListItemCategoriesBinding.videoView,getAdapterPosition());

        }

        @Override
        public void updateVideoView() {

            mCategoriesAdapterListener.updateVideoView(mListItemCategoriesBinding.videoView);


        }

    }

}
