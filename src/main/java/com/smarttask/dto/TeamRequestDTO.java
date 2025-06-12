package com.smarttask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamRequestDTO {
    @NotBlank(message = "Team name is required")
    private String name;

    private String description;
}
