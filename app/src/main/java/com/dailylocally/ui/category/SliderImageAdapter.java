package com.dailylocally.ui.category;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dailylocally.R;
import com.dailylocally.ui.category.l1.L1CategoryResponse;
import com.dailylocally.ui.category.l2.L2CategoryResponse;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;


/*<com.smarteist.autoimageslider.SliderView
        android:id="@+id/imageSlider"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        app:sliderAnimationDuration="600"
        app:sliderAutoCycleDirection="back_and_forth"
        app:sliderAutoCycleEnabled="true"
        app:sliderIndicatorAnimationDuration="600"
        app:sliderIndicatorGravity="center_horizontal|bottom"
        app:sliderIndicatorMargin="15dp"
        app:sliderIndicatorOrientation="horizontal"
        app:sliderIndicatorPadding="3dp"
        app:sliderIndicatorRadius="2dp"
        app:sliderIndicatorSelectedColor="#5A5A5A"
        app:sliderIndicatorUnselectedColor="#FFF"
        app:sliderScrollTimeInSec="1"
        app:sliderStartAutoCycle="true" />*/


 /*SliderView sliderView = findViewById(R.id.imageSlider);

         SliderAdapterExample adapter = new SliderAdapterExample(this);

         sliderView.setSliderAdapter(adapter);

         sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
         sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
         sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
         sliderView.setIndicatorSelectedColor(Color.WHITE);
         sliderView.setIndicatorUnselectedColor(Color.GRAY);
         sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
         sliderView.startAutoCycle();*/



/*

public class SliderImageAdapter extends
        SliderViewAdapter<SliderImageAdapter.SliderAdapterVH> {

    private Context context;
    private List<L2CategoryResponse.> mSliderItems = new ArrayList<>();

    public SliderImageAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        SliderItem sliderItem = mSliderItems.get(position);

        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImageUrl())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}*/
