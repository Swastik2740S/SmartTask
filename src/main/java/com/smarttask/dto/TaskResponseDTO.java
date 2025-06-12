package com.smarttask.dto;

import com.smarttask.enums.Priority;
import com.smarttask.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDTO {
    private Long taskId;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private Long projectId;
    private Long assignedTo; // userId
}
