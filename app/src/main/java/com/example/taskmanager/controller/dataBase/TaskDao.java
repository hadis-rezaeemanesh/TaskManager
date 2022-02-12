package com.example.taskmanager.controller.dataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanager.controller.model.Task;
import com.example.taskmanager.controller.repository.IRepository;

import java.util.List;
import java.util.UUID;

@Dao
public interface TaskDao extends IRepository {

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM taskTable")
    List<Task> getTasks();

    @Query("SELECT * FROM taskTable WHERE uuid = :id")
    Task getTask(UUID id);

    @Query("SELECT * FROM taskTable WHERE position = :position & userCreatorId = :userId")
    List<Task> getTasksWithState(int position, long userId);

    @Query("DELETE FROM taskTable WHERE userCreatorId = :userId")
    void deleteAll(long userId);
}
