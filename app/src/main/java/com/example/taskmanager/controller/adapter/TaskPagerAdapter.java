package com.example.taskmanager.controller.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.taskmanager.controller.fragment.TaskFragment;
import com.example.taskmanager.controller.model.State;

public class TaskPagerAdapter extends FragmentStateAdapter {

    private State mState;

    public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return TaskFragment.newInstance("ToDo");
            case 1:
                return TaskFragment.newInstance("Doing");
            case 2:
                return TaskFragment.newInstance("Done");
            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
