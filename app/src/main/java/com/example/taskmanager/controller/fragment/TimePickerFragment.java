package com.example.taskmanager.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.taskmanager.R;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {

    public static final String ARGS_TASK_TIME = "taskTime";
    public static final String EXTRA_USER_SELECTED_TIME = "com.example.taskmanager.userSelectedTime";
    private Date mTaskTime;
    private TimePicker mTimePicker;
    private Calendar mCalendar;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    public static TimePickerFragment newInstance(Date taskDate) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_TIME, taskDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskTime = (Date) getArguments().getSerializable(ARGS_TASK_TIME);
        mCalendar = Calendar.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_picker, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(
            @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_time_picker, null);

        findViews(view);

        initViews();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = mTimePicker.getCurrentHour();
                        int minute = mTimePicker.getCurrentMinute();
                        mTaskTime.setHours(hour);
                        mTaskTime.setMinutes(minute);

                        Fragment fragment = getTargetFragment();
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_USER_SELECTED_TIME, mTaskTime.getTime());
                        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                       /* Date userSelectedTime = extractTimeFromTimePicker();
                        userSelectedTime.setTime(mTimePicker.getHour());
                        mCalendar.setTimeInMillis(mTaskTime.getTime());
                        sendResult(userSelectedTime);*/
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setView(view);

        return builder.create();
    }

    private void findViews(View view) {
        mTimePicker = view.findViewById(R.id.time_picker_task);

    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initViews(){
        mCalendar.setTime(mTaskTime);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        mTimePicker.setHour(hour);
        mTimePicker.setMinute(minute);

    }

    private void sendResult(Date userSelectTime){
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER_SELECTED_TIME, userSelectTime);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }

    private Date extractTimeFromTimePicker(){
        mCalendar.setTimeInMillis(mTaskTime.getTime());
        return mCalendar.getTime();
    }
}