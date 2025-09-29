package com.metehanayhan.TaskTracker.service;

import com.metehanayhan.TaskTracker.entity.Task;
import com.metehanayhan.TaskTracker.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import com.metehanayhan.TaskTracker.mapper.TaskMapper;
import com.metehanayhan.TaskTracker.dto.TaskResponseDTO;
import com.metehanayhan.TaskTracker.dto.CreateTaskRequestDTO;
import com.metehanayhan.TaskTracker.dto.UpdateTaskRequestDTO;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<TaskResponseDTO> getAllTasks() {

        List<Task> tasks = taskRepository.findAll();

        return tasks.stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID'si " + id + " olan görev bulunamadı."));

        return TaskMapper.toDTO(task);
    }

    public TaskResponseDTO createTask(CreateTaskRequestDTO requestDTO) {

        Task newTask = TaskMapper.toTask(requestDTO);
        Task savedTask = taskRepository.save(newTask);
        return TaskMapper.toDTO(savedTask);
    }

    public TaskResponseDTO updateTask(Long id, UpdateTaskRequestDTO taskDetails) {
        Task mevcutTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID'si " + id + " olan görev bulunamadı."));

        // TaskMapper'ı kullanarak güncelleme yapıyoruz
        Task updatedTask = TaskMapper.updateTaskFromDTO(mevcutTask, taskDetails);
        updatedTask = taskRepository.save(updatedTask);

        return TaskMapper.toDTO(updatedTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}