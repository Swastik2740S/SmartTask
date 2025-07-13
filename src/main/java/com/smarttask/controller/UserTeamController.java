package com.smarttask.controller;

import com.smarttask.dto.UserTeamRequestDTO;
import com.smarttask.dto.UserTeamResponseDTO;
import com.smarttask.service.UserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams/members")
public class UserTeamController {

    private final UserTeamService userTeamService;

    @Autowired
    public UserTeamController(UserTeamService userTeamService) {
        this.userTeamService = userTeamService;
    }

    // Add a user to a team
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    @PostMapping
    public ResponseEntity<UserTeamResponseDTO> addUserToTeam(@RequestBody UserTeamRequestDTO request) {
        UserTeamResponseDTO response = userTeamService.addUserToTeam(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Bulk add users to a team
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<List<UserTeamResponseDTO>> bulkAddUsersToTeam(@RequestBody List<UserTeamRequestDTO> requests) {
        List<UserTeamResponseDTO> responses = userTeamService.bulkAddUsersToTeam(requests);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    // Update a user's role in a team
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    @PutMapping
    public ResponseEntity<UserTeamResponseDTO> updateUserRole(@RequestBody UserTeamRequestDTO request) {
        UserTeamResponseDTO response = userTeamService.updateUserRoleInTeam(request);
        return ResponseEntity.ok(response);
    }

    // Remove a user from a team
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    @DeleteMapping("/{userId}/{teamId}")
    public ResponseEntity<Void> removeUserFromTeam(
            @PathVariable Long userId,
            @PathVariable Long teamId
    ) {
        userTeamService.removeUserFromTeam(userId, teamId);
        return ResponseEntity.noContent().build();
    }

    // Get all members of a team (non-paginated)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<UserTeamResponseDTO>> getAllMembersOfTeam(@PathVariable Long teamId) {
        List<UserTeamResponseDTO> members = userTeamService.getAllMembersOfTeam(teamId);
        return ResponseEntity.ok(members);
    }

    // Get all members of a team (paginated)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/team/{teamId}/paginated")
    public ResponseEntity<Page<UserTeamResponseDTO>> getMembersOfTeamPaginated(
            @PathVariable Long teamId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserTeamResponseDTO> members = userTeamService.getMembersOfTeamPaginated(teamId, PageRequest.of(page, size));
        return ResponseEntity.ok(members);
    }

    // Get all teams for a user (non-paginated)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserTeamResponseDTO>> getAllTeamsForUser(@PathVariable Long userId) {
        List<UserTeamResponseDTO> teams = userTeamService.getAllTeamsForUser(userId);
        return ResponseEntity.ok(teams);
    }

    // Get all teams for a user (paginated)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}/paginated")
    public ResponseEntity<Page<UserTeamResponseDTO>> getTeamsForUserPaginated(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserTeamResponseDTO> teams = userTeamService.getTeamsForUserPaginated(userId, PageRequest.of(page, size));
        return ResponseEntity.ok(teams);
    }

    // Get a specific user-team membership
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{userId}/{teamId}")
    public ResponseEntity<UserTeamResponseDTO> getUserTeamMembership(
            @PathVariable Long userId,
            @PathVariable Long teamId
    ) {
        UserTeamResponseDTO dto = userTeamService.getUserTeamMembership(userId, teamId);
        return ResponseEntity.ok(dto);
    }
}
