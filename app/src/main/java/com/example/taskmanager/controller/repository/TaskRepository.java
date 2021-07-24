package com.example.taskmanager.controller.repository;

import com.example.taskmanager.controller.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private static TaskRepository sInstance;

    private List<Task> mToDoTasks;
    private List<Task> mDoingTasks;
    private List<Task> mDoneTasks;
    private List<Task> mTasks = new ArrayList<>();
    private List[] mTaskListState = {mToDoTasks, mDoingTasks, mDoneTasks};
    private int mCurrentPosition;

    public static TaskRepository getInstance(int position){
        if (sInstance == null)
            sInstance = new TaskRepository(position);
        return sInstance;
    }

    public TaskRepository(int position) {
        for (int i = 0; i < mTaskListState.length; i++){
            mTaskListState[i] = new ArrayList();
        }
        mCurrentPosition = position;
    }

    public List<Task> getToDoTasks() {
        return mToDoTasks;
    }

    public void setToDoTasks(List<Task> toDoTasks) {
        mToDoTasks = toDoTasks;
    }

    public List<Task> getDoingTasks() {
        return mDoingTasks;
    }

    public void setDoingTasks(List<Task> doingTasks) {
        mDoingTasks = doingTasks;
    }

    public List<Task> getDoneTasks() {
        return mDoneTasks;
    }

    public void setDoneTasks(List<Task> doneTasks) {
        mDoneTasks = doneTasks;
    }

    public List<Task> getList(){
        return mTasks;
    }

    public Task getItem(){
        return null;
    }

    public void deleteItem(Task task){

    }

    public Task updateItem(Task task){
        return null;
    }

    public void insertTask(Task task, int position){
        mTaskListState[position].add(task);

    }
    public List<Task> getListWithPosition(int position){
        switch (position){
            case 0:
                return mTaskListState[0];
            case 1:
                return mTaskListState[1];
            default:
                return mTaskListState[2];
        }

    }
}
