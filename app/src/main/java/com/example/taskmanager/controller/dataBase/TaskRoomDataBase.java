package com.example.taskmanager.controller.dataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.taskmanager.controller.model.Task;
import com.example.taskmanager.controller.model.User;

@Database(entities = {Task.class, User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class TaskRoomDataBase extends RoomDatabase {

    public abstract TaskDao taskDao();
    public abstract UserDao userDao();
}
