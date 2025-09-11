package com.metehanayhan.TaskTracker.service;

import com.metehanayhan.TaskTracker.entity.Task;
import com.metehanayhan.TaskTracker.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID'si " + id + " olan görev bulunamadı."));
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        // Önce mevcut görevi bulalım. Bulamazsa getTaskById zaten hata fırlatacak.
        Task existingTask = getTaskById(id);

        // Mevcut görevin alanlarını yeni detaylarla güncelleyelim.
        existingTask.setTitle(taskDetails.getTitle());
        existingTask.setCompleted(taskDetails.isCompleted());

        // Güncellenmiş nesneyi kaydedelim.
        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
