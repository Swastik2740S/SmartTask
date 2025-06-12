package com.smarttask.service;

import com.smarttask.dto.TeamRequestDTO;
import com.smarttask.dto.TeamResponseDTO;
import com.smarttask.exception.TeamNotFoundException;
import com.smarttask.model.Team;
import com.smarttask.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public TeamResponseDTO createTeam(TeamRequestDTO teamDTO) {
        Team team = modelMapper.map(teamDTO, Team.class);
        Team savedTeam = teamRepository.save(team);
        return modelMapper.map(savedTeam, TeamResponseDTO.class);
    }

    public TeamResponseDTO getTeamById(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
        return modelMapper.map(team, TeamResponseDTO.class);
    }

    public List<TeamResponseDTO> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(team -> modelMapper.map(team, TeamResponseDTO.class))
                .collect(Collectors.toList());
    }

    public TeamResponseDTO updateTeam(Long teamId, TeamRequestDTO teamDTO) {
        Team existingTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
        existingTeam.setName(teamDTO.getName());
        existingTeam.setDescription(teamDTO.getDescription());
        Team updatedTeam = teamRepository.save(existingTeam);
        return modelMapper.map(updatedTeam, TeamResponseDTO.class);
    }

    public void deleteTeam(Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new TeamNotFoundException(teamId);
        }
        teamRepository.deleteById(teamId);
    }
}
