package com.example.taskmanager.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.activity.LoginActivity;
import com.example.taskmanager.controller.model.User;

import java.util.zip.Inflater;

public class SignUpFragment extends Fragment {

    public static final String ARGS_USER_NAME = "userName";
    public static final String ARGS_PASSWORD = "password";
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private Button mButtonSignUp;

    private String mUserName;
    private String mPassword;

    public SignUpFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String userName, String password) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_USER_NAME, userName);
        args.putString(ARGS_PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserName = getArguments().getString(ARGS_USER_NAME);
        mPassword = getArguments().getString(ARGS_PASSWORD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        findViews(view);
        initView();
        setListeners();
        return view;
    }
    private void findViews(View view) {
        mEditTextUsername = view.findViewById(R.id.input_sign_up_username);
        mEditTextPassword =view.findViewById(R.id.input_sign_up_password);
        mButtonSignUp = view.findViewById(R.id.btn_sign_up);
    }

    private void initView(){
        mEditTextUsername.setText(mUserName);
        mEditTextPassword.setText(mPassword);
    }
    private void setListeners(){
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDataEntered()) {
                    String userName = mEditTextUsername.getText().toString();
                    String password =  mEditTextPassword.getText().toString();

                    User user = new User();
                    user.setUserName(userName);
                    user.setPassword(password);

                    Intent intent = LoginActivity.newIntent(getActivity(),userName, password);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean checkDataEntered(){
        if (isEmpty(mEditTextUsername)) {
            mEditTextUsername.setError("You must enter UserName!!");
            return false;
        }
        if (isEmpty(mEditTextPassword)) {
            mEditTextPassword.setError("Password is required!");
            return false;
        }
        return true;
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}