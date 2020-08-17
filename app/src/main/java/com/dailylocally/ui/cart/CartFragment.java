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
import com.dailylocally.ui.address.googleAddress.GoogleAddressActivity;
import com.dailylocally.ui.base.BaseFragment;
import com.dailylocally.ui.coupons.CouponsActivity;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.ui.subscription.SubscriptionActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.datepicker.DatePickerActivity;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;
import static com.dailylocally.utilities.AppConstants.CART_REQUESTCODE;

public class CartFragment extends BaseFragment<FragmentCartBinding, CartViewModel> implements CartNavigator, OrderNowAdapter.OrderNowProductsAdapterListener, BillListAdapter.BilldetailsInfoListener, SubscribeItemsAdapter.SubscribeProductsAdapterListener {

    public ToolTipView myToolTipView;
    @Inject
    OrderNowAdapter mOrderNowAdapter;
    @Inject
    SubscribeItemsAdapter mSubscribeItemsAdapter;
    @Inject
    CartViewModel mCartViewModel;
    @Inject
    BillListAdapter billListAdapter;
    Dialog dialog;
    CartResponse.Item product;
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
        mSubscribeItemsAdapter.setListener(this);
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


        LinearLayoutManager mLayoutManager2
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityCartBinding.recyclerviewSubscribe.setLayoutManager(mLayoutManager2);
        mActivityCartBinding.recyclerviewSubscribe.setAdapter(mSubscribeItemsAdapter);


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
        stopCartLoader();
    }

    public void stopCartLoader() {
        mActivityCartBinding.cartLoader.stopShimmerAnimation();
        mActivityCartBinding.cartLoader.setVisibility(View.GONE);
    }

    public void startCartLoader() {
        mActivityCartBinding.cartLoader.setVisibility(View.VISIBLE);
        mActivityCartBinding.cartLoader.startShimmerAnimation();
    }

    @Override
    public void changeAddress() {
        Intent intent = GoogleAddressActivity.newIntent(getContext());
        intent.putExtra("edit", "1");
        startActivity(intent);
    }


    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void emptyCart() {
        stopCartLoader();
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

    @Override
    public void orderGenerated(String orderId, String customerId, String amount) {

        ((MainActivity) getActivity()).makePayment(orderId, customerId, amount);
    }

    @Override
    public void applyCoupon() {
        Intent intent = CouponsActivity.newIntent(getContext());
        intent.putExtra(AppConstants.PAGE, AppConstants.NOTIFY_CART_FRAG);
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

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
            startCartLoader();
            mCartViewModel.getStartDate();
        }else {
            stopCartLoader();
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
            startCartLoader();
            mCartViewModel.getStartDate();
            mCartViewModel.emptyCart.set(false);
        } else {
            mCartViewModel.emptyCart.set(true);
        }

        ((MainActivity) getActivity()).statusUpdate();

    }

    @Override
    public void edit(CartResponse.SubscriptionItem product) {


        Intent intent = SubscriptionActivity.newIntent(getContext());
        intent.putExtra("pid", String.valueOf(product.getPid()));
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void deleteSubcription(CartResponse.SubscriptionItem product) {
        startCartLoader();
        mCartViewModel.deleteSubsItem(product);
        ((MainActivity) getActivity()).statusUpdate();
    }

    @Override
    public void subsItemClick(CartResponse.SubscriptionItem product) {

        Intent intent = ProductDetailsActivity.newIntent(getContext());
        intent.putExtra("vpid", String.valueOf(product.getPid()));
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public String parseDateToddMMyyyy(String time) {
        String outputPattern = "yyyy-MM-dd";
        String inputPattern = "dd-MM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public String changeDate(CartResponse.Item product) {

        this.product = product;


        Intent intent = DatePickerActivity.newIntent(getBaseActivity());
        intent.putExtra("date", (product.getStarting_date()));
        startActivityForResult(intent, AppConstants.DATE_REQUESTCODE);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

       /* String currentTime = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        SimpleDateFormat sDay = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat sMonth = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat sYear = new SimpleDateFormat("yyyy", Locale.getDefault());


        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

      *//*  Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        String tomorrowDate =dateFormat.format(tomorrow);

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dat = calendar.getTime();

        String dayAftertomorrowDate =dateFormat.format(dat);*//*

        int year = 0;
        int month = 0;
        int day = 0;


        Calendar calendarT = Calendar.getInstance();
        calendarT.add(Calendar.DAY_OF_YEAR, 1);
        Date selectableDate = calendarT.getTime();

        if (Integer.parseInt(currentTime) < 14) {
            year = calendarT.get(Calendar.YEAR);
            month = calendarT.get(Calendar.MONTH);
            day = calendarT.get(Calendar.DAY_OF_MONTH);
        } else {

            calendarT.add(Calendar.DAY_OF_YEAR, 1);
            selectableDate = calendarT.getTime();

            year = calendarT.get(Calendar.YEAR);
            month = calendarT.get(Calendar.MONTH);
            day = calendarT.get(Calendar.DAY_OF_MONTH);
        }
        final String[] date = new String[1];
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date[0] = dayOfMonth + "-" + month + "-" + year;
              mCartViewModel.productDateChange(date[0],product);

            }
        }, year, month, day);
        dialog.getDatePicker().setMinDate(selectableDate.getTime());
        dialog.show();*/

        return "";
    }

    @Override
    public void subscribe(CartResponse.Item product) {

        Intent intent = SubscriptionActivity.newIntent(getContext());
        intent.putExtra("pid", String.valueOf(product.getPid()));
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void orderNowItemClick(CartResponse.Item product) {

        Intent intent = ProductDetailsActivity.newIntent(getContext());
        intent.putExtra("vpid", String.valueOf(product.getPid()));
        startActivity(intent);
        getBaseActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                startCartLoader();
                mCartViewModel.getStartDate();

            }
        } else if (requestCode == AppConstants.COUPON_LIST_CODE) {
            if (resultCode == RESULT_OK) {
                startCartLoader();
                mCartViewModel.getStartDate();
            }


        } else if (requestCode == AppConstants.SELECT_ADDRESS_LIST_CODE) {
            if (resultCode == RESULT_OK) {
                startCartLoader();
                mCartViewModel.getStartDate();
            }
        } else if (requestCode == AppConstants.DATE_REQUESTCODE) {

            if (resultCode == RESULT_OK) {
                startCartLoader();
                mCartViewModel.productDateChange(parseDateToYYYYMMDD(data.getStringExtra("date")), product);

            }

        }
    }

    public String parseDateToYYYYMMDD(String time) {
        String outputPattern = "yyyy-MM-dd";
        String inputPattern = "dd-MM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
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
                        .withAnimationType(ToolTip.AnimationType.NONE);
                myToolTipView = mActivityCartBinding.activityMainTooltipframelayout.showToolTipForView(toolTip, imageView);
                //   myToolTipView = relativeLayout.showToolTipForView(toolTip,imageView);
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