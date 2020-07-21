/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.dailylocally.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;


import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dailylocally.ui.calendarView.CalendarDayWiseAdapter;
import com.dailylocally.ui.calendarView.CalendarDayWiseResponse;
import com.dailylocally.ui.cart.BillListAdapter;
import com.dailylocally.ui.cart.CartResponse;
import com.dailylocally.ui.cart.OrderNowAdapter;
import com.dailylocally.ui.cart.SubscribeItemsAdapter;
import com.dailylocally.ui.category.l1.L1CategoriesAdapter;
import com.dailylocally.ui.category.l1.L1CategoryResponse;
import com.dailylocally.ui.category.l2.L2CategoryResponse;
import com.dailylocally.ui.category.l2.products.ProductListAdapter;
import com.dailylocally.ui.category.l2.products.ProductsResponse;
import com.dailylocally.ui.category.l2.products.filter.FilterAdapter;
import com.dailylocally.ui.category.l2.products.filter.FilterItems;
import com.dailylocally.ui.category.l2.products.sort.SortAdapter;
import com.dailylocally.ui.category.l2.products.sort.SortItems;

import com.dailylocally.ui.category.l2.slider.L2SliderAdapter;

import com.dailylocally.ui.collection.l2.products.CollectionProductListAdapter;
import com.dailylocally.ui.collection.l2.products.CollectionProductsResponse;
import com.dailylocally.ui.coupons.CouponsAdapter;
import com.dailylocally.ui.coupons.CouponsResponse;

import com.dailylocally.ui.favourites.products.FavProductListAdapter;
import com.dailylocally.ui.favourites.products.FavProductsResponse;
import com.dailylocally.ui.rating.RatingDayWiseAdapter;
import com.dailylocally.ui.search.QuickSearchResponse;
import com.dailylocally.ui.search.SearchProductListAdapter;
import com.dailylocally.ui.search.SearchProductResponse;
import com.dailylocally.ui.search.SearchSubCategoryAdapter;
import com.dailylocally.ui.search.SearchSuggestionAdapter;
import com.dailylocally.ui.transactionHistory.TransactionHistoryAdapter;
import com.dailylocally.ui.transactionHistory.TransactionHistoryResponse;
import com.dailylocally.ui.transactionHistory.view.TransactionBillDetailAdapter;
import com.dailylocally.ui.transactionHistory.view.TransactionProductAdapter;
import com.dailylocally.ui.transactionHistory.view.TransactionViewResponse;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.dailylocally.R;
import com.dailylocally.ui.signup.faqs.FaqResponse;
import com.dailylocally.ui.signup.faqs.FaqsAdapter;
import com.dailylocally.utilities.chat.IssuesAdapter;
import com.dailylocally.utilities.chat.IssuesListResponse;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * Created by amitshekhar on 11/07/17.
 */

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }




    @BindingAdapter({"adapter"})
    public static void addIssuesItems(RecyclerView recyclerView, List<IssuesListResponse.Result> issues) {
        IssuesAdapter adapter = (IssuesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(issues);
        }
    }

 @BindingAdapter({"adapter"})
    public static void addL1CategoryItems(RecyclerView recyclerView, List<L1CategoryResponse.Result> results) {
        L1CategoriesAdapter adapter = (L1CategoriesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(results);
        }
    }
 @BindingAdapter({"filteradapter"})
    public static void addFilterItems(RecyclerView recyclerView, List<FilterItems.Result> results) {
        FilterAdapter adapter = (FilterAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(results);
        }
    }
 @BindingAdapter({"sortadapter"})
    public static void addSortItems(RecyclerView recyclerView, List<SortItems.Result> results) {
        SortAdapter adapter = (SortAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(results);
        }
    }

@BindingAdapter({"l2slideradapter"})
    public static void addL2SliderItems(SliderView recyclerView, List<L2CategoryResponse.GetSubCatImage> results) {
        L2SliderAdapter adapter = (L2SliderAdapter) recyclerView.getSliderAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(results);
        }
    }


 @BindingAdapter({"products_adapter"})
    public static void addCollectionProductItems(RecyclerView recyclerView, List<CollectionProductsResponse.Result> results) {
        CollectionProductListAdapter adapter = (CollectionProductListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(results);
        }
    }
