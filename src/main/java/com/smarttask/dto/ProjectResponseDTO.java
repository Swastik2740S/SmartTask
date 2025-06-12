package com.smarttask.dto;

import com.smarttask.enums.ProjectStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectResponseDTO {
    private Long projectId;
    private String name;
    private String description;
    private ProjectStatus status;
    private Long teamId;
    private LocalDateTime createdAt;
}
