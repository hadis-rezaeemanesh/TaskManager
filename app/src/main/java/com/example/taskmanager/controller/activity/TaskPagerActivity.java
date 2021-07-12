package com.example.taskmanager.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.adapter.TaskPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TaskPagerActivity extends AppCompatActivity {
    private TaskPagerAdapter mAdapter;
    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;
    private String[] mState=new String[]{"TODO","DOING","DONE"};

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, TaskPagerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);

        findViews();
        setAdapter();
    }

    private void findViews() {
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tabs);
    }

    private void setAdapter() {
        mAdapter = new TaskPagerAdapter(this);
        mViewPager.setAdapter(mAdapter);
        new TabLayoutMediator(mTabLayout, mViewPager,
                (tab, position) -> tab.setText(mState[position])
        ).attach();
    }

}