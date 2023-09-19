package com.davidtest.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davidtest.model.Task;
import com.davidtest.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    // CRUD CREATE , READ , UPDATE , DELETE

    // Create
    public Task addTask(Task task) {
        task.setTaskId(UUID.randomUUID().toString().split("-")[0]);
        return repository.save(task);
    }

    // Read all tasks
    public List<Task> findAllTasks() {
        return repository.findAll();
    }

    // Read one task
    public Task getTaskByTaskId(String taskId) {
        return repository.findById(taskId).get();
    }

    // update Task
    public Task updateTask(Task taskRequest) {
        // get the existing document from DB
        // populate new value from request to existing object/entity/document
        Task existingTask = repository.findById(taskRequest.getTaskId()).get();
        existingTask.setTitle(taskRequest.getTitle());
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setCompleted(taskRequest.isCompleted());
        existingTask.setDueDate(taskRequest.getDueDate());

        return repository.save(existingTask);
    }

    public String deleteTask(String taskId) {
        repository.deleteById(taskId);
        return taskId + " task deleted";
    }
}