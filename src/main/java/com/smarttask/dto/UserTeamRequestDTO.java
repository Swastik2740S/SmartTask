package com.smarttask.dto;

import com.smarttask.enums.Role;
import lombok.Data;

@Data
public class UserTeamRequestDTO {
    private Long userId;
    private Long teamId;
    private Role role;
}
