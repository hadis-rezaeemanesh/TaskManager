package com.example.taskmanager.controller.repository;

import android.content.Context;

import com.example.taskmanager.controller.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private static TaskRepository sInstance;

    private List<Task> mTasks = new ArrayList<>();

    public static TaskRepository getInstance(){
        if (sInstance == null)
            sInstance = new TaskRepository();
        return sInstance;
    }

    public TaskRepository() {


    }
}
