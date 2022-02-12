package com.example.taskmanager.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.taskmanager.controller.activity.UserActivity;
import com.example.taskmanager.controller.model.Task;
import com.example.taskmanager.controller.model.User;
import com.example.taskmanager.controller.repository.TaskDBRepository;
import com.example.taskmanager.controller.repository.UserDBRepository;
import com.google.android.material.snackbar.Snackbar;

public class LoginFragment extends Fragment {

    public static final String ARGS_USER_NAME = "userName";
    public static final String ARGS_PASSWORD = "password";
    public static final String BUNDLE_LOGIN_USER_NAME = "loginUserName";
    public static final String BUNDLE_LOGIN_PASSWORD = "login_Password";
    private EditText mUserNameLogin;
    private EditText mPasswordLogin;
    private Button mButtonLogin;
    private Button mButtonSignUp;
    private ConstraintHelper mConstraintHelper;
    private UserDBRepository mUserRepository;
    private TaskDBRepository mTaskRepository;

    private String mUserNameSignUp;
    private String mPasswordSignUp;

    public LoginFragment() {
        // Required empty public constructor
    }


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

        mUserRepository = UserDBRepository.getInstance(getActivity());
        mTaskRepository = TaskDBRepository.getInstance(getActivity(), 0);

        mUserNameSignUp = getArguments().getString(ARGS_USER_NAME);
        mPasswordSignUp = getArguments().getString(ARGS_PASSWORD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        if (savedInstanceState != null){
            mUserNameSignUp = savedInstanceState.getString(BUNDLE_LOGIN_USER_NAME);
            mPasswordSignUp = savedInstanceState.getString(BUNDLE_LOGIN_PASSWORD);
        }
        initViews();
        setListeners();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_LOGIN_USER_NAME, mUserNameLogin.getText().toString());
        outState.putString(BUNDLE_LOGIN_PASSWORD, mPasswordLogin.getText().toString());
    }

    private void findViews(View view) {
        mUserNameLogin = view.findViewById(R.id.input_username);
        mPasswordLogin = view.findViewById(R.id.input_password);
        mButtonLogin = view.findViewById(R.id.btn_login);
        mButtonSignUp = view.findViewById(R.id.btn_sign_up);
        mConstraintHelper = view.findViewById(R.id.root_constraint_layout);
    }

    private void initViews(){
        mUserNameLogin.setText(mUserNameSignUp);
        mPasswordLogin.setText(mPasswordSignUp);
    }

    private void setListeners(){
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignUpActivity.newIntent(
                        getActivity(),
                        mUserNameLogin.getText().toString(),
                        mPasswordLogin.getText().toString());
                startActivity(intent);
            }
        });
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validData();

                User user = mUserRepository.getUser(mUserNameLogin.getText().toString(),
                        mPasswordLogin.getText().toString());
                if (user != null) {
                    mTaskRepository.getTask(user.getId());
                    getStartActivity(user);
                }else if (user == null)
                    Snackbar.make(mConstraintHelper,
                            "please click SIGNUP!!", Snackbar.LENGTH_LONG).show();

                    else if (mUserNameLogin.getText().toString().equals(mUserNameSignUp) &&
                            mPasswordLogin.getText().toString().equals(mPasswordSignUp)) {
                        mTaskRepository.setLists(user.getUserId());
                        getStartActivity(user);
                    } else
                        Snackbar.make(mConstraintHelper,
                                "Your information are not valid!!", Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private boolean validData(){
        if (mUserNameLogin.getText().toString().trim().isEmpty() ){
            mUserNameLogin.setError("You must enter UserName to Login!");
            return false;
        }
        if (isEmpty(mPasswordLogin)){
            mPasswordLogin.setError("You must enter Password to Login!");
            return false;
        }
        return true;
    }

    private void getStartActivity(User user) {
       /* if (user.isAdmin()) {
            Intent intent = UserActivity.newIntent(getActivity());
            startActivity(intent);
            getActivity().finish();
            return;
        }*/
        Intent intent = TaskPagerActivity.newIntent(getActivity(), 0, user.getUserId());
        startActivity(intent);
        getActivity().finish();
    }

    private boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}