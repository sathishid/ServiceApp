package com.ara.serviceapp.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ara.serviceapp.DatePickerActivity;
import com.ara.serviceapp.R;
import com.ara.serviceapp.models.Attendance;
import com.ara.serviceapp.models.Customer;
import com.ara.serviceapp.utils.AppLogic;
import com.ara.serviceapp.utils.ListActivity;

import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.ara.serviceapp.utils.Helper.CUSTOMER_EXTRA;
import static com.ara.serviceapp.utils.Helper.CUSTOMER_SELECT;
import static com.ara.serviceapp.utils.Helper.DATE_EXTRA;
import static com.ara.serviceapp.utils.Helper.DATE_REQUEST_CODE;
import static com.ara.serviceapp.utils.Helper.REQUEST_CODE;
import static com.ara.serviceapp.utils.Helper.dateToString;
import static com.ara.serviceapp.utils.Helper.showSnackBar;
import static com.ara.serviceapp.utils.Helper.timeToString;

public class AttendanceFragment extends Fragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    Attendance attendance;
    ProgressBar progressBar;
    RelativeLayout layoutView;

    LinearLayout mDateLayoutView;
    LinearLayout mTimeLayoutView;
    LinearLayout mCustomeLayoutrView;

    TextView mDateView;
    TextView mTimeView;
    TextView mCustomerView;
    Button saveButton;
    TimePickerDialog timePickerDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_attendance, container, false);
        attendance = new Attendance();

        progressBar = (ProgressBar) rootView.findViewById(R.id.att_progress);
        layoutView = (RelativeLayout) rootView.findViewById(R.id.att_root_view);

        mDateLayoutView = (LinearLayout) rootView.findViewById(R.id.att_date_layout);
        mTimeLayoutView = (LinearLayout) rootView.findViewById(R.id.att_time_layout);
        mCustomeLayoutrView = (LinearLayout) rootView.findViewById(R.id.att_customer_layout);

        mDateView = (TextView) rootView.findViewById(R.id.att_date);
        mTimeView = (TextView) rootView.findViewById(R.id.att_time);
        mCustomerView = (TextView) rootView.findViewById(R.id.att_customer);


        attendance.setDate(dateToString(Calendar.getInstance()));
        attendance.setTime(timeToString(Calendar.getInstance()));


        saveButton = (Button) rootView.findViewById(R.id.att_save);

        mDateLayoutView.setOnClickListener(this);
        mTimeLayoutView.setOnClickListener(this);
        mCustomeLayoutrView.setOnClickListener(this);
        saveButton.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;

        switch (id) {
            case R.id.att_date_layout:
                intent = new Intent(getContext(), DatePickerActivity.class);
                intent.putExtra(DATE_EXTRA, attendance.getDate());
                startActivityForResult(intent, DATE_REQUEST_CODE);
                break;
            case R.id.att_time_layout:
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int minutes = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(getContext(), this,
                        hour, minutes, false);
                timePickerDialog.show();
                break;
            case R.id.att_customer_layout:
                intent = new Intent(getContext(), ListActivity.class);
                intent.putExtra(REQUEST_CODE, CUSTOMER_SELECT);
                startActivityForResult(intent, CUSTOMER_SELECT);
                break;
            case R.id.att_save:
                save();
                break;
        }
    }

    private void save() {
        if (hasError()) {
            return;
        }
        showProgress(true);
        Call<ResponseBody> call = AppLogic.getAppLogic().getAppService().addAttendanceDetail("attendanceAdd", attendance.toHashMap());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    showProgress(false);
                    showSnackBar(layoutView, "Attendance Marked.");
                    clearAll();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showSnackBar(layoutView, t.getMessage());
            }
        });

    }

    private void clearAll() {
        attendance.setCustomer(null);
        mCustomerView.setText("");
    }

    private boolean hasError() {
        if (attendance.getCustomer() == null) {
            showSnackBar(layoutView, R.string.choose_customer);
            return false;
        }
        return true;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String AM_PM = "AM";
        if (hourOfDay < 12) {

            AM_PM = "AM";
        } else if (hourOfDay == 0) {
            hourOfDay = 12;
        } else {
            hourOfDay -= 12;
            AM_PM = "PM";
        }

        String time = hourOfDay + " : " + minute + " " + AM_PM;
        attendance.setTime(time);
        mTimeView.setText(time);
        timePickerDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        String json;
        switch (requestCode) {
            case DATE_REQUEST_CODE:
                String date = data.getStringExtra(DATE_EXTRA);
                attendance.setDate(date);
                mDateView.setText(date);

                break;
            case CUSTOMER_SELECT:
                json = data.getStringExtra(CUSTOMER_EXTRA);
                Customer customer = Customer.fromGson(json);
                attendance.setCustomer(customer);
                mCustomerView.setText(customer.getCustomerName());
                break;
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            layoutView.setVisibility(show ? View.GONE : View.VISIBLE);
            layoutView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    layoutView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            layoutView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
