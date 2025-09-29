package com.metehanayhan.TaskTracker.dto;
import lombok.Data;

@Data
public class TaskResponseDTO {

    private Long id;
    private String title;
    private boolean completed;
}
