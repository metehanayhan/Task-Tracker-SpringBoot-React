package com.metehanayhan.TaskTracker.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "tasks")
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private boolean Completed;

}

