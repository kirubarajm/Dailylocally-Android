package com.dailylocally.ui.calendarView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.FragmentCalendarBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.fandsupport.help.HelpActivity;
import com.dailylocally.ui.productDetail.productDetailCancel.ProductCancelActivity;
import com.dailylocally.ui.rating.RatingActivity;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.analytics.Analytics;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

public class CalendarActivity extends BaseActivity<FragmentCalendarBinding, CalendarViewModel> implements CalendarNavigator,
        CalendarDayWiseAdapter.CategoriesAdapterListener {
    @Inject
    CalendarViewModel mCalendarViewModel;
    @Inject
    CalendarDayWiseAdapter mCalendarDayWiseAdapter;

    FragmentCalendarBinding mFragmentHomeBinding;
    FragmentTransaction t;

    String startDate = "", endDate = "";
    Date cDate = null, sDates = null;

    Date selectedDatetemp = null;
    List<Date> dateList = new ArrayList<>();
    Date date1 = null;
    Date date2 = null;
    Date date3 = null;
    Date date4 = null;
    Date date5 = null;
    Date date6 = null;
    String selectedDateInit = "", userFirstOrderDate = "";
    Date FirstOrderDate = null;
    List<CalendarMonthResponse.Result> results = new ArrayList<>();
    CaldroidListener listener = null;
    Date dateRating = null;
    String date = "";
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(getApplicationContext());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

        }
    };
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;

    public static Intent newIntent(Context context, String fromPage, String ToPage, String date) {
        Intent intent = new Intent(context, CalendarActivity.class);
        intent.putExtra(AppConstants.FROM, fromPage);
        intent.putExtra(AppConstants.PAGE, ToPage);
        intent.putExtra("date", date);
        return intent;
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private void unregisterWifiReceiver() {
        if (mWifiReceiver != null)
            unregisterReceiver(mWifiReceiver);
    }

    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                && networkInfo.isConnected()) {
            return true;
        } else return networkInfo != null
                && networkInfo.isConnected();
    }

    @Override
    public int getBindingVariable() {
        return BR.calendarViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_calendar;
    }

    @Override
    public CalendarViewModel getViewModel() {
        return mCalendarViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void success(List<CalendarMonthResponse.Result> resultsList) {
        this.results = resultsList;
        for (int i = 0; i < resultsList.size(); i++) {
            setCalTextColor(resultsList.get(i).getDate());
        }
    }

    public void setCalTextColor(String date) {
        java.text.DateFormat dateFormat22 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dd = dateFormat22.parse(date);
            caldroidFragment.setTextColorForDate(R.color.dl_primary_color, dd);
            caldroidFragment.refreshView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void stopLoader() {
        mFragmentHomeBinding.pageLoader.stopShimmerAnimation();
        mFragmentHomeBinding.pageLoader.setVisibility(View.GONE);
    }

    public void startLoader() {
        mFragmentHomeBinding.pageLoader.setVisibility(View.VISIBLE);
        mFragmentHomeBinding.pageLoader.startShimmerAnimation();
    }

    @Override
    public void failure(String message) {
        stopLoader();
    }

    @Override
    public void ratingClick() {
        try {
            Intent intent = RatingActivity.newIntent(CalendarActivity.this, AppConstants.SCREEN_NAME_CALENDAR, AppConstants.SCREEN_NAME_RATING);
            intent.putExtra("date", dateRating.getTime());
            intent.putExtra("doid", mCalendarViewModel.doid.get());
            startActivityForResult(intent, AppConstants.RATING_REQUEST_CODE);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void helpClick() {

        int type = 1;
        if (mCalendarViewModel.dayOrderStatus < 10) {
            type = AppConstants.CHAT_PAGE_TYPE_PROGRESS_ORDER;
        } else {
            type = AppConstants.CHAT_PAGE_TYPE_COMPLETED_ORDER;
        }

        Intent intent = HelpActivity.newIntent(CalendarActivity.this, AppConstants.NOTIFY_SUPPORT_ACTV, type, mCalendarViewModel.doid.get(),
                AppConstants.SCREEN_NAME_CALENDAR, AppConstants.SCREEN_NAME_HELP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void dataLoaded() {
        stopLoader();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCalendarViewModel.setNavigator(this);
        mFragmentHomeBinding = getViewDataBinding();
        mCalendarDayWiseAdapter.setListener(this);

        caldroidFragment = new CaldroidFragment();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            date = bundle.getString("date");
        }

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");


        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        } else {

            if (date != null) {

                SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    dateRating = dbFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
                SimpleDateFormat dateDayFormat = new SimpleDateFormat("dd, EEEE");
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                String dateDay = dateDayFormat.format(dateRating);
                String yearString = yearFormat.format(dateRating);
                String monthString = monthFormat.format(dateRating);

                Bundle args = new Bundle();
                args.putInt(CaldroidFragment.MONTH, Integer.parseInt(monthString));
                args.putInt(CaldroidFragment.YEAR, Integer.parseInt(yearString));
                args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
                args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, false);
                caldroidFragment.setArguments(args);




            } else {

                Bundle args = new Bundle();
                Calendar cal = Calendar.getInstance();
                args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
                args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
                args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
                args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, false);

                caldroidFragment.setArguments(args);
            }
        }
        t = this.getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                dateRating = date;
                startLoader();
                mCalendarViewModel.getDayWiseOrderDetails(date);
                caldroidFragment.clearSelectedDates();
                caldroidFragment.setSelectedDate(date);

                String outputDateStr = "", dateStrsdf;
                try {
                    if (date != null) {
                        java.text.DateFormat dateFormat = new SimpleDateFormat("dd,EEEE");
                        java.text.DateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
                        outputDateStr = dateFormat.format(date);
                        dateStrsdf = dateFormat1.format(date);
                        mFragmentHomeBinding.txtDate.setText(outputDateStr);
                        mCalendarViewModel.rateDeliveryButton.set("Rate Delivery " + dateStrsdf);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onChangeMonth(int month, int year) {
                mCalendarViewModel.getMonthWiseOrderDate(String.valueOf(month), String.valueOf(year));
            }

            @Override
            public void onLongClickDate(Date date, View view) {
            }

            @Override
            public void onCaldroidViewCreated() {

            }
        };

        caldroidFragment.setCaldroidListener(listener);
        try {
            java.text.DateFormat dateFormatSelected = new SimpleDateFormat("dd-MM-yyyy");
            java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            FirstOrderDate = dateFormat.parse(userFirstOrderDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentHomeBinding.recyclerDayWiseOrder.setLayoutManager(mLayoutManager);
        mFragmentHomeBinding.recyclerDayWiseOrder.setAdapter(mCalendarDayWiseAdapter);

        subscribeToLiveData();


        try {
            if (date == null) {
                Date currentDate = Calendar.getInstance().getTime();////current date
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
                SimpleDateFormat dateDayFormat = new SimpleDateFormat("dd, EEEE");
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                String rateDelivery = dateFormat1.format(currentDate);
                String dateDay = dateDayFormat.format(currentDate);
                String yearString = yearFormat.format(currentDate);
                String monthString = monthFormat.format(currentDate);
                mCalendarViewModel.getMonthWiseOrderDate(monthString, yearString);
                caldroidFragment.setSelectedDate(currentDate);
                caldroidFragment.refreshView();
                dateRating = currentDate;
                startLoader();
                mCalendarViewModel.getDayWiseOrderDetails(currentDate);
                mCalendarViewModel.rateDeliveryButton.set("Rate Delivery " + rateDelivery);
                mCalendarViewModel.dateDay.set(dateDay);
            } else if (date.isEmpty()) {
                Date currentDate = Calendar.getInstance().getTime();////current date
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
                SimpleDateFormat dateDayFormat = new SimpleDateFormat("dd, EEEE");
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                String rateDelivery = dateFormat1.format(currentDate);
                String dateDay = dateDayFormat.format(currentDate);
                String yearString = yearFormat.format(currentDate);
                String monthString = monthFormat.format(currentDate);
                mCalendarViewModel.getMonthWiseOrderDate(monthString, yearString);
                caldroidFragment.setSelectedDate(currentDate);
                caldroidFragment.refreshView();
                dateRating = currentDate;
                startLoader();
                mCalendarViewModel.getDayWiseOrderDetails(currentDate);
                mCalendarViewModel.rateDeliveryButton.set("Rate Delivery " + rateDelivery);
                mCalendarViewModel.dateDay.set(dateDay);
            } else {
                selectedDateAPICall(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        new Analytics().eventPageOpens(this, Objects.requireNonNull(intent.getExtras()).getString(AppConstants.FROM, "nil"),
                AppConstants.SCREEN_NAME_CALENDAR);
    }

    public void selectedDateAPICall(String datess) {
        try {
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            dateRating = dbFormat.parse(datess);

            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
            SimpleDateFormat dateDayFormat = new SimpleDateFormat("dd, EEEE");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            String rateDelivery = dateFormat1.format(dateRating);
            String dateDay = dateDayFormat.format(dateRating);
            String yearString = yearFormat.format(dateRating);
            String monthString = monthFormat.format(dateRating);
            mCalendarViewModel.getMonthWiseOrderDate(monthString, yearString);
            startLoader();
            mCalendarViewModel.getDayWiseOrderDetails(dateRating);
            mCalendarViewModel.rateDeliveryButton.set("Rate Delivery " + rateDelivery);
            mCalendarViewModel.dateDay.set(dateDay);

            caldroidFragment.setSelectedDate(dateRating);
            caldroidFragment.refreshView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    private void subscribeToLiveData() {
        mCalendarViewModel.getOrdernowLiveData().observe(this,
                ordernowItemViewModel -> mCalendarViewModel.addOrderNowItemsToList(ordernowItemViewModel));

    }

    @Override
    public void onResume() {
        super.onResume();
        registerWifiReceiver();
    }

    public void onDateSelected(Date selectedDate) {

        selectedDatetemp = selectedDate;

        Date currentDate = Calendar.getInstance().getTime();////current date
        if (selectedDate.equals(currentDate)) {
            Log.e("Cal", "" + selectedDate);
            Log.e("Cal", "" + currentDate);
            String sasf = (String) DateFormat.format("EEEE", selectedDate);
            String fgdf = (String) DateFormat.format("EEEE", currentDate);
        } else {
            if (selectedDate.before(currentDate)) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(selectedDate);
                int selectedDay = cal.get(Calendar.DAY_OF_WEEK);

                Log.e("Cal", "" + currentDate);
                if (selectedDay == Calendar.SUNDAY) {
                    Log.e("Cal", "" + currentDate);
                    //friday,saturday,SUNDAY,monday,tuesday,wednesday,thursday

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    Calendar calendar1 = Calendar.getInstance();
                    Calendar calendar2 = Calendar.getInstance();
                    Calendar calendar3 = Calendar.getInstance();
                    Calendar calendar4 = Calendar.getInstance();
                    Calendar calendar5 = Calendar.getInstance();
                    Calendar calendar6 = Calendar.getInstance();
                    calendar1.setTime(selectedDate);
                    calendar2.setTime(selectedDate);
                    calendar3.setTime(selectedDate);
                    calendar4.setTime(selectedDate);
                    calendar5.setTime(selectedDate);
                    calendar6.setTime(selectedDate);

                    calendar1.add(Calendar.DATE, -1);
                    calendar2.add(Calendar.DATE, -2);
                    calendar3.add(Calendar.DATE, -6);
                    calendar4.add(Calendar.DATE, -5);
                    calendar5.add(Calendar.DATE, -3);
                    calendar6.add(Calendar.DATE, -4);
                    String yesterdayAsString = dateFormat.format(calendar1.getTime());
                    String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
                    String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
                    String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
                    String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
                    String yesterdayAsString5 = dateFormat.format(calendar6.getTime());

                    startDate = yesterdayAsString2;
                    //endDate = yesterdayAsString2;

                    SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                    try {
                        date1 = dateFormatw.parse(yesterdayAsString);
                        date2 = dateFormatw.parse(yesterdayAsString1);
                        date3 = dateFormatw.parse(yesterdayAsString2);
                        date4 = dateFormatw.parse(yesterdayAsString3);
                        date5 = dateFormatw.parse(yesterdayAsString4);
                        date6 = dateFormatw.parse(yesterdayAsString5);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dateList = new ArrayList<>();
                    dateList.add(date1);
                    dateList.add(date2);
                    dateList.add(date3);
                    dateList.add(date4);
                    dateList.add(date5);
                    dateList.add(date6);
                    dateList.add(selectedDate);

                    Date cDate = null;
                    Date c = Calendar.getInstance().getTime();
                    Calendar calCurrent = Calendar.getInstance();
                    calCurrent.setTime(c);
                    String strCurrent = dateFormat.format(calCurrent.getTime());
                    try {
                        cDate = dateFormatw.parse(strCurrent);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date eDate = null;
                    for (int i = 0; i < dateList.size(); i++) {
                        if (cDate.equals(dateList.get(i))) {
                            eDate = dateList.get(i);
                            Log.e("", "" + eDate);
                        } else {
                            endDate = dateFormat.format(selectedDate);
                        }
                    }
                    if (eDate != null) {
                        endDate = dateFormat.format(eDate);
                    }

                    if (!selectedDate.before(FirstOrderDate)) {
                        ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                        caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);
                    }

                    if (!date1.before(FirstOrderDate)) {
                        if (date1.before(currentDate)) {
                            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue, date1);
                        }
                    }

                    if (!date2.before(FirstOrderDate)) {
                        if (date2.before(currentDate)) {
                            ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue1, date2);
                        }
                    }

                    if (!date3.before(FirstOrderDate)) {
                        if (date3.before(currentDate)) {
                            ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue2, date3);
                        }
                    }

                    if (!date4.before(FirstOrderDate)) {
                        if (date4.before(currentDate)) {
                            ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue3, date4);
                        }
                    }

                    if (!date5.before(FirstOrderDate)) {
                        if (date5.before(currentDate)) {
                            ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue4, date5);
                        }
                    }

                    if (!date6.before(FirstOrderDate)) {
                        if (date6.before(currentDate)) {
                            ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue5, date6);
                        }
                    }
                } else if (selectedDay == Calendar.MONDAY) {
                    Log.e("Cal", "" + currentDate);
                    //friday,saturday,sunday,MONDAY,tuesday,wednesday,thursday

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    Calendar calendar1 = Calendar.getInstance();
                    Calendar calendar2 = Calendar.getInstance();
                    Calendar calendar3 = Calendar.getInstance();
                    Calendar calendar4 = Calendar.getInstance();
                    Calendar calendar5 = Calendar.getInstance();
                    Calendar calendar6 = Calendar.getInstance();
                    calendar1.setTime(selectedDate);
                    calendar2.setTime(selectedDate);
                    calendar3.setTime(selectedDate);
                    calendar4.setTime(selectedDate);
                    calendar5.setTime(selectedDate);
                    calendar6.setTime(selectedDate);

                    calendar1.add(Calendar.DATE, +4);
                    calendar2.add(Calendar.DATE, +5);
                    calendar3.add(Calendar.DATE, +6);
                    calendar4.add(Calendar.DATE, +3);
                    calendar5.add(Calendar.DATE, +1);
                    calendar6.add(Calendar.DATE, +2);
                    String yesterdayAsString = dateFormat.format(calendar1.getTime());
                    String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
                    String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
                    String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
                    String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
                    String yesterdayAsString5 = dateFormat.format(calendar6.getTime());

                    startDate = dateFormat.format(selectedDate);
                    //endDate = yesterdayAsString3;

                    SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                    try {
                        date1 = dateFormatw.parse(yesterdayAsString);
                        date2 = dateFormatw.parse(yesterdayAsString1);
                        date3 = dateFormatw.parse(yesterdayAsString2);
                        date4 = dateFormatw.parse(yesterdayAsString3);
                        date5 = dateFormatw.parse(yesterdayAsString4);
                        date6 = dateFormatw.parse(yesterdayAsString5);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dateList = new ArrayList<>();
                    dateList.add(date1);
                    dateList.add(date2);
                    dateList.add(date3);
                    dateList.add(date4);
                    dateList.add(date5);
                    dateList.add(date6);
                    dateList.add(selectedDate);

                    Date cDate = null;
                    Date c = Calendar.getInstance().getTime();
                    Calendar calCurrent = Calendar.getInstance();
                    calCurrent.setTime(c);
                    String strCurrent = dateFormat.format(calCurrent.getTime());
                    try {
                        cDate = dateFormatw.parse(strCurrent);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date eDate = null;
                    for (int i = 0; i < dateList.size(); i++) {
                        if (cDate.equals(dateList.get(i))) {
                            eDate = dateList.get(i);
                            Log.e("", "" + eDate);
                        } else {
                            endDate = yesterdayAsString2;
                        }
                    }
                    if (eDate != null) {
                        endDate = dateFormat.format(eDate);
                    }

                    if (!selectedDate.before(FirstOrderDate)) {
                        ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                        caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);
                    }

                    if (!date1.before(FirstOrderDate)) {
                        if (date1.before(currentDate)) {
                            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue, date1);
                        }
                    }

                    if (!date2.before(FirstOrderDate)) {
                        if (date2.before(currentDate)) {
                            ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue1, date2);
                        }
                    }

                    if (!date3.before(FirstOrderDate)) {
                        if (date3.before(currentDate)) {
                            ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue2, date3);
                        }
                    }

                    if (!date4.before(FirstOrderDate)) {
                        if (date4.before(currentDate)) {
                            ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue3, date4);
                        }
                    }

                    if (!date5.before(FirstOrderDate)) {
                        if (date5.before(currentDate)) {
                            ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue4, date5);
                        }
                    }

                    if (!date6.before(FirstOrderDate)) {
                        if (date6.before(currentDate)) {
                            ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue5, date6);
                        }
                    }

                } else if (selectedDay == Calendar.TUESDAY) {

                    //friday,saturday,sunday,monday,TUESDAY,wednesday,thursday

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    Calendar calendar1 = Calendar.getInstance();
                    Calendar calendar2 = Calendar.getInstance();
                    Calendar calendar3 = Calendar.getInstance();
                    Calendar calendar4 = Calendar.getInstance();
                    Calendar calendar5 = Calendar.getInstance();
                    Calendar calendar6 = Calendar.getInstance();
                    calendar1.setTime(selectedDate);
                    calendar2.setTime(selectedDate);
                    calendar3.setTime(selectedDate);
                    calendar4.setTime(selectedDate);
                    calendar5.setTime(selectedDate);
                    calendar6.setTime(selectedDate);

                    calendar1.add(Calendar.DATE, -1);
                    calendar2.add(Calendar.DATE, +3);
                    calendar3.add(Calendar.DATE, +4);
                    calendar4.add(Calendar.DATE, +5);
                    calendar5.add(Calendar.DATE, +1);
                    calendar6.add(Calendar.DATE, +2);
                    String yesterdayAsString = dateFormat.format(calendar1.getTime());
                    String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
                    String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
                    String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
                    String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
                    String yesterdayAsString5 = dateFormat.format(calendar6.getTime());

                    startDate = yesterdayAsString;
                    //endDate = yesterdayAsString5;

                    SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    try {
                        date1 = dateFormatw.parse(yesterdayAsString);
                        date2 = dateFormatw.parse(yesterdayAsString1);
                        date3 = dateFormatw.parse(yesterdayAsString2);
                        date4 = dateFormatw.parse(yesterdayAsString3);
                        date5 = dateFormatw.parse(yesterdayAsString4);
                        date6 = dateFormatw.parse(yesterdayAsString5);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dateList = new ArrayList<>();
                    dateList.add(date1);
                    dateList.add(date2);
                    dateList.add(date3);
                    dateList.add(date4);
                    dateList.add(date5);
                    dateList.add(date6);
                    dateList.add(selectedDate);

                    Date cDate = null;
                    Date c = Calendar.getInstance().getTime();
                    Calendar calCurrent = Calendar.getInstance();
                    calCurrent.setTime(c);
                    String strCurrent = dateFormat.format(calCurrent.getTime());
                    try {
                        cDate = dateFormatw.parse(strCurrent);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date eDate = null;
                    for (int i = 0; i < dateList.size(); i++) {
                        if (cDate.equals(dateList.get(i))) {
                            eDate = dateList.get(i);
                            Log.e("", "" + eDate);
                        } else {
                            endDate = yesterdayAsString3;
                        }
                    }
                    if (eDate != null) {
                        endDate = dateFormat.format(eDate);
                    }

                    if (!selectedDate.before(FirstOrderDate)) {
                        ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                        caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);
                    }

                    if (!date1.before(FirstOrderDate)) {
                        if (date1.before(currentDate)) {
                            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue, date1);
                        }
                    }

                    if (!date2.before(FirstOrderDate)) {
                        if (date2.before(currentDate)) {
                            ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue1, date2);
                        }
                    }

                    if (!date3.before(FirstOrderDate)) {
                        if (date3.before(currentDate)) {
                            ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue2, date3);
                        }
                    }

                    if (!date4.before(FirstOrderDate)) {
                        if (date4.before(currentDate)) {
                            ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue3, date4);
                        }
                    }

                    if (!date5.before(FirstOrderDate)) {
                        if (date5.before(currentDate)) {
                            ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue4, date5);
                        }
                    }

                    if (!date6.before(FirstOrderDate)) {
                        if (date6.before(currentDate)) {
                            ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue5, date6);
                        }
                    }

                } else if (selectedDay == Calendar.WEDNESDAY) {
                    Log.e("Cal", "" + currentDate);
                    //friday,saturday,sunday,monday,tuesday,WEDNESDAY,thursday

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    Calendar calendar1 = Calendar.getInstance();
                    Calendar calendar2 = Calendar.getInstance();
                    Calendar calendar3 = Calendar.getInstance();
                    Calendar calendar4 = Calendar.getInstance();
                    Calendar calendar5 = Calendar.getInstance();
                    Calendar calendar6 = Calendar.getInstance();
                    calendar1.setTime(selectedDate);
                    calendar2.setTime(selectedDate);
                    calendar3.setTime(selectedDate);
                    calendar4.setTime(selectedDate);
                    calendar5.setTime(selectedDate);
                    calendar6.setTime(selectedDate);

                    calendar1.add(Calendar.DATE, -1);
                    calendar2.add(Calendar.DATE, -2);
                    calendar3.add(Calendar.DATE, +2);
                    calendar4.add(Calendar.DATE, +3);
                    calendar5.add(Calendar.DATE, +1);
                    calendar6.add(Calendar.DATE, +4);
                    String yesterdayAsString = dateFormat.format(calendar1.getTime());
                    String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
                    String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
                    String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
                    String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
                    String yesterdayAsString5 = dateFormat.format(calendar6.getTime());

                    startDate = yesterdayAsString1;
                    //endDate = yesterdayAsString4;

                    SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                    try {
                        date1 = dateFormatw.parse(yesterdayAsString);
                        date2 = dateFormatw.parse(yesterdayAsString1);
                        date3 = dateFormatw.parse(yesterdayAsString2);
                        date4 = dateFormatw.parse(yesterdayAsString3);
                        date5 = dateFormatw.parse(yesterdayAsString4);
                        date6 = dateFormatw.parse(yesterdayAsString5);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dateList = new ArrayList<>();
                    dateList.add(date1);
                    dateList.add(date2);
                    dateList.add(date3);
                    dateList.add(date4);
                    dateList.add(date5);
                    dateList.add(date6);
                    dateList.add(selectedDate);

                    Date cDate = null;
                    Date c = Calendar.getInstance().getTime();
                    Calendar calCurrent = Calendar.getInstance();
                    calCurrent.setTime(c);
                    String strCurrent = dateFormat.format(calCurrent.getTime());
                    try {
                        cDate = dateFormatw.parse(strCurrent);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date eDate = null;
                    for (int i = 0; i < dateList.size(); i++) {
                        if (cDate.equals(dateList.get(i))) {
                            eDate = dateList.get(i);
                            Log.e("", "" + eDate);
                        } else {
                            endDate = yesterdayAsString5;
                        }
                    }
                    if (eDate != null) {
                        endDate = dateFormat.format(eDate);
                    }

                    if (!selectedDate.before(FirstOrderDate)) {
                        ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                        caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);
                    }

                    if (!date1.before(FirstOrderDate)) {
                        if (date1.before(currentDate)) {
                            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue, date1);
                        }
                    }

                    if (!date2.before(FirstOrderDate)) {
                        if (date2.before(currentDate)) {
                            ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue1, date2);
                        }
                    }

                    if (!date3.before(FirstOrderDate)) {
                        if (date3.before(currentDate)) {
                            ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue2, date3);
                        }
                    }

                    if (!date4.before(FirstOrderDate)) {
                        if (date4.before(currentDate)) {
                            ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue3, date4);
                        }
                    }

                    if (!date5.before(FirstOrderDate)) {
                        if (date5.before(currentDate)) {
                            ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue4, date5);
                        }
                    }

                    if (!date6.before(FirstOrderDate)) {
                        if (date6.before(currentDate)) {
                            ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue5, date6);
                        }
                    }
                } else if (selectedDay == Calendar.THURSDAY) {
                    Log.e("Cal", "" + currentDate);
                    //friday,saturday,sunday,monday,tuesday,wednesday,THURSDAY

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    Calendar calendar1 = Calendar.getInstance();
                    Calendar calendar2 = Calendar.getInstance();
                    Calendar calendar3 = Calendar.getInstance();
                    Calendar calendar4 = Calendar.getInstance();
                    Calendar calendar5 = Calendar.getInstance();
                    Calendar calendar6 = Calendar.getInstance();
                    calendar1.setTime(selectedDate);
                    calendar2.setTime(selectedDate);
                    calendar3.setTime(selectedDate);
                    calendar4.setTime(selectedDate);
                    calendar5.setTime(selectedDate);
                    calendar6.setTime(selectedDate);

                    calendar1.add(Calendar.DATE, -1);
                    calendar2.add(Calendar.DATE, -2);
                    calendar3.add(Calendar.DATE, -3);
                    calendar4.add(Calendar.DATE, +1);
                    calendar5.add(Calendar.DATE, +2);
                    calendar6.add(Calendar.DATE, +3);
                    String yesterdayAsString = dateFormat.format(calendar1.getTime());
                    String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
                    String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
                    String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
                    String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
                    String yesterdayAsString5 = dateFormat.format(calendar6.getTime());

                    startDate = yesterdayAsString2;
                    //endDate = yesterdayAsString5;

                    SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    try {
                        date1 = dateFormatw.parse(yesterdayAsString);
                        date2 = dateFormatw.parse(yesterdayAsString1);
                        date3 = dateFormatw.parse(yesterdayAsString2);
                        date4 = dateFormatw.parse(yesterdayAsString3);
                        date5 = dateFormatw.parse(yesterdayAsString4);
                        date6 = dateFormatw.parse(yesterdayAsString5);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dateList = new ArrayList<>();
                    dateList.add(date1);
                    dateList.add(date2);
                    dateList.add(date3);
                    dateList.add(date4);
                    dateList.add(date5);
                    dateList.add(date6);
                    dateList.add(selectedDate);

                    Date cDate = null;
                    Date c = Calendar.getInstance().getTime();
                    Calendar calCurrent = Calendar.getInstance();
                    calCurrent.setTime(c);
                    String strCurrent = dateFormat.format(calCurrent.getTime());
                    try {
                        cDate = dateFormatw.parse(strCurrent);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date eDate = null;
                    for (int i = 0; i < dateList.size(); i++) {
                        if (cDate.equals(dateList.get(i))) {
                            eDate = dateList.get(i);
                            Log.e("", "" + eDate);
                        } else {
                            endDate = yesterdayAsString5;
                        }
                    }
                    if (eDate != null) {
                        endDate = dateFormat.format(eDate);
                    }

                    if (!selectedDate.before(FirstOrderDate)) {
                        ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                        caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);
                    }

                    if (!date1.before(FirstOrderDate)) {
                        if (date1.before(currentDate)) {
                            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue, date1);
                        }
                    }

                    if (!date2.before(FirstOrderDate)) {
                        if (date2.before(currentDate)) {
                            ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue1, date2);
                        }
                    }

                    if (!date3.before(FirstOrderDate)) {
                        if (date3.before(currentDate)) {
                            ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue2, date3);
                        }
                    }

                    if (!date4.before(FirstOrderDate)) {
                        if (date4.before(currentDate)) {
                            ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue3, date4);
                        }
                    }

                    if (!date5.before(FirstOrderDate)) {
                        if (date5.before(currentDate)) {
                            ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue4, date5);
                        }
                    }

                    if (!date6.before(FirstOrderDate)) {
                        if (date6.before(currentDate)) {
                            ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue5, date6);
                        }
                    }
                } else if (selectedDay == Calendar.FRIDAY) {
                    Log.e("Cal", "" + currentDate);
                    //friday,saturday,sunday,monday,tuesday,wednesday,THURSDAY

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    Calendar calendar1 = Calendar.getInstance();
                    Calendar calendar2 = Calendar.getInstance();
                    Calendar calendar3 = Calendar.getInstance();
                    Calendar calendar4 = Calendar.getInstance();
                    Calendar calendar5 = Calendar.getInstance();
                    Calendar calendar6 = Calendar.getInstance();
                    calendar1.setTime(selectedDate);
                    calendar2.setTime(selectedDate);
                    calendar3.setTime(selectedDate);
                    calendar4.setTime(selectedDate);
                    calendar5.setTime(selectedDate);
                    calendar6.setTime(selectedDate);

                    calendar1.add(Calendar.DATE, +1);
                    calendar2.add(Calendar.DATE, +2);
                    calendar3.add(Calendar.DATE, -1);
                    calendar4.add(Calendar.DATE, -2);
                    calendar5.add(Calendar.DATE, -3);
                    calendar6.add(Calendar.DATE, -4);
                    String yesterdayAsString = dateFormat.format(calendar1.getTime());
                    String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
                    String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
                    String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
                    String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
                    String yesterdayAsString5 = dateFormat.format(calendar6.getTime());

                    startDate = yesterdayAsString5;
                    //endDate = yesterdayAsString5;

                    SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    try {
                        date1 = dateFormatw.parse(yesterdayAsString);
                        date2 = dateFormatw.parse(yesterdayAsString1);
                        date3 = dateFormatw.parse(yesterdayAsString2);
                        date4 = dateFormatw.parse(yesterdayAsString3);
                        date5 = dateFormatw.parse(yesterdayAsString4);
                        date6 = dateFormatw.parse(yesterdayAsString5);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dateList = new ArrayList<>();
                    dateList.add(date1);
                    dateList.add(date2);
                    dateList.add(date3);
                    dateList.add(date4);
                    dateList.add(date5);
                    dateList.add(date6);
                    dateList.add(selectedDate);

                    Date cDate = null;
                    Date c = Calendar.getInstance().getTime();
                    Calendar calCurrent = Calendar.getInstance();
                    calCurrent.setTime(c);
                    String strCurrent = dateFormat.format(calCurrent.getTime());
                    try {
                        cDate = dateFormatw.parse(strCurrent);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date eDate = null;
                    for (int i = 0; i < dateList.size(); i++) {
                        if (cDate.equals(dateList.get(i))) {
                            eDate = dateList.get(i);
                            Log.e("", "" + eDate);
                        } else {
                            endDate = yesterdayAsString1;
                        }
                    }
                    if (eDate != null) {
                        endDate = dateFormat.format(eDate);
                    }

                    if (!selectedDate.before(FirstOrderDate)) {
                        ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                        caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);
                    }

                    if (!date1.before(FirstOrderDate)) {
                        if (date1.before(currentDate)) {
                            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue, date1);
                        }
                    }

                    if (!date2.before(FirstOrderDate)) {
                        if (date2.before(currentDate)) {
                            ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue1, date2);
                        }
                    }

                    if (!date3.before(FirstOrderDate)) {
                        if (date3.before(currentDate)) {
                            ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue2, date3);
                        }
                    }

                    if (!date4.before(FirstOrderDate)) {
                        if (date4.before(currentDate)) {
                            ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue3, date4);
                        }
                    }

                    if (!date5.before(FirstOrderDate)) {
                        if (date5.before(currentDate)) {
                            ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue4, date5);
                        }
                    }

                    if (!date6.before(FirstOrderDate)) {
                        if (date6.before(currentDate)) {
                            ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue5, date6);
                        }
                    }
                } else if (selectedDay == Calendar.SATURDAY) {
                    Log.e("Cal", "" + currentDate);
                    //friday,saturday,sunday,monday,tuesday,wednesday,THURSDAY

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    Calendar calendar1 = Calendar.getInstance();
                    Calendar calendar2 = Calendar.getInstance();
                    Calendar calendar3 = Calendar.getInstance();
                    Calendar calendar4 = Calendar.getInstance();
                    Calendar calendar5 = Calendar.getInstance();
                    Calendar calendar6 = Calendar.getInstance();
                    calendar1.setTime(selectedDate);
                    calendar2.setTime(selectedDate);
                    calendar3.setTime(selectedDate);
                    calendar4.setTime(selectedDate);
                    calendar5.setTime(selectedDate);
                    calendar6.setTime(selectedDate);

                    calendar1.add(Calendar.DATE, -1);
                    calendar2.add(Calendar.DATE, +1);
                    calendar3.add(Calendar.DATE, -2);
                    calendar4.add(Calendar.DATE, -3);
                    calendar5.add(Calendar.DATE, -4);
                    calendar6.add(Calendar.DATE, -5);
                    String yesterdayAsString = dateFormat.format(calendar1.getTime());
                    String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
                    String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
                    String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
                    String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
                    String yesterdayAsString5 = dateFormat.format(calendar6.getTime());

                    startDate = yesterdayAsString5;
                    //endDate = yesterdayAsString5;

                    SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    try {
                        date1 = dateFormatw.parse(yesterdayAsString);
                        date2 = dateFormatw.parse(yesterdayAsString1);
                        date3 = dateFormatw.parse(yesterdayAsString2);
                        date4 = dateFormatw.parse(yesterdayAsString3);
                        date5 = dateFormatw.parse(yesterdayAsString4);
                        date6 = dateFormatw.parse(yesterdayAsString5);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dateList = new ArrayList<>();
                    dateList.add(date1);
                    dateList.add(date2);
                    dateList.add(date3);
                    dateList.add(date4);
                    dateList.add(date5);
                    dateList.add(date6);
                    dateList.add(selectedDate);

                    Date cDate = null;
                    Date c = Calendar.getInstance().getTime();
                    Calendar calCurrent = Calendar.getInstance();
                    calCurrent.setTime(c);
                    String strCurrent = dateFormat.format(calCurrent.getTime());
                    try {
                        cDate = dateFormatw.parse(strCurrent);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date eDate = null;
                    for (int i = 0; i < dateList.size(); i++) {
                        if (cDate.equals(dateList.get(i))) {
                            eDate = dateList.get(i);
                            Log.e("", "" + eDate);
                        } else {
                            endDate = yesterdayAsString1;
                        }
                    }
                    if (eDate != null) {
                        endDate = dateFormat.format(eDate);
                    }

                    if (!selectedDate.before(FirstOrderDate)) {
                        ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                        caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);
                    }

                    if (!date1.before(FirstOrderDate)) {
                        if (date1.before(currentDate)) {
                            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue, date1);
                        }
                    }

                    if (!date2.before(FirstOrderDate)) {
                        if (date2.before(currentDate)) {
                            ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue1, date2);
                        }
                    }

                    if (!date3.before(FirstOrderDate)) {
                        if (date3.before(currentDate)) {
                            ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue2, date3);
                        }
                    }

                    if (!date4.before(FirstOrderDate)) {
                        if (date4.before(currentDate)) {
                            ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue3, date4);
                        }
                    }

                    if (!date5.before(FirstOrderDate)) {
                        if (date5.before(currentDate)) {
                            ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue4, date5);
                        }
                    }

                    if (!date6.before(FirstOrderDate)) {
                        if (date6.before(currentDate)) {
                            ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                            caldroidFragment.setBackgroundDrawableForDate(blue5, date6);
                        }
                    }
                }

            } else {
                Log.e("Cal", "" + selectedDate);
                Toast.makeText(this, "No data for future date", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onCurrentDateSelected(Date selectedDate) {
        selectedDatetemp = selectedDate;

        Date currentDate = Calendar.getInstance().getTime();////current date

        Calendar cal = Calendar.getInstance();
        cal.setTime(selectedDate);
        int selectedDay = cal.get(Calendar.DAY_OF_WEEK);

        Log.e("Cal", "" + currentDate);
        if (selectedDay == Calendar.SUNDAY) {
            Log.e("Cal", "" + currentDate);
            //friday,saturday,SUNDAY,monday,tuesday,wednesday,thursday

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            Calendar calendar3 = Calendar.getInstance();
            Calendar calendar4 = Calendar.getInstance();
            Calendar calendar5 = Calendar.getInstance();
            Calendar calendar6 = Calendar.getInstance();
            calendar1.setTime(selectedDate);
            calendar2.setTime(selectedDate);
            calendar3.setTime(selectedDate);
            calendar4.setTime(selectedDate);
            calendar5.setTime(selectedDate);
            calendar6.setTime(selectedDate);
            calendar1.add(Calendar.DATE, -1);
            calendar2.add(Calendar.DATE, -2);
            calendar3.add(Calendar.DATE, -3);
            calendar4.add(Calendar.DATE, -4);
            calendar5.add(Calendar.DATE, -5);
            calendar6.add(Calendar.DATE, -6);

            String yesterdayAsString = dateFormat.format(calendar1.getTime());
            String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
            String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
            String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
            String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
            String yesterdayAsString5 = dateFormat.format(calendar6.getTime());
            endDate = dateFormat.format(selectedDate);
            startDate = yesterdayAsString1;

            SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date date1 = null;
            Date date2 = null;
            Date date3 = null;
            Date date4 = null;
            Date date5 = null;
            Date date6 = null;
            try {
                date1 = dateFormatw.parse(yesterdayAsString);
                date2 = dateFormatw.parse(yesterdayAsString1);
                date3 = dateFormatw.parse(yesterdayAsString2);
                date4 = dateFormatw.parse(yesterdayAsString3);
                date5 = dateFormatw.parse(yesterdayAsString4);
                date6 = dateFormatw.parse(yesterdayAsString5);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (!selectedDate.before(FirstOrderDate)) {
                ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);
            }

            if (!date1.before(FirstOrderDate)) {
                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue, date1);
            }

            if (!date2.before(FirstOrderDate)) {
                ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue1, date2);
            }

            if (!date3.before(FirstOrderDate)) {
                ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue2, date3);
            }

            if (!date4.before(FirstOrderDate)) {
                ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue3, date4);
            }

            if (!date5.before(FirstOrderDate)) {
                ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue4, date5);
            }

            if (!date6.before(FirstOrderDate)) {
                ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue5, date6);
            }

        } else if (selectedDay == Calendar.MONDAY) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            startDate = dateFormat.format(selectedDate);
            endDate = dateFormat.format(selectedDate);

            if (!selectedDate.before(FirstOrderDate)) {
                ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);
            }
        } else if (selectedDay == Calendar.TUESDAY) {

            //friday,saturday,sunday,monday,TUESDAY,wednesday,thursday

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(selectedDate);

            calendar1.add(Calendar.DATE, -1);
            String yesterdayAsString = dateFormat.format(calendar1.getTime());

            startDate = yesterdayAsString;
            endDate = dateFormat.format(selectedDate);

            SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date date1 = null;
            try {
                date1 = dateFormatw.parse(yesterdayAsString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (!selectedDate.before(FirstOrderDate)) {
                ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);
            }

            if (!date1.before(FirstOrderDate)) {
                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue, date1);
            }

        } else if (selectedDay == Calendar.WEDNESDAY) {
            Log.e("Cal", "" + currentDate);
            //friday,saturday,sunday,monday,tuesday,WEDNESDAY,thursday

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();

            calendar1.setTime(selectedDate);
            calendar2.setTime(selectedDate);

            calendar1.add(Calendar.DATE, -1);
            calendar2.add(Calendar.DATE, -2);

            String yesterdayAsString = dateFormat.format(calendar1.getTime());
            String yesterdayAsString1 = dateFormat.format(calendar2.getTime());

            startDate = yesterdayAsString1;
            endDate = dateFormat.format(selectedDate);

            SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date date1 = null;
            Date date2 = null;

            try {
                date1 = dateFormatw.parse(yesterdayAsString);
                date2 = dateFormatw.parse(yesterdayAsString1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (!selectedDate.before(FirstOrderDate)) {
                ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);
            }

            if (!date1.before(FirstOrderDate)) {
                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue, date1);
            }

            if (!date2.before(FirstOrderDate)) {
                ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue1, date2);
            }
        } else if (selectedDay == Calendar.THURSDAY) {
            Log.e("Cal", "" + currentDate);
            //friday,saturday,sunday,monday,tuesday,wednesday,THURSDAY

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            Calendar calendar3 = Calendar.getInstance();

            calendar1.setTime(selectedDate);
            calendar2.setTime(selectedDate);
            calendar3.setTime(selectedDate);

            calendar1.add(Calendar.DATE, -1);
            calendar2.add(Calendar.DATE, -2);
            calendar3.add(Calendar.DATE, -3);

            String yesterdayAsString = dateFormat.format(calendar1.getTime());
            String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
            String yesterdayAsString2 = dateFormat.format(calendar3.getTime());

            startDate = yesterdayAsString2;
            endDate = dateFormat.format(selectedDate);

            SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date date1 = null;
            Date date2 = null;
            Date date3 = null;
            try {
                date1 = dateFormatw.parse(yesterdayAsString);
                date2 = dateFormatw.parse(yesterdayAsString1);
                date3 = dateFormatw.parse(yesterdayAsString2);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (!selectedDate.before(FirstOrderDate)) {
                ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);
            }

            if (!date1.before(FirstOrderDate)) {
                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue, date1);
            }

            if (!date2.before(FirstOrderDate)) {
                ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue1, date2);
            }

            if (!date3.before(FirstOrderDate)) {
                ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue2, date3);
            }
        } else if (selectedDay == Calendar.FRIDAY) {
            /*Log.e("Cal", "" + currentDate);
            //friday,saturday,sunday,monday,tuesday,wednesday,THURSDAY

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            startDate = dateFormat.format(selectedDate);
            endDate = dateFormat.format(selectedDate);

            ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
            caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);*/

            Log.e("Cal", "" + currentDate);
            //friday,saturday,sunday,monday,tuesday,wednesday,THURSDAY

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            Calendar calendar3 = Calendar.getInstance();
            Calendar calendar4 = Calendar.getInstance();

            calendar1.setTime(selectedDate);
            calendar2.setTime(selectedDate);
            calendar3.setTime(selectedDate);
            calendar4.setTime(selectedDate);

            calendar1.add(Calendar.DATE, -1);
            calendar2.add(Calendar.DATE, -2);
            calendar3.add(Calendar.DATE, -3);
            calendar4.add(Calendar.DATE, -4);

            String yesterdayAsString = dateFormat.format(calendar1.getTime());
            String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
            String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
            String yesterdayAsString3 = dateFormat.format(calendar4.getTime());

            startDate = yesterdayAsString3;
            endDate = dateFormat.format(selectedDate);

            SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date date1 = null;
            Date date2 = null;
            Date date3 = null;
            Date date4 = null;
            try {
                date1 = dateFormatw.parse(yesterdayAsString);
                date2 = dateFormatw.parse(yesterdayAsString1);
                date3 = dateFormatw.parse(yesterdayAsString2);
                date4 = dateFormatw.parse(yesterdayAsString3);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (!selectedDate.before(FirstOrderDate)) {
                ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);
            }

            if (!date1.before(FirstOrderDate)) {
                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue, date1);
            }

            if (!date2.before(FirstOrderDate)) {
                ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue1, date2);
            }

            if (!date3.before(FirstOrderDate)) {
                ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue2, date3);
            }

            if (!date4.before(FirstOrderDate)) {
                ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue3, date4);
            }
        } else if (selectedDay == Calendar.SATURDAY) {
            Log.e("Cal", "" + currentDate);
            //friday,saturday,sunday,monday,tuesday,wednesday,THURSDAY

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            Calendar calendar3 = Calendar.getInstance();
            Calendar calendar4 = Calendar.getInstance();
            Calendar calendar5 = Calendar.getInstance();

            calendar1.setTime(selectedDate);
            calendar2.setTime(selectedDate);
            calendar3.setTime(selectedDate);
            calendar4.setTime(selectedDate);
            calendar5.setTime(selectedDate);

            calendar1.add(Calendar.DATE, -1);
            calendar2.add(Calendar.DATE, -2);
            calendar3.add(Calendar.DATE, -3);
            calendar4.add(Calendar.DATE, -4);
            calendar5.add(Calendar.DATE, -5);

            String yesterdayAsString = dateFormat.format(calendar1.getTime());
            String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
            String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
            String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
            String yesterdayAsString4 = dateFormat.format(calendar5.getTime());

            startDate = yesterdayAsString4;
            endDate = dateFormat.format(selectedDate);

            SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date date1 = null;
            Date date2 = null;
            Date date3 = null;
            Date date4 = null;
            Date date5 = null;
            try {
                date1 = dateFormatw.parse(yesterdayAsString);
                date2 = dateFormatw.parse(yesterdayAsString1);
                date3 = dateFormatw.parse(yesterdayAsString2);
                date4 = dateFormatw.parse(yesterdayAsString3);
                date5 = dateFormatw.parse(yesterdayAsString4);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (!selectedDate.before(FirstOrderDate)) {
                ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blues, selectedDate);
            }

            if (!date1.before(FirstOrderDate)) {
                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue, date1);
            }

            if (!date2.before(FirstOrderDate)) {
                ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue1, date2);
            }

            if (!date3.before(FirstOrderDate)) {
                ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue2, date3);
            }

            if (!date4.before(FirstOrderDate)) {
                ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue3, date4);
            }

            if (!date5.before(FirstOrderDate)) {
                ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.setBackgroundDrawableForDate(blue4, date5);
            }

        } else {
            Log.e("Cal", "" + selectedDate);
            Toast.makeText(this, "No data for future date (" + selectedDate + ")", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDateSelectedClear(Date selectedDate) {
        Date currentDate = Calendar.getInstance().getTime();////current date
        if (selectedDate.equals(currentDate)) {
            Log.e("Cal", "" + selectedDate);
            Log.e("Cal", "" + currentDate);
            String sasf = (String) DateFormat.format("EEEE", selectedDate);
            String fgdf = (String) DateFormat.format("EEEE", currentDate);
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(selectedDate);
            int selectedDay = cal.get(Calendar.DAY_OF_WEEK);

            Log.e("Cal", "" + currentDate);
            if (selectedDay == Calendar.SUNDAY) {
                Log.e("Cal", "" + currentDate);
                //friday,saturday,SUNDAY,monday,tuesday,wednesday,thursday

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                Calendar calendar3 = Calendar.getInstance();
                Calendar calendar4 = Calendar.getInstance();
                Calendar calendar5 = Calendar.getInstance();
                Calendar calendar6 = Calendar.getInstance();
                calendar1.setTime(selectedDate);
                calendar2.setTime(selectedDate);
                calendar3.setTime(selectedDate);
                calendar4.setTime(selectedDate);
                calendar5.setTime(selectedDate);
                calendar6.setTime(selectedDate);

                calendar1.add(Calendar.DATE, -1);
                calendar2.add(Calendar.DATE, -2);
                calendar3.add(Calendar.DATE, -6);
                calendar4.add(Calendar.DATE, -5);
                calendar5.add(Calendar.DATE, -3);
                calendar6.add(Calendar.DATE, -4);
                String yesterdayAsString = dateFormat.format(calendar1.getTime());
                String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
                String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
                String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
                String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
                String yesterdayAsString5 = dateFormat.format(calendar6.getTime());

                SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date date1 = null;
                Date date2 = null;
                Date date3 = null;
                Date date4 = null;
                Date date5 = null;
                Date date6 = null;
                try {
                    date1 = dateFormatw.parse(yesterdayAsString);
                    date2 = dateFormatw.parse(yesterdayAsString1);
                    date3 = dateFormatw.parse(yesterdayAsString2);
                    date4 = dateFormatw.parse(yesterdayAsString3);
                    date5 = dateFormatw.parse(yesterdayAsString4);
                    date6 = dateFormatw.parse(yesterdayAsString5);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(selectedDate);


                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date1);

                ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date2);

                ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date3);

                ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date4);

                ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date5);

                ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date6);
            } else if (selectedDay == Calendar.MONDAY) {
                Log.e("Cal", "" + currentDate);
                //friday,saturday,sunday,MONDAY,tuesday,wednesday,thursday

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                Calendar calendar3 = Calendar.getInstance();
                Calendar calendar4 = Calendar.getInstance();
                Calendar calendar5 = Calendar.getInstance();
                Calendar calendar6 = Calendar.getInstance();
                calendar1.setTime(selectedDate);
                calendar2.setTime(selectedDate);
                calendar3.setTime(selectedDate);
                calendar4.setTime(selectedDate);
                calendar5.setTime(selectedDate);
                calendar6.setTime(selectedDate);

                calendar1.add(Calendar.DATE, +4);
                calendar2.add(Calendar.DATE, +5);
                calendar3.add(Calendar.DATE, +6);
                calendar4.add(Calendar.DATE, +3);
                calendar5.add(Calendar.DATE, +1);
                calendar6.add(Calendar.DATE, +2);
                String yesterdayAsString = dateFormat.format(calendar1.getTime());
                String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
                String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
                String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
                String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
                String yesterdayAsString5 = dateFormat.format(calendar6.getTime());

                SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date date1 = null;
                Date date2 = null;
                Date date3 = null;
                Date date4 = null;
                Date date5 = null;
                Date date6 = null;
                try {
                    date1 = dateFormatw.parse(yesterdayAsString);
                    date2 = dateFormatw.parse(yesterdayAsString1);
                    date3 = dateFormatw.parse(yesterdayAsString2);
                    date4 = dateFormatw.parse(yesterdayAsString3);
                    date5 = dateFormatw.parse(yesterdayAsString4);
                    date6 = dateFormatw.parse(yesterdayAsString5);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(selectedDate);


                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date1);

                ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date2);

                ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date3);

                ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date4);

                ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date5);

                ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date6);

            } else if (selectedDay == Calendar.TUESDAY) {

                //friday,saturday,sunday,monday,TUESDAY,wednesday,thursday

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                Calendar calendar3 = Calendar.getInstance();
                Calendar calendar4 = Calendar.getInstance();
                Calendar calendar5 = Calendar.getInstance();
                Calendar calendar6 = Calendar.getInstance();
                calendar1.setTime(selectedDate);
                calendar2.setTime(selectedDate);
                calendar3.setTime(selectedDate);
                calendar4.setTime(selectedDate);
                calendar5.setTime(selectedDate);
                calendar6.setTime(selectedDate);

                calendar1.add(Calendar.DATE, -1);
                calendar2.add(Calendar.DATE, +3);
                calendar3.add(Calendar.DATE, +4);
                calendar4.add(Calendar.DATE, +5);
                calendar5.add(Calendar.DATE, +1);
                calendar6.add(Calendar.DATE, +2);
                String yesterdayAsString = dateFormat.format(calendar1.getTime());
                String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
                String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
                String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
                String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
                String yesterdayAsString5 = dateFormat.format(calendar6.getTime());

                SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date date1 = null;
                Date date2 = null;
                Date date3 = null;
                Date date4 = null;
                Date date5 = null;
                Date date6 = null;
                try {
                    date1 = dateFormatw.parse(yesterdayAsString);
                    date2 = dateFormatw.parse(yesterdayAsString1);
                    date3 = dateFormatw.parse(yesterdayAsString2);
                    date4 = dateFormatw.parse(yesterdayAsString3);
                    date5 = dateFormatw.parse(yesterdayAsString4);
                    date6 = dateFormatw.parse(yesterdayAsString5);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(selectedDate);


                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date1);

                ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date2);

                ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date3);

                ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date4);

                ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date5);

                ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date6);
            } else if (selectedDay == Calendar.WEDNESDAY) {
                Log.e("Cal", "" + currentDate);
                //friday,saturday,sunday,monday,tuesday,WEDNESDAY,thursday

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                Calendar calendar3 = Calendar.getInstance();
                Calendar calendar4 = Calendar.getInstance();
                Calendar calendar5 = Calendar.getInstance();
                Calendar calendar6 = Calendar.getInstance();
                calendar1.setTime(selectedDate);
                calendar2.setTime(selectedDate);
                calendar3.setTime(selectedDate);
                calendar4.setTime(selectedDate);
                calendar5.setTime(selectedDate);
                calendar6.setTime(selectedDate);

                calendar1.add(Calendar.DATE, -1);
                calendar2.add(Calendar.DATE, -2);
                calendar3.add(Calendar.DATE, +2);
                calendar4.add(Calendar.DATE, +3);
                calendar5.add(Calendar.DATE, +1);
                calendar6.add(Calendar.DATE, +4);
                String yesterdayAsString = dateFormat.format(calendar1.getTime());
                String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
                String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
                String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
                String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
                String yesterdayAsString5 = dateFormat.format(calendar6.getTime());

                SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date date1 = null;
                Date date2 = null;
                Date date3 = null;
                Date date4 = null;
                Date date5 = null;
                Date date6 = null;
                try {
                    date1 = dateFormatw.parse(yesterdayAsString);
                    date2 = dateFormatw.parse(yesterdayAsString1);
                    date3 = dateFormatw.parse(yesterdayAsString2);
                    date4 = dateFormatw.parse(yesterdayAsString3);
                    date5 = dateFormatw.parse(yesterdayAsString4);
                    date6 = dateFormatw.parse(yesterdayAsString5);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(selectedDate);


                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date1);

                ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date2);

                ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date3);

                ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date4);

                ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date5);

                ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date6);
            } else if (selectedDay == Calendar.THURSDAY) {
                Log.e("Cal", "" + currentDate);
                //friday,saturday,sunday,monday,tuesday,wednesday,THURSDAY

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                Calendar calendar3 = Calendar.getInstance();
                Calendar calendar4 = Calendar.getInstance();
                Calendar calendar5 = Calendar.getInstance();
                Calendar calendar6 = Calendar.getInstance();
                calendar1.setTime(selectedDate);
                calendar2.setTime(selectedDate);
                calendar3.setTime(selectedDate);
                calendar4.setTime(selectedDate);
                calendar5.setTime(selectedDate);
                calendar6.setTime(selectedDate);

                calendar1.add(Calendar.DATE, -1);
                calendar2.add(Calendar.DATE, -2);
                calendar3.add(Calendar.DATE, -3);
                calendar4.add(Calendar.DATE, +1);
                calendar5.add(Calendar.DATE, +2);
                calendar6.add(Calendar.DATE, +3);
                String yesterdayAsString = dateFormat.format(calendar1.getTime());
                String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
                String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
                String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
                String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
                String yesterdayAsString5 = dateFormat.format(calendar6.getTime());

                SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date date1 = null;
                Date date2 = null;
                Date date3 = null;
                Date date4 = null;
                Date date5 = null;
                Date date6 = null;
                try {
                    date1 = dateFormatw.parse(yesterdayAsString);
                    date2 = dateFormatw.parse(yesterdayAsString1);
                    date3 = dateFormatw.parse(yesterdayAsString2);
                    date4 = dateFormatw.parse(yesterdayAsString3);
                    date5 = dateFormatw.parse(yesterdayAsString4);
                    date6 = dateFormatw.parse(yesterdayAsString5);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(selectedDate);


                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date1);

                ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date2);

                ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date3);

                ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date4);

                ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date5);

                ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date6);
            } else if (selectedDay == Calendar.FRIDAY) {
                Log.e("Cal", "" + currentDate);
                //friday,saturday,sunday,monday,tuesday,wednesday,THURSDAY

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                Calendar calendar3 = Calendar.getInstance();
                Calendar calendar4 = Calendar.getInstance();
                Calendar calendar5 = Calendar.getInstance();
                Calendar calendar6 = Calendar.getInstance();
                calendar1.setTime(selectedDate);
                calendar2.setTime(selectedDate);
                calendar3.setTime(selectedDate);
                calendar4.setTime(selectedDate);
                calendar5.setTime(selectedDate);
                calendar6.setTime(selectedDate);

                calendar1.add(Calendar.DATE, +1);
                calendar2.add(Calendar.DATE, +2);
                calendar3.add(Calendar.DATE, -1);
                calendar4.add(Calendar.DATE, -2);
                calendar5.add(Calendar.DATE, -3);
                calendar6.add(Calendar.DATE, -4);
                String yesterdayAsString = dateFormat.format(calendar1.getTime());
                String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
                String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
                String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
                String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
                String yesterdayAsString5 = dateFormat.format(calendar6.getTime());

                SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date date1 = null;
                Date date2 = null;
                Date date3 = null;
                Date date4 = null;
                Date date5 = null;
                Date date6 = null;
                try {
                    date1 = dateFormatw.parse(yesterdayAsString);
                    date2 = dateFormatw.parse(yesterdayAsString1);
                    date3 = dateFormatw.parse(yesterdayAsString2);
                    date4 = dateFormatw.parse(yesterdayAsString3);
                    date5 = dateFormatw.parse(yesterdayAsString4);
                    date6 = dateFormatw.parse(yesterdayAsString5);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(selectedDate);


                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date1);

                ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date2);

                ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date3);

                ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date4);

                ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date5);

                ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date6);
            } else if (selectedDay == Calendar.SATURDAY) {
                Log.e("Cal", "" + currentDate);
                //friday,saturday,sunday,monday,tuesday,wednesday,THURSDAY

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                Calendar calendar3 = Calendar.getInstance();
                Calendar calendar4 = Calendar.getInstance();
                Calendar calendar5 = Calendar.getInstance();
                Calendar calendar6 = Calendar.getInstance();
                calendar1.setTime(selectedDate);
                calendar2.setTime(selectedDate);
                calendar3.setTime(selectedDate);
                calendar4.setTime(selectedDate);
                calendar5.setTime(selectedDate);
                calendar6.setTime(selectedDate);

                calendar1.add(Calendar.DATE, -1);
                calendar2.add(Calendar.DATE, +1);
                calendar3.add(Calendar.DATE, -2);
                calendar4.add(Calendar.DATE, -3);
                calendar5.add(Calendar.DATE, -4);
                calendar6.add(Calendar.DATE, -5);
                String yesterdayAsString = dateFormat.format(calendar1.getTime());
                String yesterdayAsString1 = dateFormat.format(calendar2.getTime());
                String yesterdayAsString2 = dateFormat.format(calendar3.getTime());
                String yesterdayAsString3 = dateFormat.format(calendar4.getTime());
                String yesterdayAsString4 = dateFormat.format(calendar5.getTime());
                String yesterdayAsString5 = dateFormat.format(calendar6.getTime());

                SimpleDateFormat dateFormatw = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date date1 = null;
                Date date2 = null;
                Date date3 = null;
                Date date4 = null;
                Date date5 = null;
                Date date6 = null;
                try {
                    date1 = dateFormatw.parse(yesterdayAsString);
                    date2 = dateFormatw.parse(yesterdayAsString1);
                    date3 = dateFormatw.parse(yesterdayAsString2);
                    date4 = dateFormatw.parse(yesterdayAsString3);
                    date5 = dateFormatw.parse(yesterdayAsString4);
                    date6 = dateFormatw.parse(yesterdayAsString5);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ColorDrawable blues = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(selectedDate);


                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date1);

                ColorDrawable blue1 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date2);

                ColorDrawable blue2 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date3);

                ColorDrawable blue3 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date4);

                ColorDrawable blue4 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date5);

                ColorDrawable blue5 = new ColorDrawable(getResources().getColor(R.color.light_blue));
                caldroidFragment.clearBackgroundDrawableForDate(date6);
            }
            /*} else {
                Log.e("Cal", "" + selectedDate);
                Toast.makeText(getActivity(), "No data for future date (" + selectedDate + ")", Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    @Override
    public void onItemClick(CalendarDayWiseResponse.Result.Item result) {
        Intent intent = ProductCancelActivity.newIntent(CalendarActivity.this, AppConstants.SCREEN_NAME_CALENDAR, AppConstants.SCREEN_NAME_PRODUCT_CANCEL);
        intent.putExtra("doid", result.getDoid());
        intent.putExtra("dayorderpid", result.getDayorderpid());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void canceled() {

    }

    @Override
    public void onBackPressed() {
        t.remove(caldroidFragment);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
        } catch (Exception dd) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.RATING_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Bundle bundle = data.getExtras();
                String datee = bundle.getString("date");
                selectedDateAPICall(datee);
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}
