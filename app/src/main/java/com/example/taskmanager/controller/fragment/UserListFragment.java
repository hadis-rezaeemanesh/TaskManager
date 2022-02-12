package com.example.taskmanager.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.activity.TaskPagerActivity;
import com.example.taskmanager.controller.model.User;
import com.example.taskmanager.controller.repository.TaskDBRepository;
import com.example.taskmanager.controller.repository.UserDBRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private UserDBRepository mUserDBRepository;
    private TaskDBRepository mTaskDBRepository;
    private List<User> mUserList = new ArrayList<>();
    private long mUserId;
    private UserAdapter mAdapter;



    public UserListFragment() {
        // Required empty public constructor
    }


    public static UserListFragment newInstance() {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity(), 0);
        mUserDBRepository = UserDBRepository.getInstance(getActivity());
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.rv_user_list);
    }

    private void initViews(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }

    private void updateUI() {
        mUserList = mUserDBRepository.getUsers();
        int[] result = mUserDBRepository.setUserTaskNumber();
        for (int i = 0; i < result.length; i++) {
            mUserList.get(i).setTaskNumber(result[i]);
        }
        if (mAdapter == null) {
            mAdapter = new UserAdapter(mUserList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setUserList(mUserList);
            mAdapter.notifyDataSetChanged();
        }
    }

    class UserHolder extends RecyclerView.ViewHolder{

        private TextView mUserName;
        private TextView mNumberOfTask;
        private TextView mDate;
        private ImageButton mDelete;

        private User mUser;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mUserDBRepository.deleteUser(mUser);
                    updateUI();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTaskDBRepository.setLists(mUser.getUserId());
                    Intent intent =
                            TaskPagerActivity.newIntent(getActivity(), 0, mUser.getUserId());
                    startActivity(intent);
                    getActivity().finish();
                }
            });

        }

        private void findViews(@NonNull View itemView) {
            mUserName = itemView.findViewById(R.id.txtview_username);
            mNumberOfTask = itemView.findViewById(R.id.txtview_task_number);
            mDelete = itemView.findViewById(R.id.btn_delete);
            mDate = itemView.findViewById(R.id.txtview_signup_date);
        }

        public void bindUser(User user){
            mUser = user;
            mUserName.setText(user.getUserName());
            mNumberOfTask.setText(user.getTaskNumber()+" Task");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:SS");
            mDate.setText(simpleDateFormat.format(user.getDate()));
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder>{

        private List<User> mUserList;

        public List<User> getUserList() {
            return mUserList;
        }

        public void setUserList(List<User> userList) {
            mUserList = userList;
        }

        public UserAdapter(List<User> userList) {
            mUserList = userList;
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UserHolder(LayoutInflater.from(getActivity()).inflate(
                    R.layout.item_user_list,
                    parent,
                    false
            ));
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            holder.bindUser(mUserList.get(position));
        }

        @Override
        public int getItemCount() {
            return mUserList.size();
        }
    }


}