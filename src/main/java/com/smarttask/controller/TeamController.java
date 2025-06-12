package com.smarttask.controller;

import com.smarttask.dto.TeamRequestDTO;
import com.smarttask.dto.TeamResponseDTO;
import com.smarttask.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponseDTO> createTeam(@RequestBody TeamRequestDTO teamDTO) {
        TeamResponseDTO newTeam = teamService.createTeam(teamDTO);
        return new ResponseEntity<>(newTeam, HttpStatus.CREATED);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseDTO> getTeamById(@PathVariable Long teamId) {
        TeamResponseDTO team = teamService.getTeamById(teamId);
        return ResponseEntity.ok(team);
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
        List<TeamResponseDTO> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<TeamResponseDTO> updateTeam(
            @PathVariable Long teamId,
            @RequestBody TeamRequestDTO teamDTO
    ) {
        TeamResponseDTO updatedTeam = teamService.updateTeam(teamId, teamDTO);
        return ResponseEntity.ok(updatedTeam);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }
}
