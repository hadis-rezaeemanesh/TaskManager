package com.example.taskmanager.controller.dataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.taskmanager.controller.model.Task;
import com.example.taskmanager.controller.model.User;
import com.example.taskmanager.controller.model.UserWithTask;

import java.util.List;
import java.util.UUID;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM userTable")
    List<User> getUsers();

    @Query("SELECT * FROM userTable WHERE uuid = :uuid")
    User getUser(UUID uuid);

    @Query("SELECT * FROM userTable WHERE userName = :userName & password = :password")
    User getUser(String userName, String password);

    @Query("SELECT * FROM taskTable WHERE userCreatorId = :userId")
    List<Task> getUserTasks(long userId);

    @Transaction
    @Query("SELECT * FROM userTable")
    List<UserWithTask> getUserWithTask();
}
