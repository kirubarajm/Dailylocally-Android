package com.dailylocally.utilities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.ui.category.viewall.CatProductActivity;
import com.dailylocally.ui.collection.l2.CollectionDetailsActivity;
import com.dailylocally.ui.community.event.EventActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.ui.splash.SplashActivity;
import com.dailylocally.ui.transactionHistory.TransactionHistoryActivity;
import com.dailylocally.ui.transactionHistory.view.TransactionDetailsActivity;

import java.util.Map;

public class PageNavigator {
    Context mContext;
    Intent intent;
    public PageNavigator(Context mContext, Intent intent) {
        this.mContext = mContext;
        this.intent = intent;
        mContext.startActivity(intent);
     //   openPage();
    }
    
    public void openPage(){
        Bundle bundle = new Bundle();
        Intent intent = null;
        String pageId = "0";
        pageId=bundle.getString("pageid");

        if (pageId == null) pageId = "0";

        switch (pageId) {
            case AppConstants.NOTIFY_CATEGORY_L1_ACTV:
                intent = CategoryL1Activity.newIntent(mContext);
                bundle.putString("catid", bundle.getString("catid"));
                break;
            case AppConstants.NOTIFY_CATEGORY_L2_ACTV:
                intent = CategoryL2Activity.newIntent(mContext);
                bundle.putString("catid", bundle.getString("catid"));
                bundle.putString("scl1id", bundle.getString("scl1id"));
                break;
            case AppConstants.NOTIFY_CATEGORY_L1_PROD_ACTV:
                intent = CatProductActivity.newIntent(mContext);
                bundle.putString("catid", bundle.getString("catid"));
                break;
            case AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG:
                intent = MainActivity.newIntent(mContext, AppConstants.NOTIFY_COMMUNITY_CATLIST_FRAG, AppConstants.NOTIFY_COMMUNITY_ACTV);
                break;
            case AppConstants.NOTIFY_TRANS_LIST_ACTV:
                intent = TransactionHistoryActivity.newIntent(mContext);
                break;
            case AppConstants.NOTIFY_TRANS_DETAILS_ACTV:
                intent = TransactionDetailsActivity.newIntent(mContext);
                bundle.putString("orderid", bundle.getString("orderid"));
                break;
            case AppConstants.NOTIFY_PRODUCT_DETAILS_ACTV:
                intent = ProductDetailsActivity.newIntent(mContext);
                bundle.putString("vpid", bundle.getString("vpid"));
                break;
            case AppConstants.NOTIFY_COLLECTION_ACTV:
                intent = CollectionDetailsActivity.newIntent(mContext);
                bundle.putString("cid", bundle.getString("cid"));
                break;
            case AppConstants.NOTIFY_COMMUNITY_EVENT_POST:
                intent = EventActivity.newIntent(mContext, bundle.getString("topic"), bundle.getString("title"));
                break;

            default:
                intent = SplashActivity.newIntent(mContext);
        }

        intent.putExtras(bundle);
        mContext.startActivity(intent);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
    }
    
}
