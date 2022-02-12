package com.example.taskmanager.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager.controller.fragment.UserListFragment;

public class UserActivity extends SingleActivityFragment {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, UserActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return UserListFragment.newInstance();
    }

}