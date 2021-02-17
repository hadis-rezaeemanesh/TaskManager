package com.example.taskmanager.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.fragment.SignUpFragment;

public class SignUpActivity extends SingleActivityFragment {

    public static final String EXTRA_USER_NAME_SIGN_UP = "com.example.taskmanager.controller.activity.userNameSignUp";
    public static final String EXTRA_PASSWORD_SIGN_UP = "com.example.taskmanager.controller.activity.passwordSignUp";

    public static Intent newIntent(Context context, String userName, String password){
        Intent intent = new Intent(context, SignUpActivity.class);
        intent.putExtra(EXTRA_USER_NAME_SIGN_UP, userName);
        intent.putExtra(EXTRA_PASSWORD_SIGN_UP, password);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        String userName = getIntent().getStringExtra(EXTRA_USER_NAME_SIGN_UP);
        String password = getIntent().getStringExtra(EXTRA_PASSWORD_SIGN_UP);
        return SignUpFragment.newInstance(userName, password);
    }
}