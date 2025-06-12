package com.smarttask.controller;

import com.smarttask.dto.UserTeamRequestDTO;
import com.smarttask.dto.UserTeamResponseDTO;
import com.smarttask.model.UserTeam;
import com.smarttask.model.UserTeamKey;
import com.smarttask.service.UserTeamService;
import com.smarttask.repository.UserTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams/members")
public class UserTeamController {

    private final UserTeamService userTeamService;
    private final UserTeamRepository userTeamRepository;

    @Autowired
    public UserTeamController(UserTeamService userTeamService, UserTeamRepository userTeamRepository) {
        this.userTeamService = userTeamService;
        this.userTeamRepository = userTeamRepository;
    }

    // Add a user to a team
    @PostMapping
    public ResponseEntity<UserTeamResponseDTO> addUserToTeam(@RequestBody UserTeamRequestDTO request) {
        UserTeamResponseDTO response = userTeamService.addUserToTeam(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update a user's role in a team
    @PutMapping
    public ResponseEntity<UserTeamResponseDTO> updateUserRole(@RequestBody UserTeamRequestDTO request) {
        UserTeamResponseDTO response = userTeamService.updateUserRoleInTeam(request);
        return ResponseEntity.ok(response);
    }

    // Remove a user from a team
    @DeleteMapping("/{userId}/{teamId}")
    public ResponseEntity<Void> removeUserFromTeam(
            @PathVariable Long userId,
            @PathVariable Long teamId
    ) {
        userTeamService.removeUserFromTeam(userId, teamId);
        return ResponseEntity.noContent().build();
    }

    // Get all members of a team
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<UserTeamResponseDTO>> getAllMembersOfTeam(@PathVariable Long teamId) {
        List<UserTeamResponseDTO> members = userTeamRepository.findAll().stream()
                .filter(ut -> ut.getTeam().getTeamId().equals(teamId))
                .map(ut -> {
                    UserTeamResponseDTO dto = new UserTeamResponseDTO();
                    dto.setUserId(ut.getUser().getUserId());
                    dto.setTeamId(ut.getTeam().getTeamId());
                    dto.setRole(ut.getRole());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(members);
    }

    // Get all teams for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserTeamResponseDTO>> getAllTeamsForUser(@PathVariable Long userId) {
        List<UserTeamResponseDTO> teams = userTeamRepository.findAll().stream()
                .filter(ut -> ut.getUser().getUserId().equals(userId))
                .map(ut -> {
                    UserTeamResponseDTO dto = new UserTeamResponseDTO();
                    dto.setUserId(ut.getUser().getUserId());
                    dto.setTeamId(ut.getTeam().getTeamId());
                    dto.setRole(ut.getRole());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(teams);
    }

    // Get a specific user-team membership
    @GetMapping("/{userId}/{teamId}")
    public ResponseEntity<UserTeamResponseDTO> getUserTeamMembership(
            @PathVariable Long userId,
            @PathVariable Long teamId
    ) {
        UserTeamKey key = new UserTeamKey(userId, teamId);
        UserTeam ut = userTeamRepository.findById(key)
                .orElseThrow(() -> new RuntimeException("Membership not found"));
        UserTeamResponseDTO dto = new UserTeamResponseDTO();
        dto.setUserId(ut.getUser().getUserId());
        dto.setTeamId(ut.getTeam().getTeamId());
        dto.setRole(ut.getRole());
        return ResponseEntity.ok(dto);
    }
}
