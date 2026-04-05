package com.example.task_service.controller;

import com.example.task_service.entity.Task;
import com.example.task_service.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id,
                       @RequestBody Task t,
                       @RequestHeader("username") String user,
                       @RequestHeader("role") String role) {

        return service.update(id, t, user, role.equals("ADMIN"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @RequestHeader("username") String user,
                       @RequestHeader("role") String role) {

        service.delete(id, user, role.equals("ADMIN"));
    }

    @PostMapping
    public Task create(@Valid @RequestBody Task t,
                       @RequestHeader("username") String user) {
        return service.create(t, user);
    }

    @GetMapping
    public List<Task> get(@RequestHeader("username") String user,
                          @RequestHeader("role") String role) {
        return service.get(user, role.equals("ADMIN"));
    }
}
