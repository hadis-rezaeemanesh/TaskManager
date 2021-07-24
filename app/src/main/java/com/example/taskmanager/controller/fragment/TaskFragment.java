package com.example.taskmanager.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.adapter.TaskStateAdapter;
import com.example.taskmanager.controller.model.Task;
import com.example.taskmanager.controller.repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment {

    public static final String ARGS_TASK_STATE = "taskState";
    public static final String TAG_ADDING_TASK = "addingTask";

    private FloatingActionButton mButtonAdd;
    private RecyclerView mRecyclerView;
    private LinearLayout mLayoutEmptyTask;

    private TaskStateAdapter mTaskStateAdapter;
    private String mTaskState;
    private  List<Task> mTasks = new ArrayList<>();
    private TaskRepository mTaskRepository;
    private int mCurrentPosition;
    private Task mTask = new Task();

    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(List<Task> tasks, int taskState) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_TASK_STATE, taskState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentPosition = getArguments().getInt(ARGS_TASK_STATE);
        mTaskRepository = TaskRepository.getInstance(mCurrentPosition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        findViews(view);
        setListeners();
        initViews();
        return view;
    }

    private void findViews(View view) {
        mButtonAdd = view.findViewById(R.id.floating_action_button);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutEmptyTask = view.findViewById(R.id.linear_layout_empty_task);
    }

    private void setListeners() {
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAddDialogFragment taskAddDialogFragment =
                        TaskAddDialogFragment.newInstance(mCurrentPosition);
                taskAddDialogFragment.show(
                        getActivity().getSupportFragmentManager(),
                        TAG_ADDING_TASK);
            }
        });
    }

    private void initViews(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (mTasks.size() != 0){
            mLayoutEmptyTask.setVisibility(View.GONE);

            mTaskStateAdapter = new TaskStateAdapter(mTasks, getContext());
            mRecyclerView.setAdapter(mTaskStateAdapter);
            mTaskStateAdapter.notifyItemInserted(mTasks.size());
        }

    }
}