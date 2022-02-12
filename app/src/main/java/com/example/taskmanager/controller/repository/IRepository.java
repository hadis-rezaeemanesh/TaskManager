package com.example.taskmanager.controller.repository;

import com.example.taskmanager.controller.model.Task;

import java.util.List;
import java.util.UUID;

public interface IRepository {
    List<Task> getTasksWithState(int position, long userId);

    List<Task> getTasks();

    Task getTask(UUID id);

    void insertTask(Task task);

    void deleteTask(Task task);

    void updateTask(Task task);
}
