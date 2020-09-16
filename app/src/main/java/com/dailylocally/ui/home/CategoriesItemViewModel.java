package com.dailylocally.ui.home;


import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

public class CategoriesItemViewModel {


    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> videourl = new ObservableField<>();
    public final ObservableBoolean isVideo = new ObservableBoolean();

    public final CategoriesItemViewModelListener mListener;
    private final HomepageResponse.Result result;


    public CategoriesItemViewModel(CategoriesItemViewModelListener mListener, HomepageResponse.Result result) {
        this.mListener = mListener;
        this.result = result;
        image.set(result.getImage());
        if (result.getShowVideo() != null) {
            isVideo.set(result.getShowVideo());

if (result.getShowVideo())
    mListener.updateVideoView();
        }


        if (result.getCid() != null) {
            name.set("");

        } else {
            name.set(result.getName());
        }



        // name.set("Abcdefghijklmnopqrstuvwxyz a b c d e f g h i j k l m n o p q r s t u v w x y z ");
    }


    public void onItemClick() {
        if (result.getClickable())
            mListener.onItemClick(result);

    }


    public interface CategoriesItemViewModelListener {
        void onItemClick(HomepageResponse.Result result);
        void updateVideoView();
    }

}
