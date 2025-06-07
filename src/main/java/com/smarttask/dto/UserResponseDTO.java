package com.smarttask.dto;

import com.smarttask.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
    private Long userId;
    private String username;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
}
