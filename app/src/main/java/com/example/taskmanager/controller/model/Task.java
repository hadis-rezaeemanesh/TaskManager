package com.example.taskmanager.controller.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.taskmanager.controller.utiles.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
@Entity(tableName = "taskTable")
public class Task {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    private long primaryId;

    @ColumnInfo(name = "uuid")
    private UUID mId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDes;

    @ColumnInfo(name = "done")
    private boolean mDone;

    @ColumnInfo(name = "date")
    private Date mDate;

    @ColumnInfo(name = "time")
    private long mTime;

    @ColumnInfo(name = "position")
    private int mPosition;

    @ColumnInfo(name = "userCreatorId")
    private long mUserCreatorId;

    @Ignore
    private SimpleDateFormat mFormat;

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDes() {
        return mDes;
    }

    public void setDes(String des) {
        mDes = des;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
    }

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }

    public long getUserCreatorId() {
        return mUserCreatorId;
    }

    public void setUserCreatorId(long userCreatorId) {
        mUserCreatorId = userCreatorId;
    }

    public Task() {
        this(UUID.randomUUID());
    }

    public Task(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public Task(UUID id, String description, String title, Date date, long time, boolean done,
                int position, long userCreatorId) {
        mId = id;
        mDes = description;
        mTitle = title;
        mDate = date;
        mDone = done;
        mTime = time;
        mPosition = position;
        mUserCreatorId = userCreatorId;
    }

    public String getSimpleDate() {
        mFormat = new SimpleDateFormat("dd MMM yyyy");
        return mFormat.format(mDate);

    }

    public String getSimpleTime() {
        mFormat = new SimpleDateFormat("hh:mm a");
        return mFormat.format(mDate);

    }

}
