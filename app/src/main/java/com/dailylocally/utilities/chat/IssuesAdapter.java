package com.dailylocally.utilities.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.dailylocally.databinding.ListItemIssuesBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;

public class IssuesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 0;

    Context context;
    private List<IssuesListResponse.Result> item_list;
    private IssuesAdapterListener mIssuesAdapterListener;

    public IssuesAdapter(List<IssuesListResponse.Result> item_list) {
        this.item_list = item_list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ListItemIssuesBinding blogViewBinding1 = ListItemIssuesBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new LiveProductsViewHolder(blogViewBinding1);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {

        return item_list.size();
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }

    public void clearItems() {
        item_list.clear();
    }

    public void addItems(List<IssuesListResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(IssuesAdapterListener listener) {
        this.mIssuesAdapterListener = listener;
    }


    public interface IssuesAdapterListener {

        void issueItemClick(IssuesListResponse.Result issues);

    }


    public class LiveProductsViewHolder extends BaseViewHolder implements IssuesItemViewModel.IssueItemViewModelListener {
        ListItemIssuesBinding mListItemIssuesBinding;
        IssuesItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemIssuesBinding binding) {
            super(binding.getRoot());
            this.mListItemIssuesBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final IssuesListResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new IssuesItemViewModel(this, blog);
            mListItemIssuesBinding.setIssuesItemViewModel(mLiveProductsItemViewModel);


            mListItemIssuesBinding.executePendingBindings();


        }

        @Override
        public void onItemClick(IssuesListResponse.Result issue) {
            mIssuesAdapterListener.issueItemClick(issue);
        }
    }


}
