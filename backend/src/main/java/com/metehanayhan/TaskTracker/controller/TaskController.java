package com.metehanayhan.TaskTracker.controller;
import com.metehanayhan.TaskTracker.entity.Task;
import com.metehanayhan.TaskTracker.service.TaskService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.metehanayhan.TaskTracker.dto.CreateTaskRequestDTO;
import com.metehanayhan.TaskTracker.dto.TaskResponseDTO;
import com.metehanayhan.TaskTracker.mapper.TaskMapper;
import com.metehanayhan.TaskTracker.dto.UpdateTaskRequestDTO;

@RequestMapping("/api/tasks")
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponseDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public TaskResponseDTO createTask(@RequestBody CreateTaskRequestDTO taskRequest) {
        return taskService.createTask(taskRequest);
    }

    @PutMapping("/{id}")
    public TaskResponseDTO updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequestDTO taskDetails) {
        return taskService.updateTask(id, taskDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}