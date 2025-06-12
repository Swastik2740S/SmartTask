package com.smarttask.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TeamResponseDTO {
    private Long teamId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