@BindingAdapter({"products_adapter"})
    public static void addProductItems(RecyclerView recyclerView, List<ProductsResponse.Result> results) {
        ProductListAdapter adapter = (ProductListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(results,recyclerView);
        }
    }

@BindingAdapter({"products_adapter"})
    public static void addFavProductItems(RecyclerView recyclerView, List<FavProductsResponse.Result> results) {
        FavProductListAdapter adapter = (FavProductListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(results);
        }
    }

    @BindingAdapter({"day_adapter"})
    public static void addDayWiseCalendarItems(RecyclerView recyclerView, List<CalendarDayWiseResponse.Result.Item> results) {
        CalendarDayWiseAdapter adapter = (CalendarDayWiseAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(results);
        }
    }

@BindingAdapter({"billdetails_adapter"})
    public static void addBillItems(RecyclerView recyclerView, List<CartResponse.Cartdetail> results) {
        BillListAdapter adapter = (BillListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(results);
        }
    }

@BindingAdapter({"cart_ordernow_adapter"})
    public static void addOrderNowItems(RecyclerView recyclerView, List<CartResponse.Item> results) {
        OrderNowAdapter adapter = (OrderNowAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(results);
        }
    }

@BindingAdapter({"cart_subscribe_adapter"})
    public static void addSubscribeItems(RecyclerView recyclerView, List<CartResponse.SubscriptionItem> results) {
        SubscribeItemsAdapter adapter = (SubscribeItemsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(results);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addSearchItems(RecyclerView recyclerView, List<QuickSearchResponse.Result.ProductsList> response) {
        SearchSuggestionAdapter adapter = (SearchSuggestionAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();

            adapter.addItems(response);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addSearchSubCategoryItems(RecyclerView recyclerView, List<QuickSearchResponse.Result.SubcategoryList> response) {
        SearchSubCategoryAdapter adapter = (SearchSubCategoryAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();

            adapter.addItems(response);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addSearchProductItems(RecyclerView recyclerView, List<SearchProductResponse.Result> response) {
        SearchProductListAdapter adapter = (SearchProductListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();

            adapter.addItems(response);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addFaqItems(RecyclerView recyclerView, List<FaqResponse.Result> blogs) {
        FaqsAdapter adapter = (FaqsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(blogs);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addCouponsItems(RecyclerView recyclerView, List<CouponsResponse.Result> blogs) {
        CouponsAdapter adapter = (CouponsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(blogs);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addTransactionProductItems(RecyclerView recyclerView, List<TransactionViewResponse.Result.Item> blogs) {
        TransactionProductAdapter adapter = (TransactionProductAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(blogs);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addTransactionBillDetailItems(RecyclerView recyclerView, List<TransactionViewResponse.Result.Cartdetail> blogs) {
        TransactionBillDetailAdapter adapter = (TransactionBillDetailAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(blogs);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addRatingProductItems(RecyclerView recyclerView, List<CalendarDayWiseResponse.Result.Item> blogs) {
        RatingDayWiseAdapter adapter = (RatingDayWiseAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(blogs);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addTransactionHistoryItems(RecyclerView recyclerView, List<TransactionHistoryResponse.Result> blogs) {
        TransactionHistoryAdapter adapter = (TransactionHistoryAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(blogs);
        }
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(RoundCornerImageView imageView, String url) {
        Context context = imageView.getContext();


        Glide.with(context)
                .load(url)
             //   .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
               // .listener(new LoggingListener<String, Bitmap>())
                .into(imageView);


    }




    @BindingAdapter({"imageUrl","loader"})
    public static void setImageUrl(ImageView imageView, String url, final ImageView loader) {
        Context context = imageView.getContext();

        loader.setVisibility(View.VISIBLE);
        Glide.with(context).load(R.raw.img_loader).into(loader);

        Glide.with(context)
                .load(url)
              //  .asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        loader.setVisibility(View.GONE );
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        loader.setVisibility(View.GONE );
                        return false;
                    }
                })
                .error(R.drawable.imagenotavailable)
                .into(imageView);


    }
 @BindingAdapter({"imageimageUrl","loader"})
    public static void setImageImageUrl(ImageView imageView, String url, final ImageView loader) {
        Context context = imageView.getContext();

        loader.setVisibility(View.VISIBLE);
        Glide.with(context).load(R.raw.img_loader).into(loader);

        Glide.with(context)
                .load(url)
              //  .asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        loader.setVisibility(View.GONE );
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        loader.setVisibility(View.GONE );
                        return false;
                    }
                })

                .into(imageView);


    }

    @BindingAdapter({"roundimageUrl","roundloader"})
    public static void setRoundImageUrl(RoundCornerImageView imageView, String url, ImageView loader) {
        Context context = imageView.getContext();

        loader.setVisibility(View.VISIBLE);




    }

@BindingAdapter({"imageUrl","shimmer"})
    public static void setImageUrl(ImageView imageView, String url, final ShimmerFrameLayout loader) {
        Context context = imageView.getContext();

        loader.setVisibility(View.VISIBLE);
        loader.startShimmerAnimation();
    Glide.with(context)
            .load(url)
            //  .asBitmap()
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    loader.setVisibility(View.GONE );
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    loader.setVisibility(View.GONE );
                    return false;
                }
            })
            .error(R.drawable.imagenotavailable)
            .into(imageView);

    }




        @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();

        Glide.with(context)
                .load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);


    }

   public static Drawable getProgressBarIndeterminate() {
        final int[] attrs = {android.R.attr.indeterminateDrawable};
        final int attrs_indeterminateDrawable_index = 0;
        TypedArray a = DailylocallyApp.getInstance().obtainStyledAttributes(android.R.style.Widget_Material_ProgressBar_Small, attrs);
        try {
            return a.getDrawable(attrs_indeterminateDrawable_index);
        } finally {
            a.recycle();
        }
    }

 @BindingAdapter("enter")
    public static void closeKeyboaard(final EditText editText, boolean status) {
        final Context context = editText.getContext();

     editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
         @Override
         public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
             if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                 InputMethodManager inputMethodManager = (InputMethodManager)context. getSystemService(INPUT_METHOD_SERVICE);
                 inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);

             }
             return false;
         }
     });
    }
@BindingAdapter("closekey")
    public static void closeSoftKeyboaard(ImageView view, boolean status) {
        Context context = view.getContext();

    InputMethodManager inputMethodManager = (InputMethodManager)context. getSystemService(INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    @BindingAdapter("cusrsiveImageUrl")
    public static void setCursiveImageUrl(RoundCornerImageView imageView, String url) {
        Context context = imageView.getContext();

        Glide.with(context)
                .load(url)
                //.asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.crossFade()
                .into(imageView);


    }

    @BindingAdapter("setBitmap")
    public static void setBitmap(ImageView imageView, Bitmap bitmap) {
        Context context = imageView.getContext();
        imageView.setImageBitmap(bitmap);
    }

    @BindingAdapter({"setWebViewClient"})
    public static void setWebViewClient(WebView view, WebViewClient client) {
        view.setWebViewClient(client);
    }

    @BindingAdapter({"loadUrl"})
    public static void loadUrl(WebView view, String url) {
        view.loadUrl(url);
    }

    @BindingAdapter({"videoUrl"})
    public static void setVideourl(VideoView view, String url) {
        if (url != null) {
            view.setVideoURI(Uri.parse(url));
            view.requestFocus();
        }
    }

    @BindingAdapter("touchListener")
    public void setTouchListener(View self, boolean value) {
        self.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // Check if the button is PRESSED
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //do some thing
                    view.setBackgroundColor(R.color.dl_primary_color);

                }// Check if the button is RELEASED
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //do some thing
                }
                return false;
            }
        });
    }


}


