package com.dailylocally.ui.calendarView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dailylocally.databinding.ListItemCalendarBinding;
import com.dailylocally.ui.base.BaseViewHolder;

import java.util.List;


public class CalendarDayWiseAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<CalendarDayWiseResponse.Result.Item> item_list;
    private CategoriesAdapterListener mCategoriesAdapterListener;

    public CalendarDayWiseAdapter(List<CalendarDayWiseResponse.Result.Item> item_list) {
        this.item_list = item_list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ListItemCalendarBinding collectionBinding = ListItemCalendarBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public class CategoriesViewHolder extends BaseViewHolder implements CalendarDayWiseItemViewModel.CalendarItemViewModelListener {

        ListItemCalendarBinding mListItemCategoriesBinding;
        CalendarDayWiseItemViewModel mCategoriesItemViewModel;

        public CategoriesViewHolder(ListItemCalendarBinding binding) {
            super(binding.getRoot());
            this.mListItemCategoriesBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final CalendarDayWiseResponse.Result.Item blog = item_list.get(position);
            mCategoriesItemViewModel = new CalendarDayWiseItemViewModel(this, blog);
            mListItemCategoriesBinding.setCalendarItemViewModel(mCategoriesItemViewModel);
            mListItemCategoriesBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(CalendarDayWiseResponse.Result.Item result) {
            mCategoriesAdapterListener.onItemClick(result);

        }
    }
}
