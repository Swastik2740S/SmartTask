package com.smarttask.service;

import com.smarttask.dto.TeamRequestDTO;
import com.smarttask.dto.TeamResponseDTO;
import com.smarttask.exception.TeamNotFoundException;
import com.smarttask.model.Team;
import com.smarttask.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TeamService(TeamRepository teamRepository, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
    }

    // Create a new team with a uniqueness check
    public TeamResponseDTO createTeam(TeamRequestDTO teamDTO) {
        if (teamRepository.existsByName(teamDTO.getName())) {
            throw new RuntimeException("Team name already exists");
        }
        Team team = modelMapper.map(teamDTO, Team.class);
        Team savedTeam = teamRepository.save(team);
        return modelMapper.map(savedTeam, TeamResponseDTO.class);
    }

    // Get a team by its ID
    public TeamResponseDTO getTeamById(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
        return modelMapper.map(team, TeamResponseDTO.class);
    }

    // Get all teams (non-paginated)
    public List<TeamResponseDTO> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(team -> modelMapper.map(team, TeamResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Get all teams (paginated)
    public Page<TeamResponseDTO> getAllTeams(Pageable pageable) {
        return teamRepository.findAll(pageable)
                .map(team -> modelMapper.map(team, TeamResponseDTO.class));
    }

    // Search teams by (partial) name
    public List<TeamResponseDTO> searchTeamsByName(String name) {
        return teamRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(team -> modelMapper.map(team, TeamResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Check if a team name is already taken
    public boolean isTeamNameTaken(String name) {
        return teamRepository.existsByName(name);
    }

    // Update a team's details with uniqueness check
    public TeamResponseDTO updateTeam(Long teamId, TeamRequestDTO teamDTO) {
        Team existingTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        if (!existingTeam.getName().equals(teamDTO.getName()) && teamRepository.existsByName(teamDTO.getName())) {
            throw new RuntimeException("Team name already exists");
        }

        existingTeam.setName(teamDTO.getName());
        existingTeam.setDescription(teamDTO.getDescription());
        Team updatedTeam = teamRepository.save(existingTeam);
        return modelMapper.map(updatedTeam, TeamResponseDTO.class);
    }

    // Delete a team by its ID
    public void deleteTeam(Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new TeamNotFoundException(teamId);
        }
        teamRepository.deleteById(teamId);
    }

    // Bulk create teams
    public List<TeamResponseDTO> createTeamsBulk(List<TeamRequestDTO> teamDTOs) {
        List<Team> teams = teamDTOs.stream()
                .map(dto -> modelMapper.map(dto, Team.class))
                .collect(Collectors.toList());
        List<Team> saved = teamRepository.saveAll(teams);
        return saved.stream()
                .map(team -> modelMapper.map(team, TeamResponseDTO.class))
                .collect(Collectors.toList());
    }



}
