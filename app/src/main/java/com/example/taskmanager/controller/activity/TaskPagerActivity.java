package com.example.taskmanager.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.adapter.TaskPagerAdapter;
import com.example.taskmanager.controller.fragment.TaskFragment;
import com.example.taskmanager.controller.repository.IRepository;
import com.example.taskmanager.controller.repository.TaskDBRepository;
import com.example.taskmanager.controller.repository.TaskRepository;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class TaskPagerActivity extends AppCompatActivity {

    public static final String EXTRA_CURRENT_POSITION = "com.example.taskmanager.currentPosition";
    public static final String EXTRA_CURRENT_USER_ID = "com.example.taskmanager.currentUserId";
    public static final int REQUEST_CODE_ADD_TASK = 100;
    private TaskPagerAdapter mAdapter;
    public static IRepository mRepository;
    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;
    private String[] mState=new String[]{"TODO","DOING","DONE"};
    private List<Fragment> mFragmentList = new ArrayList<>();
    private int mCurrentPosition;
    private long mUserId;

    public static Intent newIntent(Context context, int position, long userId){
        Intent intent = new Intent(context, TaskPagerActivity.class);
        intent.putExtra(EXTRA_CURRENT_POSITION, position);
        intent.putExtra(EXTRA_CURRENT_USER_ID,userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);
        mCurrentPosition = getIntent().getIntExtra(EXTRA_CURRENT_POSITION, 0);
        mUserId = getIntent().getLongExtra(EXTRA_CURRENT_USER_ID, 0);
        mRepository = TaskDBRepository.getInstance(this, mCurrentPosition);


        findViews();
        setAdapter();
    }

    private void findViews() {
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tabs);
    }

    private void setAdapter() {
        mAdapter = new TaskPagerAdapter(this, this, mUserId );
        mViewPager.setCurrentItem(mCurrentPosition);
        mViewPager.setAdapter(mAdapter);


        new TabLayoutMediator(mTabLayout, mViewPager,
                (tab, position) -> tab.setText(mState[position])
        ).attach();
    }

 /*   private void setListeners() {

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentPosition = mTabLayout.getSelectedTabPosition();
                AddTaskFragment fragment = AddTaskFragment.newInstance(mCurrentPosition, mUserId);
                fragment.setTargetFragment(
                        mTaskPagerAdapter.getFragments(mCurrentPosition), REQUEST_CODE_ADD_TASK);
                fragment.show(getSupportFragmentManager(), FRAGMENT_TAG_ADD_TASK);
            }
        });
    }*/

}