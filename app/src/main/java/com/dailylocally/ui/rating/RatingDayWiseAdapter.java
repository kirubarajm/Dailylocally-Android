package com.dailylocally.ui.rating;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dailylocally.databinding.ListItemRatingProductBinding;
import com.dailylocally.ui.base.BaseViewHolder;
import com.dailylocally.ui.calendarView.CalendarDayWiseResponse;

import java.util.List;


public class RatingDayWiseAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<CalendarDayWiseResponse.Result.Item> item_list;
    private CategoriesAdapterListener mCategoriesAdapterListener;

    public RatingDayWiseAdapter(List<CalendarDayWiseResponse.Result.Item> item_list) {
        this.item_list = item_list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ListItemRatingProductBinding collectionBinding = ListItemRatingProductBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<CalendarDayWiseResponse.Result.Item> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(CategoriesAdapterListener listener) {
        this.mCategoriesAdapterListener = listener;
    }

    public interface CategoriesAdapterListener {
        void onItemClick(CalendarDayWiseResponse.Result.Item result);
    }

    public class CategoriesViewHolder extends BaseViewHolder implements RatingDayWiseItemViewModel.CalendarItemViewModelListener {

        ListItemRatingProductBinding mListItemCategoriesBinding;
        RatingDayWiseItemViewModel mCategoriesItemViewModel;

        public CategoriesViewHolder(ListItemRatingProductBinding binding) {
            super(binding.getRoot());
            this.mListItemCategoriesBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final CalendarDayWiseResponse.Result.Item blog = item_list.get(position);
            mCategoriesItemViewModel = new RatingDayWiseItemViewModel(this, blog);
            mListItemCategoriesBinding.setRatingProductsItemViewModel(mCategoriesItemViewModel);
            mListItemCategoriesBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(CalendarDayWiseResponse.Result.Item result) {
            mCategoriesAdapterListener.onItemClick(result);

        }
    }
}
