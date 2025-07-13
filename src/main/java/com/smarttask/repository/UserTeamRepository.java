package com.smarttask.repository;

import com.smarttask.model.UserTeam;
import com.smarttask.model.UserTeamKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Repository for managing UserTeam join table with composite key.
 * Provides efficient queries for team membership and user participation.
 */
public interface UserTeamRepository extends JpaRepository<UserTeam, UserTeamKey> {

    /**
     * Find all user-team memberships for a given team.
     * @param teamId the team ID
     * @return list of UserTeam associations
     */
    List<UserTeam> findByTeam_TeamId(Long teamId);

    /**
     * Find all team memberships for a given user.
     * @param userId the user ID
     * @return list of UserTeam associations
     */
    List<UserTeam> findByUser_UserId(Long userId);

    /**
     * (Optional) Paginated: Find all user-team memberships for a given team.
     * @param teamId the team ID
     * @param pageable pagination info
     * @return page of UserTeam associations
     */
    Page<UserTeam> findByTeam_TeamId(Long teamId, Pageable pageable);

    /**
     * (Optional) Paginated: Find all team memberships for a given user.
     * @param userId the user ID
     * @param pageable pagination info
     * @return page of UserTeam associations
     */
    Page<UserTeam> findByUser_UserId(Long userId, Pageable pageable);
}
