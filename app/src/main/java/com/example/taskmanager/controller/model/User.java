package com.example.taskmanager.controller.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "userTable")
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    private long mUserId;

    @ColumnInfo(name = "uuid")
    private UUID mId;

    @ColumnInfo(name = "userName")
    private String mUserName;

    @ColumnInfo(name = "password")
    private String mPassword;

    @ColumnInfo(name = "date")
    private Date mDate;

    @ColumnInfo(name = "taskId")
    private long mTaskId;

    @ColumnInfo(name = "taskNumber")
    private int mTaskNumber;



    public User() {
        mId = mId.randomUUID();
        mDate = new Date();
    }

    public User(UUID id, String userName, String passWord, Date date, long taskId,
                int taskNumber) {
        mId = id;
        mUserName = userName;
        mPassword = passWord;
        mDate = date;
        mTaskId = taskId;
        mTaskNumber = taskNumber;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
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

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(long userId) {
        mUserId = userId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public long getTaskId() {
        return mTaskId;
    }

    public void setTaskId(long taskId) {
        mTaskId = taskId;
    }

    public int getTaskNumber() {
        return mTaskNumber;
    }

    public void setTaskNumber(int taskNumber) {
        mTaskNumber = taskNumber;
    }
}
