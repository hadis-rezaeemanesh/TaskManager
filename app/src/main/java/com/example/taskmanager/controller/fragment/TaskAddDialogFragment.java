package com.example.taskmanager.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.model.Task;
import com.example.taskmanager.controller.repository.TaskRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class TaskAddDialogFragment extends DialogFragment {

    public static final String TAG_Date_PICKER = "datePicker";
    public static final String TAG_TIME_PICKER = "time_picker";
    public static final int REQUEST_CODE_TIME_PICKER = 1;
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final String ARGS_CURRENT_POSITION = "currentPosition";

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private Button mBtnDate;
    private Button mBtnTime;
    private CheckBox mCheckBoxDone;
    private Button mBtnSave;
    private Button mBtnCancel;

    private Task mTask;
    private TaskRepository mRepository;
    private int mCurrentPosition;

    public TaskAddDialogFragment() {
        // Required empty public constructor
    }

    public static TaskAddDialogFragment newInstance(int currentPosition) {
        TaskAddDialogFragment fragment = new TaskAddDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_CURRENT_POSITION, currentPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentPosition = getArguments().getInt(ARGS_CURRENT_POSITION);
        mRepository = TaskRepository.getInstance(mCurrentPosition);
        mTask = new Task();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_task_add_dialog, container, false);
        findViews(view);
        initViews();
        setListeners();
        return view;
    }

    @Override
    public void onActivityResult(
            int requestCode,
            int resultCode,
            @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PICKER){
            Date dateUserSelected =
                    (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_USER_SELECTED_DATE);

            updateTaskDate(dateUserSelected);
        }

        if (requestCode == REQUEST_CODE_TIME_PICKER){
            Date timeUserSelected =
                    (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_USER_SELECTED_TIME);
            updateTaskTime(timeUserSelected);
        }
    }

    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.edit_txt_title);
        mEditTextDescription = view.findViewById(R.id.edit_txt_description);
        mBtnDate = view.findViewById(R.id.btn_date);
        mBtnTime = view.findViewById(R.id.btn_time);
        mCheckBoxDone = view.findViewById(R.id.checkBox_done);
        mBtnSave = view.findViewById(R.id.btn_save);
        mBtnCancel = view.findViewById(R.id.btn_cancel);
    }

    private void initViews(){
        mTask.setTitle(mEditTextTitle.getText().toString());
        mTask.setDes(mEditTextDescription.getText().toString());

        mBtnDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(mTask.getDate()));
        mBtnTime.setText(new  SimpleDateFormat("HH:mm:ss").format(mTask.getTime()));
    }

    private void setListeners() {
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setTitle(mEditTextTitle.getText().toString());
                mTask.setDes(mEditTextDescription.getText().toString());


            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBtnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment =
                        TimePickerFragment.newInstance(mTask.getTime());
                timePickerFragment.setTargetFragment(
                        TaskAddDialogFragment.this,
                        REQUEST_CODE_TIME_PICKER);
                timePickerFragment.show(getActivity().getSupportFragmentManager(), TAG_TIME_PICKER);

            }
        });

        mBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment =
                        DatePickerFragment.newInstance(mTask.getDate());
                datePickerFragment.setTargetFragment(
                        TaskAddDialogFragment.this,
                        REQUEST_CODE_DATE_PICKER);
                datePickerFragment.show(getActivity().getSupportFragmentManager(), TAG_Date_PICKER);
            }
        });
    }

    private void updateTaskDate(Date userSelectedDate){
        mTask.setDate(userSelectedDate);

        mBtnDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(mTask.getDate()));
    }

    private void updateTaskTime(Date userSelectedTime){
        mTask.setTime(userSelectedTime);

        mBtnTime.setText(new SimpleDateFormat("HH:mm:ss").format(mTask.getTime()));
    }

}