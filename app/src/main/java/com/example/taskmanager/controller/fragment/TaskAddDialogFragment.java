package com.example.taskmanager.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.model.Task;

public class TaskAddDialogFragment extends DialogFragment {

    public static final String TAG_Date_PICKER = "datePicker";
    public static final String TAG_TIME_PICKER = "time_picker";
    public static final int REQUEST_CODE_TIME_PICKER = 1;
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private Button mBtnDate;
    private Button mBtnTime;
    private CheckBox mCheckBoxDone;
    private Button mBtnSave;
    private Button mBtnCancel;
    private Task mTask;

    public TaskAddDialogFragment() {
        // Required empty public constructor
    }

    public static TaskAddDialogFragment newInstance() {
        TaskAddDialogFragment fragment = new TaskAddDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mBtnDate.setText(mTask.getDate().toString());
    }

    private void setListeners() {
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mBtnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance();
                timePickerFragment.setTargetFragment(
                        TaskAddDialogFragment.this,
                        REQUEST_CODE_TIME_PICKER);
                timePickerFragment.show(getActivity().getSupportFragmentManager(), TAG_TIME_PICKER);

            }
        });

        mBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance();
                datePickerFragment.setTargetFragment(
                        TaskAddDialogFragment.this,
                        REQUEST_CODE_DATE_PICKER);
                datePickerFragment.show(getActivity().getSupportFragmentManager(), TAG_Date_PICKER);
            }
        });
    }

}