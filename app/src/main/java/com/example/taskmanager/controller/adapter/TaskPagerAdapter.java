package com.example.taskmanager.controller.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.taskmanager.controller.fragment.TaskFragment;
import com.example.taskmanager.controller.model.State;
import com.example.taskmanager.controller.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

public class TaskPagerAdapter extends FragmentStateAdapter implements TaskFragment.Callbacks {

    private TaskFragment mTaskFragment;
    private long mUserId;
    private Context mContext;
    private List<TaskFragment> mFragments = new ArrayList<TaskFragment>(){{
        add(TaskFragment.newInstance(0, mUserId));
        add(TaskFragment.newInstance(1, mUserId));
        add(TaskFragment.newInstance(2, mUserId));
    }};
    private TaskRepository mTaskRepository;

    public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity, Context context, long userId) {
        super(fragmentActivity);
        mContext = context;
        mUserId = userId;

    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }

    public TaskFragment getFragment(int position){
        return mFragments.get(position);
    }

    @Override
    public void onTaskListUpdated(int position) {
        mFragments.get(position).updateUI(position);
    }
}
