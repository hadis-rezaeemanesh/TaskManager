package com.example.taskmanager.controller.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.taskmanager.R;
import com.example.taskmanager.controller.dataBase.TaskDao;
import com.example.taskmanager.controller.dataBase.TaskRoomDataBase;
import com.example.taskmanager.controller.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDBRepository implements TaskDao {

    private static TaskDBRepository sInstance;
    private TaskDao mTaskDao;
    private Context mContext;
    private int mCurrentPosition;
    private List<Task> mTasks;
    private List<Task> mTodoTasks = new ArrayList<>();
    private List<Task> mDoingTasks = new ArrayList<>();
    private List<Task> mDoneTasks = new ArrayList<>();
    private long mUserId;


    public static TaskDBRepository getInstance(Context context, int position) {
        if (sInstance == null)
            sInstance = new TaskDBRepository(context, position);
        return sInstance;
    }

    public TaskDBRepository(Context context, int position) {
        mContext = context.getApplicationContext();

        TaskRoomDataBase taskRoomDataBase = Room.databaseBuilder(
                mContext, TaskRoomDataBase.class, "task.db").allowMainThreadQueries().build();

        mTaskDao = taskRoomDataBase.taskDao();
        mCurrentPosition = position;
    }

    @Override
    public void insertTask(Task task) {
        mTaskDao.insertTask(task);
    }

    @Override
    public void deleteTask(Task task) {
        mTaskDao.deleteTask(task);
    }

    @Override
    public void updateTask(Task task) {
        mTaskDao.updateTask(task);
    }

    @Override
    public List<Task> getTasks() {
        return mTaskDao.getTasks();
    }

    @Override
    public Task getTask(UUID id) {
        return mTaskDao.getTask(id);
    }

    @Override
    public List<Task> getTasksWithState(int position, long userId) {
        return mTaskDao.getTasksWithState(position, userId);
    }

    @Override
    public void deleteAll(long userId) {
        mTaskDao.deleteAll(userId);
    }

    public void setLists(long userId){
        mTasks = new ArrayList<>();
        mUserId = userId;
        mTodoTasks.clear();
        mDoingTasks.clear();
        mDoneTasks.clear();

        for (int i = 0; i < getTasks().size(); i++) {
            if (getTasks().get(i).getUserCreatorId() == mUserId){
                mTasks.add(getTasks().get(i));
            }
        }

        for (int i = 0; i < mTasks.size(); i++) {
            switch (mTasks.get(i).getPosition()){
                case 0:
                    mTodoTasks.add(mTasks.get(i));
                    break;
                case 1:
                    mDoingTasks.add(mTasks.get(i));
                    break;
                case 2:
                    mDoneTasks.add(mTasks.get(i));
                    break;

            }
        }
    }

    public List<Task> getListWithPosition(int position){
        mCurrentPosition = position;
        switch (mCurrentPosition){
            case 0:
                return mTodoTasks;
            case 1:
                return mDoingTasks;
            default:
                return mDoneTasks;
        }
    }

    public void updateList(){
        mTasks = new ArrayList<>();
        mTodoTasks.clear();
        mDoingTasks.clear();
        mDoneTasks.clear();

        for (int i = 0; i < getTasks().size(); i++) {
            if (getTasks().get(i).getUserCreatorId() == mUserId){
                mTasks.add(getTasks().get(i));
            }
        }

        for (int i = 0; i < mTasks.size(); i++) {
            switch (mTasks.get(i).getPosition()){
                case 0:
                    mTodoTasks.add(mTasks.get(i));
                    break;
                case 1:
                    mDoingTasks.add(mTasks.get(i));
                    break;
                case 2:
                    mDoneTasks.add(mTasks.get(i));
                    break;
            }

        }
    }


}
