package com.example.assignment12.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import com.example.assignment12.R;
import com.example.assignment12.models.DateTime;

public class TimePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private DateTimeListener mListener;
    private int year, month, day, hour, minute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);

        DatePicker datePicker = view.findViewById(R.id.date_picker);
        TimePicker timePicker = view.findViewById(R.id.time_picker);
        Button confirmButton = view.findViewById(R.id.button_confirm);

        confirmButton.setOnClickListener(v -> {
            // Extract the date and time from the DatePicker and TimePicker
            int selectedDay = datePicker.getDayOfMonth();
            int selectedMonth = datePicker.getMonth();
            int selectedYear = datePicker.getYear();
            int selectedHour = timePicker.getCurrentHour();
            int selectedMinute = timePicker.getCurrentMinute();

            // Create a new DateTime object
            DateTime selectedDateTime = new DateTime(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute);

            // Pass the DateTime object back to the activity via the implemented interface method
            if (mListener != null) {
                mListener.onDateTimeSelected(selectedDateTime);
            } else {
                throw new IllegalStateException("DateTimeListener not implemented in hosting activity");
            }
        });


        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Save date and proceed to pick time
        this.year = year;
        this.month = month;
        this.day = day;

        // Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        new TimePickerDialog(getActivity(), this, hour, minute, true).show();
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Create a DateTime object to encapsulate all values
        DateTime dateTime = new DateTime(year, month, day, hourOfDay, minute);

        // Pass back to the activity through the implemented listener
        if (mListener != null) {
            mListener.onDateTimeSelected(dateTime);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (DateTimeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DateTimeListener");
        }
    }

    public interface DateTimeListener {
        void onDateTimeSelected(DateTime dateTime);
    }
}
