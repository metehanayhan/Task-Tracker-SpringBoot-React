package com.metehanayhan.TaskTracker.mapper;
import com.metehanayhan.TaskTracker.entity.Task;
import com.metehanayhan.TaskTracker.dto.TaskResponseDTO;
import com.metehanayhan.TaskTracker.dto.CreateTaskRequestDTO;
import com.metehanayhan.TaskTracker.dto.UpdateTaskRequestDTO;

public class TaskMapper {

    public static TaskResponseDTO toDTO(Task task) {

        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setCompleted(task.isCompleted());

        return dto;
    }

    public static Task toTask(CreateTaskRequestDTO requestDTO) {

        Task task = new Task();
        task.setTitle(requestDTO.getTitle());
        task.setCompleted(false);

        return task;
    }

    public static Task updateTaskFromDTO(Task mevcutTask, UpdateTaskRequestDTO updateDTO) {
        mevcutTask.setCompleted(updateDTO.isCompleted());
        return mevcutTask;
    }
}