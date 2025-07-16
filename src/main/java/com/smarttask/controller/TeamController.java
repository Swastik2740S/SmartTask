package com.smarttask.controller;

import com.smarttask.dto.TeamRequestDTO;
import com.smarttask.dto.TeamResponseDTO;
import com.smarttask.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    // Only ADMIN and PROJECT_MANAGER can create a team
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    @PostMapping
    public ResponseEntity<TeamResponseDTO> createTeam(@Valid @RequestBody TeamRequestDTO teamDTO) {
        TeamResponseDTO newTeam = teamService.createTeam(teamDTO);
        return new ResponseEntity<>(newTeam, HttpStatus.CREATED);
    }

    // Only ADMIN can bulk create teams
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<List<TeamResponseDTO>> createTeamsBulk(@Valid @RequestBody List<TeamRequestDTO> teamDTOs) {
        List<TeamResponseDTO> createdTeams = teamService.createTeamsBulk(teamDTOs);
        return new ResponseEntity<>(createdTeams, HttpStatus.CREATED);
    }

    // Any authenticated user can get team by ID
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseDTO> getTeamById(@PathVariable Long teamId) {
        TeamResponseDTO team = teamService.getTeamById(teamId);
        return ResponseEntity.ok(team);
    }

    // Any authenticated user can get all teams (paged or unpaged)
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getAllTeams(
            Pageable pageable,
            @RequestParam(value = "paged", required = false, defaultValue = "false") boolean paged
    ) {
        if (paged) {
            Page<TeamResponseDTO> teams = teamService.getAllTeams(pageable);
            return ResponseEntity.ok(teams);
        } else {
            List<TeamResponseDTO> teams = teamService.getAllTeams();
            return ResponseEntity.ok(teams);
        }






        
    }

    // Any authenticated user can search teams by name
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public ResponseEntity<List<TeamResponseDTO>> searchTeamsByName(@RequestParam String name) {
        List<TeamResponseDTO> teams = teamService.searchTeamsByName(name);
        return ResponseEntity.ok(teams);
    }

    // Any authenticated user can check if the team name is taken
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/exists")
    public ResponseEntity<Boolean> isTeamNameTaken(@RequestParam String name) {
        boolean exists = teamService.isTeamNameTaken(name);
        return ResponseEntity.ok(exists);
    }

    // Only ADMIN and PROJECT_MANAGER can update a team
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    @PutMapping("/{teamId}")
    public ResponseEntity<TeamResponseDTO> updateTeam(
            @PathVariable Long teamId,
            @Valid @RequestBody TeamRequestDTO teamDTO
    ) {
        TeamResponseDTO updatedTeam = teamService.updateTeam(teamId, teamDTO);
        return ResponseEntity.ok(updatedTeam);
    }

    // Only ADMIN can delete a team
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }
}
