package com.smarttask.repository;

import com.smarttask.enums.Role;
import com.smarttask.model.UserTeam;
import com.smarttask.model.UserTeamKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTeamRepository extends JpaRepository<UserTeam, UserTeamKey> {
    List<UserTeam> findByUser_UserId(Long userId);
    List<UserTeam> findByTeam_TeamId(Long teamId);
    Page<UserTeam> findByTeam_TeamId(Long teamId, Pageable pageable);
    Page<UserTeam> findByUser_UserId(Long userId, Pageable pageable);
    Page<UserTeam> findByTeam_TeamIdAndRole(Long teamId, Role role, Pageable pageable);
}
