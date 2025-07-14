package com.smarttask.service;

import com.smarttask.dto.UserTeamRequestDTO;
import com.smarttask.dto.UserTeamResponseDTO;
import com.smarttask.enums.Role;
import com.smarttask.exception.TeamNotFoundException;
import com.smarttask.exception.UserAlreadyInTeamException;
import com.smarttask.exception.UserNotFoundException;
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

        UserTeamKey key = new UserTeamKey(request.getUserId(), request.getTeamId());
        if (userTeamRepository.findById(key).isPresent()) {
            throw new UserAlreadyInTeamException(request.getUserId(), request.getTeamId());
        }

        UserTeam userTeam = new UserTeam();
        userTeam.setId(key);
        userTeam.setUser(user);
        userTeam.setTeam(team);
        userTeam.setRole(request.getRole());

        userTeamRepository.save(userTeam);
        UserTeam hydrated = userTeamRepository.findById(key).orElseThrow();
        return modelMapper.map(hydrated, UserTeamResponseDTO.class);
    }

    // Bulk add users to a team
    @Transactional
    public List<UserTeamResponseDTO> bulkAddUsersToTeam(List<UserTeamRequestDTO> requests) {
        return requests.stream()
                .map(this::addUserToTeam)
                .collect(Collectors.toList());
    }

    // Bulk remove users from a team
    @Transactional
    public void bulkRemoveUsersFromTeam(List<UserTeamKey> keys) {
        keys.forEach(key -> {
            if (userTeamRepository.existsById(key)) {
                userTeamRepository.deleteById(key);
            }
        });
    }

    // Update a user's role in a team
    @Transactional
    public UserTeamResponseDTO updateUserRoleInTeam(UserTeamRequestDTO request) {
        UserTeamKey key = new UserTeamKey(request.getUserId(), request.getTeamId());
        UserTeam userTeam = userTeamRepository.findById(key)
                .orElseThrow(() -> new UserNotInTeamException(request.getUserId(), request.getTeamId()));

        userTeam.setRole(request.getRole());
        userTeamRepository.save(userTeam);
        UserTeam hydrated = userTeamRepository.findById(key).orElseThrow();
        return modelMapper.map(hydrated, UserTeamResponseDTO.class);
    }

    // Remove a user from a team
    @Transactional
    public void removeUserFromTeam(Long userId, Long teamId) {
        UserTeamKey key = new UserTeamKey(userId, teamId);
        UserTeam userTeam = userTeamRepository.findById(key)
                .orElseThrow(() -> new UserNotInTeamException(userId, teamId));
        userTeamRepository.delete(userTeam);
    }

    // Get all members of a team (non-paginated)
    @Transactional(readOnly = true)
    public List<UserTeamResponseDTO> getAllMembersOfTeam(Long teamId) {
        return userTeamRepository.findByTeam_TeamId(teamId).stream()
                .map(ut -> modelMapper.map(ut, UserTeamResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Get all members of a team (paginated)
    @Transactional(readOnly = true)
    public Page<UserTeamResponseDTO> getMembersOfTeamPaginated(Long teamId, Pageable pageable) {
        return userTeamRepository.findByTeam_TeamId(teamId, pageable)
                .map(ut -> modelMapper.map(ut, UserTeamResponseDTO.class));
    }

    // Filter team members by role (paginated)
    @Transactional(readOnly = true)
    public Page<UserTeamResponseDTO> getMembersOfTeamByRole(Long teamId, Role role, Pageable pageable) {
        return userTeamRepository.findByTeam_TeamIdAndRole(teamId, role, pageable)
                .map(ut -> modelMapper.map(ut, UserTeamResponseDTO.class));
    }

    // Get all teams for a user (non-paginated)
    @Transactional(readOnly = true)
    public List<UserTeamResponseDTO> getAllTeamsForUser(Long userId) {
        return userTeamRepository.findByUser_UserId(userId).stream()
                .map(ut -> modelMapper.map(ut, UserTeamResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Get all teams for a user (paginated)
    @Transactional(readOnly = true)
    public Page<UserTeamResponseDTO> getTeamsForUserPaginated(Long userId, Pageable pageable) {
        return userTeamRepository.findByUser_UserId(userId, pageable)
                .map(ut -> modelMapper.map(ut, UserTeamResponseDTO.class));
    }

    // Get a specific user-team membership
    @Transactional(readOnly = true)
    public UserTeamResponseDTO getUserTeamMembership(Long userId, Long teamId) {
        UserTeamKey key = new UserTeamKey(userId, teamId);
        UserTeam ut = userTeamRepository.findById(key)
                .orElseThrow(() -> new UserNotInTeamException(userId, teamId));
        return modelMapper.map(ut, UserTeamResponseDTO.class);
    }

    // (Optional) Soft delete support (if you add a 'deleted' flag to UserTeam)
    // public void softDeleteUserFromTeam(Long userId, Long teamId) { ... }

    // (Optional) Audit logging can be implemented with @EntityListeners or a separate audit service
}
