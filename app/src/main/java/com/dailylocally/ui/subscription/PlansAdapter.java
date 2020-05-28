package com.dailylocally.ui.subscription;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dailylocally.R;

import java.util.ArrayList;
import java.util.List;

public class PlansAdapter extends BaseAdapter {
    List<SubscriptionResponse.SubscriptionPlan> subscriptionPlans = new ArrayList<>();
    LayoutInflater inflter;
    Context context;
    planListener mPlanListener;
    public PlansAdapter(List<SubscriptionResponse.SubscriptionPlan> subscriptionPlans, Context context, planListener planListener) {
        this.subscriptionPlans = subscriptionPlans;
        inflter = (LayoutInflater.from(context));
        this.context = context;
        this.mPlanListener=planListener;
    }

    @Override
    public int getCount() {
        return subscriptionPlans.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflter.inflate(R.layout.list_item_plans, null);
        TextView plan=convertView.findViewById(R.id.plan);

        plan.setText(subscriptionPlans.get(position).getPlanName());


        return convertView;
    }
    interface planListener{
        void selectedPlan(SubscriptionResponse.SubscriptionPlan subscriptionPlan);
    }
}
