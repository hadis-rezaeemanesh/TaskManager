package com.example.taskmanager.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.fragment.LoginFragment;

public class LoginActivity extends SingleActivityFragment {

    public static final String EXTRA_USER_NAME_LOGIN = "com.example.taskmanager.controller.activity.userNameLogin";
    public static final String EXTRA_PASSWORD_LOGIN = "com.example.taskmanager.controller.activity.passwordLogin";

    public static Intent newIntent(Context context, String userName, String password){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(EXTRA_USER_NAME_LOGIN, userName);
        intent.putExtra(EXTRA_PASSWORD_LOGIN, password);
        return intent;
    }
                                   @Override
    public Fragment createFragment() {
        String userName = getIntent().getStringExtra(EXTRA_USER_NAME_LOGIN);
        String password = getIntent().getStringExtra(EXTRA_PASSWORD_LOGIN);
        return LoginFragment.newInstance(userName, password);
    }

}