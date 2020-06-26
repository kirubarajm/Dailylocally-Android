package com.dailylocally.ui.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentMyAccountBinding;
import com.dailylocally.ui.account.referrals.ReferralsActivity;
import com.dailylocally.ui.address.viewAddress.ViewAddressActivity;
import com.dailylocally.ui.base.BaseBottomSheetFragment;
import com.dailylocally.ui.calendarView.CalendarActivity;
import com.dailylocally.ui.coupons.CouponsActivity;
import com.dailylocally.ui.signup.SignUpActivity;
import com.dailylocally.ui.signup.registration.RegistrationActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import javax.inject.Inject;

public class MyAccountFragment extends BaseBottomSheetFragment<FragmentMyAccountBinding, MyAccountViewModel> implements MyAccountNavigator {

    public static final String TAG = MyAccountFragment.class.getSimpleName();
    @Inject
    MyAccountViewModel mMyAccountViewModel;

    FragmentMyAccountBinding mFragmentMyAccountBinding;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_MY_ACCOUNT;


    public static MyAccountFragment newInstance() {
        Bundle args = new Bundle();
        MyAccountFragment fragment = new MyAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.myAccountViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_account;
    }

    @Override
    public MyAccountViewModel getViewModel() {
        return mMyAccountViewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentMyAccountBinding = getViewDataBinding();
        mMyAccountViewModel.setNavigator(this);
        analytics = new Analytics(getActivity(), pageName);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMyAccountViewModel.toolbarTitle.set(getString(R.string.my_account));
    }

    @Override
    public void onResume() {
        super.onResume();
        //mMyAccountViewModel.fetchUserDetails();
        mMyAccountViewModel.loadUserDetails();

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void changeAddress() {

        new Analytics().sendClickData(pageName, AppConstants.CLICK_MANAGE_ADDRESS);

        /*Intent intent = GoogleAddressActivity.newIntent(getContext());
        intent.putExtra("edit","1");
        startActivity(intent);*/

        Intent intent = ViewAddressActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void orderHistory() {
        //new Analytics().sendClickData(pageName, AppConstants.CLICK_ORDER_HISTORY);
        Intent intent = CalendarActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void couponsAndOffers() {
        Intent intent = CouponsActivity.newIntent(getContext());
        intent.putExtra(AppConstants.PAGE,AppConstants.NOTIFY_MYACCOUNT_FRAG);
        startActivity(intent);
    }

    @Override
    public void favourites() {
    }

    @Override
    public void referrals() {
        //new Analytics().sendClickData(pageName, AppConstants.CLICK_REFERRALS);
        Intent intent = ReferralsActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void favorites() {

    }

    @Override
    public void offers() {
    }

    @Override
    public void logout() {

        //new Analytics().sendClickData(pageName, AppConstants.CLICK_LOGOUT);

        SharedPreferences settings = getBaseActivity().getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().apply();

        Intent intent = SignUpActivity.newIntent(getActivity());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void logoutFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void feedbackAndSupport() {
        //new Analytics().sendClickData(pageName, AppConstants.CLICK_FEEDBACK_SUPPORT);

        //Intent intent = FeedbackAndSupportActivity.newIntent(getContext());
        //startActivity(intent);
    }

    @Override
    public void editProfile() {
        Intent intent = RegistrationActivity.newIntent(getContext());
        intent.putExtra("edit","1");
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
