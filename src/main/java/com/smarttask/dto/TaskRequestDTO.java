package com.smarttask.dto;

import com.smarttask.enums.Priority;
import com.smarttask.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequestDTO {
    @NotBlank(message = "Task title is required")
    private String title;

    private String description;

    private TaskStatus status; // Optional, default to TODO

    private Priority priority; // Optional, default to MEDIUM

    private LocalDateTime dueDate;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private Long assignedTo; // userId (optional)
}
