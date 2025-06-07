package com.smarttask.repository;

import com.smarttask.model.UserTeam;
import com.smarttask.model.UserTeamKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTeamRepository extends JpaRepository<UserTeam, UserTeamKey> {}
