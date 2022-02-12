package com.example.taskmanager.controller.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithTask {

    @Embedded
    public User mUser;

    @Relation(
            parentColumn = "userId",
            entityColumn = "userCreatorId"
    )
    public List<Task> mTasks;
}
