package com.dailylocally.utilities.datepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;

import com.dailylocally.BR;
import com.dailylocally.R;
import com.dailylocally.databinding.ActivityDatePickerBinding;
import com.dailylocally.ui.base.BaseActivity;
import com.dailylocally.ui.calendarView.CalendarMonthResponse;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class DatePickerActivity extends BaseActivity<ActivityDatePickerBinding, DatePickerViewModel> implements DatePickerNavigator {
    @Inject
    DatePickerViewModel mDatePickerViewModel;

    ActivityDatePickerBinding mFragmentHomeBinding;
    FragmentTransaction t;

    Date FirstOrderDate = null;
    List<CalendarMonthResponse.Result> results = new ArrayList<>();
    CaldroidListener listener = null;
    Date dateRating = null;
    String date = "";
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;

    public static Intent newIntent(Context context) {
        return new Intent(context, DatePickerActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.calendarViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_date_picker;
    }

    @Override
    public DatePickerViewModel getViewModel() {
        return mDatePickerViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
    }


    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatePickerViewModel.setNavigator(this);
        mFragmentHomeBinding = getViewDataBinding();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            date = bundle.getString("date");
        }

        caldroidFragment = new CaldroidFragment();

        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        } else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            //args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putString(CaldroidFragment.MIN_DATE, date);
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, false);
            args.putBoolean(CaldroidFragment.SELECTED_DATES, false);
            caldroidFragment.setArguments(args);
        }
        t = this.getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();


        caldroidFragment.setMinDateFromString(date, "yyyy-MM-dd");

        // Setup listener
        listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                dateRating = date;
                caldroidFragment.clearSelectedDates();
                caldroidFragment.setSelectedDate(date);


                String selectedDate = DateFormat.format("dd-MM-yyyy", date).toString();
                Intent intent = new Intent();
                intent.putExtra("date", selectedDate);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }

            @Override
            public void onChangeMonth(int month, int year) {

            }

            @Override
            public void onLongClickDate(Date date, View view) {

            }

            @Override
            public void onCaldroidViewCreated() {

            }
        };

        caldroidFragment.setCaldroidListener(listener);

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void canceled() {

    }

    @Override
    public void onBackPressed() {
        t.remove(caldroidFragment);
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
        } catch (Exception dd) {
        }
    }
}
