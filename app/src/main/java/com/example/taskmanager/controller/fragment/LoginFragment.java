package com.example.taskmanager.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.activity.SignUpActivity;
import com.example.taskmanager.controller.activity.TaskPagerActivity;
import com.google.android.material.snackbar.Snackbar;

public class LoginFragment extends Fragment {

    public static final String ARGS_USER_NAME = "userName";
    public static final String ARGS_PASSWORD = "password";
    private EditText mUserNameLogin;
    private EditText mPasswordLogin;
    private Button mButtonLogin;
    private Button mButtonSignUp;
    private ConstraintHelper mConstraintHelper;

    private String mUserNameSignUp;
    private String mPasswordSignUp;

    public LoginFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String userName, String password) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_USER_NAME, userName);
        args.putString(ARGS_PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserNameSignUp = getArguments().getString(ARGS_USER_NAME);
        mPasswordSignUp = getArguments().getString(ARGS_PASSWORD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        setListeners();
        return view;
    }

    private void findViews(View view) {
        mUserNameLogin = view.findViewById(R.id.input_username);
        mPasswordLogin = view.findViewById(R.id.input_password);
        mButtonLogin = view.findViewById(R.id.btn_login);
        mButtonSignUp = view.findViewById(R.id.btn_sign_up);
        mConstraintHelper = view.findViewById(R.id.root_constraint_layout);
    }

    private void setListeners(){
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignUpActivity.newIntent(
                        getActivity(),
                        mUserNameSignUp,
                        mPasswordSignUp);
                startActivity(intent);
            }
        });
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validData()) {
                    if (mUserNameSignUp == null || mPasswordSignUp == null)
                        Snackbar.make(mConstraintHelper,
                                "please click SIGNUP!!", Snackbar.LENGTH_LONG).show();

                    else if (mUserNameLogin.getText().toString().equals(mUserNameSignUp) &&
                            mPasswordLogin.getText().toString().equals(mPasswordSignUp)) {
                        Intent intent = TaskPagerActivity.newIntent(getActivity());
                        startActivity(intent);
                    } else
                        Snackbar.make(mConstraintHelper,
                                "Your information are not valid!!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validData(){
        if (isEmpty(mUserNameLogin) ){
            mUserNameLogin.setError("You must enter UserName to Login!");
            return false;
        }
        if (isEmpty(mPasswordLogin)){
            mPasswordLogin.setError("You must enter Password to Login!");
            return false;
        }
        return true;
    }

    private boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}