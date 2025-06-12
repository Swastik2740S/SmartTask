package com.smarttask.dto;

import com.smarttask.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectRequestDTO {
    @NotBlank(message = "Project name is required")
    private String name;

    private String description;

    @NotNull(message = "Team ID is required")
    private Long teamId;

    private ProjectStatus status; // Optional on create, can default to PLANNING
}
