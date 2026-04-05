package com.example.task_service.service;

import com.example.task_service.entity.Task;
import com.example.task_service.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public Task create(Task t, String user) {
        t.setCreatedBy(user);
        t.setCreatedAt(LocalDateTime.now());
        t.setUpdatedAt(LocalDateTime.now());
        return repo.save(t);
    }

    public List<Task> get(String user, boolean isAdmin) {
        return isAdmin ? repo.findAll() : repo.findByCreatedBy(user);
    }
    public Task update(Long id, Task t, String user, boolean isAdmin) {

        Task task = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!isAdmin && !task.getCreatedBy().equals(user)) {
            throw new RuntimeException("Unauthorized");
        }

        task.setTitle(t.getTitle());
        task.setDescription(t.getDescription());
        task.setStatus(t.getStatus());
        task.setUpdatedAt(LocalDateTime.now());
        return repo.save(task);
    }
    public void delete(Long id, String user, boolean isAdmin) {

        Task task = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!isAdmin && !task.getCreatedBy().equals(user)) {
            throw new RuntimeException("Unauthorized");
        }

        repo.delete(task);
    }

}
