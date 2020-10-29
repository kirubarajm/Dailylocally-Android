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
import com.dailylocally.ui.favourites.FavActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.signup.SignUpActivity;
import com.dailylocally.ui.signup.registration.RegistrationActivity;
import com.dailylocally.ui.fandsupport.FeedbackSupportActivity;
import com.dailylocally.ui.transactionHistory.TransactionHistoryActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import javax.inject.Inject;

public class MyAccountFragment extends BaseBottomSheetFragment<FragmentMyAccountBinding, MyAccountViewModel> implements MyAccountNavigator {

    public static final String TAG = MyAccountFragment.class.getSimpleName();
    @Inject
    MyAccountViewModel mMyAccountViewModel;

    FragmentMyAccountBinding mFragmentMyAccountBinding;


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
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMyAccountViewModel.toolbarTitle.set(getString(R.string.my_account));
    }

    @Override
    public void onResume() {
        super.onResume();
        mMyAccountViewModel.loadUserDetails();

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void changeAddress() {
        Intent intent = ViewAddressActivity.newIntent(getContext());
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void orderHistory() {
        Intent intent = CalendarActivity.newIntent(getContext());
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void couponsAndOffers() {
        Intent intent = CouponsActivity.newIntent(getContext());
        intent.putExtra(AppConstants.PAGE,AppConstants.NOTIFY_MYACCOUNT_FRAG);
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void favourites() {
        Intent intent = FavActivity.newIntent(getContext());
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void referrals() {
        Intent intent = ReferralsActivity.newIntent(getContext());
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void favorites() {

    }

    @Override
    public void offers() {
    }

    @Override
    public void logout() {
        SharedPreferences settings = getBaseActivity().getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().apply();

        Intent intent = SignUpActivity.newIntent(getActivity());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        getActivity().finish();
    }

    @Override
    public void logoutFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void feedbackAndSupport() {
        Intent intent = FeedbackSupportActivity.newIntent(getContext());
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void editProfile() {
        Intent intent = RegistrationActivity.newIntent(getContext());
        intent.putExtra("edit","1");
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void transactions() {
        Intent intent = TransactionHistoryActivity.newIntent(getContext());
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void gotoCommunity() {
        ((MainActivity)getActivity()).openCommunity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
