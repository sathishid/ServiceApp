package com.ara.serviceapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.DatePicker;

import java.util.Calendar;

import static com.ara.serviceapp.utils.Helper.DATE_EXTRA;

public class DatePickerActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener,
        DatePickerDialog.OnDateSetListener {
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
            calendarView.setDate(System.currentTimeMillis(),true,true);
            calendarView.setOnDateChangeListener(this);
        } else {

            datePickerDialog = new DatePickerDialog(this, this, mYear, mMonth, mDay);
            datePickerDialog.show();

        }

    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        Intent intent = new Intent();
        String date = String.format("%d-%d-%d", dayOfMonth, month + 1, year);
        intent.putExtra(DATE_EXTRA, date);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        datePickerDialog.dismiss();
        Intent intent = new Intent();
        String date = String.format("%d-%d-%d", dayOfMonth, month + 1, year);
        intent.putExtra(DATE_EXTRA, date);
        setResult(RESULT_OK, intent);
        finish();
    }
}
