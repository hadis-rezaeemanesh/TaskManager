package com.example.taskmanager.controller.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.taskmanager.controller.dataBase.TaskRoomDataBase;
import com.example.taskmanager.controller.dataBase.UserDao;
import com.example.taskmanager.controller.model.Task;
import com.example.taskmanager.controller.model.User;
import com.example.taskmanager.controller.model.UserWithTask;

import java.util.List;
import java.util.UUID;

public class UserDBRepository implements UserDao {

    private static UserDBRepository sInstance;
    private UserDao mUserDao;
    private Context mContext;

    public static UserDBRepository getInstance(Context context) {
        if (sInstance == null)
            return new UserDBRepository(context);
        return sInstance;
    }

    public UserDBRepository(Context context) {
        mContext = context.getApplicationContext();

        TaskRoomDataBase taskRoomDataBase =
                Room.databaseBuilder(mContext, TaskRoomDataBase.class, "task.db")
                        .allowMainThreadQueries().build();

        mUserDao = taskRoomDataBase.userDao();
    }

    @Override
    public void insertUser(User user) {
        mUserDao.insertUser(user);

    }

    @Override
    public void deleteUser(User user) {
        mUserDao.deleteUser(user);
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public List<User> getUsers() {
        return mUserDao.getUsers();
    }

    @Override
    public User getUser(UUID uuid) {
        return mUserDao.getUser(uuid);
    }

    @Override
    public User getUser(String userName, String password) {
        return mUserDao.getUser(userName, password);
    }

    @Override
    public List<Task> getUserTasks(long userId) {
        return mUserDao.getUserTasks(userId);
    }

    @Override
    public List<UserWithTask> getUserWithTask() {
        return mUserDao.getUserWithTask();
    }

    public int[] setUserTaskNumber(){
        List<UserWithTask> userWithTasks = mUserDao.getUserWithTask();
        int[] result = new int[userWithTasks.size()];
        for (int i = 0; i < userWithTasks.size(); i++) {
            result[i] = userWithTasks.get(i).mTasks.size();
        }
        return result;
    }
}
