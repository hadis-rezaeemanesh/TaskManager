package com.example.taskmanager.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.activity.TaskPagerActivity;
import com.example.taskmanager.controller.model.Task;
import com.example.taskmanager.controller.repository.TaskDBRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ShowDetailFragment extends DialogFragment {

    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;
    public static final String EXTRA_NEW_TASK = "com.example.taskmanager.extra.new.task";
    public static final String EXTRA_EDIT_TEXT = "com.example.taskmanager.editText";
    public static final String EXTRA_TASK_EDITED_CURRENT_POSITION =
            "com.example.taskmanager.taskEditedCurrentPosition";
    public static final String EXTRA_TASK_DELETED_CURRENT_POSITION =
            "com.example.taskmanager.taskDeletedCurrentPosition";



    public static final String FRAGMENT_TAG_DATE_PICKER = "datePicker";
    public static final String FRAGMENT_TAG_TIME_PICKER = "timePicker";

    public static final String ARGS_TASK_ID = "taskId";
    public static final String ARGS_TASK_POSITION = "taskPosition";
    public static final String EXTRA_DELETED_CURRENT_POSITION = "deletedCurrentPosition";

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private Button mBtnDate;
    private Button mBtnTime;
    private CheckBox mCheckBox;
    private Button mBtnSave;
    private Button mBtnDelete;
    private RadioGroup mRadioGroupStates;
    private RadioButton mButtonTodo, mButtonDoing, mButtonDone;
    private ImageButton mBtnCancel;

    private Task mTask;
    private List<Task> mTasks;
    private UUID mTaskId;
    private int mPosition;
    private TaskDBRepository mRepository;

    private Date userSelectedDate;
    private Long userSelectedTime;

    public ShowDetailFragment() {
        // Required empty public constructor
    }

    public static ShowDetailFragment newInstance(UUID id, int position) {
        ShowDetailFragment fragment = new ShowDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_ID, id);
        args.putInt(ARGS_TASK_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskId = (UUID) getArguments().getSerializable(ARGS_TASK_ID);
        mPosition = getArguments().getInt(ARGS_TASK_POSITION);
        mRepository = TaskDBRepository.getInstance(getActivity(), mPosition);
        mTask = mRepository.getTask(mTaskId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_show_detail, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.edit_txt_title);
        mEditTextDescription = view.findViewById(R.id.edit_txt_description);
        mBtnDate = view.findViewById(R.id.btn_date);
        mBtnTime = view.findViewById(R.id.btn_time);
        mCheckBox = view.findViewById(R.id.checkBox_done);
        mBtnSave = view.findViewById(R.id.btn_save);
        mBtnDelete = view.findViewById(R.id.btn_delete);
        mRadioGroupStates = view.findViewById(R.id.radiogp_states);
        mButtonTodo = view.findViewById(R.id.btn_todo);
        mButtonDoing = view.findViewById(R.id.btn_doing);
        mButtonDone = view.findViewById(R.id.btn_done);
        mBtnCancel = view.findViewById(R.id.btn_cancel);
    }

    private void initViews() {
        mEditTextTitle.setText(mTask.getTitle());
        mEditTextDescription.setText(mTask.getDes());
        mCheckBox.setChecked(mTask.isDone());

        mBtnDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(mTask.getDate()));
        mBtnTime.setText(new SimpleDateFormat("HH:mm:ss").format(mTask.getDate()));

        switch (mPosition) {
            case 0:
                mButtonTodo.setChecked(true);
                break;
            case 1:
                mButtonDoing.setChecked(true);
                break;
            case 2:
                mButtonDone.setChecked(true);
                break;
        }
    }

    private void setListeners() {

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setTitle(mEditTextTitle.getText().toString());
                mTask.setDes(mEditTextDescription.getText().toString());
                mTask.setDone(mCheckBox.isChecked());
                Intent intent = new Intent();
                intent.putExtra(EXTRA_EDIT_TEXT, true);
                intent.putExtra("EXTRA_NEW_TASK", (Parcelable) mTask);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();

                }
        });
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRepository.deleteTask(mTask);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_DELETED_CURRENT_POSITION, mTask.getPosition());
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment =
                        DatePickerFragment.newInstance(mTask.getDate());

                datePickerFragment.setTargetFragment(
                        ShowDetailFragment.this, REQUEST_CODE_DATE_PICKER);

                datePickerFragment.show(getActivity().getSupportFragmentManager(),
                        FRAGMENT_TAG_DATE_PICKER);
            }
        });

        mBtnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment =
                        TimePickerFragment.newInstance(mTask.getDate());

                timePickerFragment.setTargetFragment(
                        ShowDetailFragment.this, REQUEST_CODE_TIME_PICKER);

                timePickerFragment.show(getActivity().getSupportFragmentManager(),
                        FRAGMENT_TAG_TIME_PICKER);
            }
        });

        mRadioGroupStates.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_todo:
                        mTask.setPosition(0);
                        break;
                    case R.id.btn_doing:
                        mTask.setPosition(1);
                        break;
                    case R.id.btn_done:
                        mTask.setPosition(2);
                        break;
                }
            }
        });
    }
}