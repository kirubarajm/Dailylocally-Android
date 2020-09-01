package com.dailylocally.ui.aboutus;


import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

public class AboutUsItemViewModel {

    public final ObservableField<String> type = new ObservableField<>();

    public final ObservableField<String> question = new ObservableField<>();

    public final ObservableField<String> answer = new ObservableField<>();

    public final ObservableField<String> faqid = new ObservableField<>();

    public final ObservableField<String> created_at = new ObservableField<>();

    public final ObservableBoolean expandClicked = new ObservableBoolean();


    public final FaqItemViewModelListener mListener;

    private final AboutUsResponse.Result mBlog;


    public AboutUsItemViewModel(AboutUsResponse.Result menuProducts, FaqItemViewModelListener listener) {

        this.type.set(menuProducts.getType());
        this.question.set(menuProducts.getQuestion());
        this.answer.set(menuProducts.getAnswer());
        this.faqid.set(menuProducts.getFaqid());

        this.mListener = listener;
        this.mBlog = menuProducts;
    }

    public void onItemClick() {
        mListener.onItemClick(mBlog.getType());
    }

    public void expandClick() {
        if (expandClicked.get()) {
            expandClicked.set(false);
        }else {
            expandClicked.set(true);
        }

    }


    public interface FaqItemViewModelListener {

        void onItemClick(String blogUrl);
    }

}
