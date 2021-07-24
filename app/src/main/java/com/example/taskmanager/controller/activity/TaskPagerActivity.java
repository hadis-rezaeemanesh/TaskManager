package com.example.taskmanager.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.adapter.TaskPagerAdapter;
import com.example.taskmanager.controller.fragment.TaskFragment;
import com.example.taskmanager.controller.repository.TaskRepository;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class TaskPagerActivity extends AppCompatActivity {

    public static final String EXTRA_CURRENT_POSITION = "com.example.taskmanager.currentPosition";
    private TaskPagerAdapter mAdapter;
    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;
    private String[] mState=new String[]{"TODO","DOING","DONE"};
    private List<Fragment> mFragmentList = new ArrayList<>();
    private TaskRepository mRepository;
    private int mCurrentPosition;

    public static Intent newIntent(Context context, int position){
        Intent intent = new Intent(context, TaskPagerActivity.class);
        intent.putExtra(EXTRA_CURRENT_POSITION, position);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);
        mCurrentPosition = getIntent().getIntExtra(EXTRA_CURRENT_POSITION, 0);
        mRepository = TaskRepository.getInstance(mCurrentPosition);
        findViews();
        initViews();
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

    public void initViews(){
        mFragmentList.add(TaskFragment.newInstance(mRepository.getToDoTasks(), 0));
        mFragmentList.add(TaskFragment.newInstance(mRepository.getDoingTasks(), 1));
        mFragmentList.add(TaskFragment.newInstance(mRepository.getDoneTasks(), 2));
    }

}