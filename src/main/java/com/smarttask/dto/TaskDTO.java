package com.smarttask.dto;

import com.smarttask.enums.Priority;
import com.smarttask.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Status is required")
    private TaskStatus status;

    @NotNull(message = "Priority is required")
    private Priority priority;

    private LocalDateTime dueDate;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private Long assignedTo;
}
