package com.example.taskmanager.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskFragment extends Fragment {

    public static final String ARGS_TASK_STATE = "taskState";

    private FloatingActionButton mButtonAdd;

    private String mTaskState;

    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(String taskState) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_TASK_STATE, taskState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskState = (String) getArguments().get(ARGS_TASK_STATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        mButtonAdd = view.findViewById(R.id.floating_action_button);
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAddDialogFragment taskAddDialogFragment = TaskAddDialogFragment.newInstance();
                taskAddDialogFragment.show(getActivity().getSupportFragmentManager(), "addingTask");
            }
        });
        return view;
    }
}