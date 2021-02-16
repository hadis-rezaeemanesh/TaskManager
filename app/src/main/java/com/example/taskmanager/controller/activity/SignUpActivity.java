package com.example.taskmanager.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.fragment.SignUpFragment;

public class SignUpActivity extends SingleActivityFragment {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return SignUpFragment.newInstance();
    }
}