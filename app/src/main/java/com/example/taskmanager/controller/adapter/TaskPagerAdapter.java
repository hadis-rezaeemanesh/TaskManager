package com.example.taskmanager.controller.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.taskmanager.controller.fragment.TaskFragment;
import com.example.taskmanager.controller.model.State;
import com.example.taskmanager.controller.repository.TaskRepository;

public class TaskPagerAdapter extends FragmentStateAdapter {

    private TaskFragment mTaskFragment;
    private TaskRepository mTaskRepository;

    public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        mTaskRepository = TaskRepository.getInstance(position);
        mTaskFragment =
                TaskFragment.newInstance(mTaskRepository.getListWithPosition(position), position);
        return mTaskFragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
