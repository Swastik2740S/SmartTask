package com.smarttask.service;

import com.smarttask.dto.UserTeamRequestDTO;
import com.smarttask.dto.UserTeamResponseDTO;
import com.smarttask.exception.TeamNotFoundException;
import com.smarttask.exception.UserNotFoundException;
import com.smarttask.exception.UserAlreadyInTeamException;
import com.smarttask.exception.UserNotInTeamException;
import com.smarttask.model.*;
import com.smarttask.repository.TeamRepository;
import com.smarttask.repository.UserRepository;
import com.smarttask.repository.UserTeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTeamService {

    private final UserTeamRepository userTeamRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserTeamService(
            UserTeamRepository userTeamRepository,
            UserRepository userRepository,
            TeamRepository teamRepository,
            ModelMapper modelMapper
    ) {
        this.userTeamRepository = userTeamRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
    }

    // Add a user to a team
    @Transactional
    public UserTeamResponseDTO addUserToTeam(UserTeamRequestDTO request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new TeamNotFoundException(request.getTeamId()));

        if (userTeamRepository.findById(new UserTeamKey(request.getUserId(), request.getTeamId())).isPresent()) {
            throw new UserAlreadyInTeamException(request.getUserId(), request.getTeamId());
        }

        UserTeam userTeam = new UserTeam();
        userTeam.setId(new UserTeamKey(request.getUserId(), request.getTeamId()));
        userTeam.setUser(user);
        userTeam.setTeam(team);
        userTeam.setRole(request.getRole());

        UserTeam saved = userTeamRepository.save(userTeam);
        return modelMapper.map(saved, UserTeamResponseDTO.class);
    }

    // Update a user's role in a team
    @Transactional
    public UserTeamResponseDTO updateUserRoleInTeam(UserTeamRequestDTO request) {
        UserTeamKey key = new UserTeamKey(request.getUserId(), request.getTeamId());
        UserTeam userTeam = userTeamRepository.findById(key)
                .orElseThrow(() -> new UserNotInTeamException(request.getUserId(), request.getTeamId()));

        userTeam.setRole(request.getRole());
        UserTeam updated = userTeamRepository.save(userTeam);
        return modelMapper.map(updated, UserTeamResponseDTO.class);
    }

    // Remove a user from a team
    @Transactional
    public void removeUserFromTeam(Long userId, Long teamId) {
        UserTeamKey key = new UserTeamKey(userId, teamId);
        UserTeam userTeam = userTeamRepository.findById(key)
                .orElseThrow(() -> new UserNotInTeamException(userId, teamId));
        userTeamRepository.delete(userTeam);
    }

    // Get all members of a team
    public List<UserTeamResponseDTO> getAllMembersOfTeam(Long teamId) {
        return userTeamRepository.findByTeam_TeamId(teamId).stream()
                .map(ut -> modelMapper.map(ut, UserTeamResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Get all teams for a user
    public List<UserTeamResponseDTO> getAllTeamsForUser(Long userId) {
        return userTeamRepository.findByUser_UserId(userId).stream()
                .map(ut -> modelMapper.map(ut, UserTeamResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Get a specific user-team membership
    public UserTeamResponseDTO getUserTeamMembership(Long userId, Long teamId) {
        UserTeamKey key = new UserTeamKey(userId, teamId);
        UserTeam ut = userTeamRepository.findById(key)
                .orElseThrow(() -> new UserNotInTeamException(userId, teamId));
        return modelMapper.map(ut, UserTeamResponseDTO.class);
    }

    // Paginated: Get all members of a team
    public Page<UserTeamResponseDTO> getMembersOfTeamPaginated(Long teamId, Pageable pageable) {
        return userTeamRepository.findByTeam_TeamId(teamId, pageable)
                .map(ut -> modelMapper.map(ut, UserTeamResponseDTO.class));
    }

    // Paginated: Get all teams for a user
    public Page<UserTeamResponseDTO> getTeamsForUserPaginated(Long userId, Pageable pageable) {
        return userTeamRepository.findByUser_UserId(userId, pageable)
                .map(ut -> modelMapper.map(ut, UserTeamResponseDTO.class));
    }

    // (Optional) Bulk add users to a team
    @Transactional
    public List<UserTeamResponseDTO> bulkAddUsersToTeam(List<UserTeamRequestDTO> requests) {
        return requests.stream()
                .map(this::addUserToTeam)
                .collect(Collectors.toList());
    }
}
