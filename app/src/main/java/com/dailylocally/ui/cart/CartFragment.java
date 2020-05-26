package com.dailylocally.ui.cart;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentCartBinding;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.utilities.AppConstants;

import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.analytics.Analytics;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipView;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;
import static com.dailylocally.utilities.AppConstants.CART_REQUESTCODE;

public class CartFragment extends BaseFragment<FragmentCartBinding, CartViewModel> implements CartNavigator, OrderNowAdapter.OrderNowProductsAdapterListener, BillListAdapter.BilldetailsInfoListener {

    public ToolTipView myToolTipView;
    @Inject
    OrderNowAdapter mOrderNowAdapter;
    @Inject
    CartViewModel mCartViewModel;
    @Inject
    BillListAdapter billListAdapter;
    Dialog dialog;

    boolean infoClicked = false;
    private FragmentCartBinding mActivityCartBinding;


    public static CartFragment newInstance() {
        Bundle args = new Bundle();
        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getBindingVariable() {
        return BR.cartViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    public CartViewModel getViewModel() {

        return mCartViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCartViewModel.setNavigator(this);
        mOrderNowAdapter.setListener(this);
        billListAdapter.setListener(this);


    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivityCartBinding = getViewDataBinding();
        dialog = new Dialog(getBaseActivity());

        mCartViewModel.toolbarTitle.set(getString(R.string.cart));

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityCartBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mActivityCartBinding.recyclerviewOrders.setAdapter(mOrderNowAdapter);



        LinearLayoutManager billLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mActivityCartBinding.recyclerviewBill.setLayoutManager(billLayoutManager);
        mActivityCartBinding.recyclerviewBill.setAdapter(billListAdapter);




        subscribeToLiveData();



        mActivityCartBinding.cartScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (myToolTipView != null) {
                    myToolTipView.remove();
                    infoClicked = false;
                }
            }
        });

    }


    @Override
    public void cartLoaded() {
//        stopCartLoader();
    }

    @Override
    public void changeAddress() {

    }


    @Override
    public void showToast(String msg) {
        Toast.makeText(getBaseActivity(), msg, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void emptyCart() {
    }






    @Override
    public void redirectHome() {
        ((MainActivity) getActivity()).openHome();

    }

    @Override
    public void notServicable() {

       /* if (!dialog.isShowing())
            showDialog();*/
    }




    @Override
    public void clearToolTips() {
        if (myToolTipView != null) {
            myToolTipView.remove();
            infoClicked = false;
            myToolTipView = null;
        }
    }

    @Override
    public void metricsCartOpen() {
      //  metricsOpenCartPage();
    }

    private void subscribeToLiveData() {
        mCartViewModel.getOrdernowLiveData().observe(this,
                ordernowItemViewModel -> mCartViewModel.addOrderNowItemsToList(ordernowItemViewModel));

        mCartViewModel.getSubscribeLiveData().observe(this,
                subscriptionItemViewModel -> mCartViewModel.addSubscriptionItemsToList(subscriptionItemViewModel));

        mCartViewModel.getCartBillLiveData().observe(this,
                cartdetails -> mCartViewModel.addBillItemsToList(cartdetails));

    }


    @Override
    public void onResume() {
        super.onResume();
        mCartViewModel.setAddressTitle();

        if (mCartViewModel.getCartPojoDetails() != null) {
            mCartViewModel.fetchRepos();
        }
        if (myToolTipView != null) {
            myToolTipView.remove();
            infoClicked = false;
            myToolTipView = null;
        }
    }


    @Override
    public void reloadCart() {
        if (myToolTipView != null) {
            myToolTipView.remove();
            infoClicked = false;
            myToolTipView = null;
        }
        if (mCartViewModel.getCartPojoDetails() != null) {
            mCartViewModel.xfactorClick.set(false);
            mCartViewModel.fetchRepos();
            mCartViewModel.emptyCart.set(false);
        } else {
            mCartViewModel.emptyCart.set(true);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CART_REQUESTCODE) {
            boolean status = data.getBooleanExtra("status", false);
            if (status) {
                //    mCartViewModel.paymentModeCheck();

            }
        } else if (requestCode == AppConstants.REFUND_LIST_CODE) {

            if (resultCode == RESULT_OK) {
                mCartViewModel.fetchRepos();
            }
        } else if (requestCode == AppConstants.COUPON_LIST_CODE) {
            if (resultCode == RESULT_OK) {
                mCartViewModel.fetchRepos();
            }


        } else if (requestCode == AppConstants.SELECT_ADDRESS_LIST_CODE) {
            if (resultCode == RESULT_OK) {
                mCartViewModel.fetchRepos();
            }
        }
    }


    @Override
    public void infoClick(CartResponse.Cartdetail cartdetail, ImageView imageView) {


        if (!infoClicked) {
            if (cartdetail.getInfostatus()) {
                infoClicked = true;
                ToolTip toolTip = new ToolTip()
                        .withContentView(LayoutInflater.from(DailylocallyApp.getInstance()
                         ).inflate(R.layout.tool_tip_cart_info, null))
                        // .withText("Now delivering to "+mHomeTabViewModel.getDataManager().getCurrentAddress())
                        .withColor(DailylocallyApp.getInstance().getResources().getColor(R.color.light_gray))
                        .withShadow()
                        .withTextColor(Color.BLACK)
                        .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW);
                myToolTipView = mActivityCartBinding.activityMainTooltipframelayout.showToolTipForView(toolTip, imageView);
                TextView title = myToolTipView.findViewById(R.id.activity_main_redtv);
                StringBuilder sTitle = new StringBuilder();
                for (int i = 0; i < cartdetail.getInfodetails().size(); i++) {
                    sTitle.append("\n").append(cartdetail.getInfodetails().get(i).getName()).append("    ").append("Rs.").append(cartdetail.getInfodetails().get(i).getPrice());
                }
                title.setText(sTitle.toString());

                myToolTipView.setOnToolTipViewClickedListener(new ToolTipView.OnToolTipViewClickedListener() {
                    @Override
                    public void onToolTipViewClicked(ToolTipView toolTipView) {

                        if (myToolTipView != null) {
                            myToolTipView.remove();
                            infoClicked = false;
                        }
                    }
                });
            }
        } else {
            if (myToolTipView != null) {
                myToolTipView.remove();
                infoClicked = false;
                myToolTipView = null;
            }
        }


        mActivityCartBinding.cart.setOnTouchListener((v, event) -> {
            if (myToolTipView != null) {
                myToolTipView.remove();
                infoClicked = false;
                myToolTipView = null;
            }
            return true;
        });

    }


}