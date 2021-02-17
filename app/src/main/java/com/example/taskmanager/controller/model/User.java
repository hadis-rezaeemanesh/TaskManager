package com.example.taskmanager.controller.model;

import java.util.UUID;

public class User {

    private UUID mId;
    private String mUserName;
    private String mPassword;

    public User() {
        mId = mId.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
