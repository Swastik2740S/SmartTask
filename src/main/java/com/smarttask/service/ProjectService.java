package com.smarttask.service;

import com.smarttask.dto.ProjectRequestDTO;
import com.smarttask.dto.ProjectResponseDTO;
import com.smarttask.enums.ProjectStatus;
import com.smarttask.exception.ProjectNotFoundException;
import com.smarttask.exception.TeamNotFoundException;
import com.smarttask.model.Project;
import com.smarttask.model.Team;
import com.smarttask.repository.ProjectRepository;
import com.smarttask.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,
                          TeamRepository teamRepository,
                          ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
    }

    // Create a new project
    @Transactional
    public ProjectResponseDTO createProject(ProjectRequestDTO requestDTO) {
        if (projectRepository.existsByNameAndTeam_TeamId(requestDTO.getName(), requestDTO.getTeamId())) {
            throw new RuntimeException("Project name already exists for this team.");
        }
        Team team = teamRepository.findById(requestDTO.getTeamId())
                .orElseThrow(() -> new TeamNotFoundException(requestDTO.getTeamId()));

        Project project = new Project();
        project.setName(requestDTO.getName());
        project.setDescription(requestDTO.getDescription());
        project.setTeam(team);
        project.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : ProjectStatus.PLANNING);
        // project.setDeleted(false); // If using soft delete

        Project saved = projectRepository.save(project);
        // log creation event here if needed
        return modelMapper.map(saved, ProjectResponseDTO.class);
    }

    // Get a project by ID
    public ProjectResponseDTO getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        // if (project.isDeleted()) throw new ProjectNotFoundException(projectId); // If using soft delete
        return modelMapper.map(project, ProjectResponseDTO.class);
    }

    // Get all projects (no pagination)
    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                //.filter(project -> !project.isDeleted()) // Uncomment for soft delete
                .map(project -> modelMapper.map(project, ProjectResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Get all projects with pagination and sorting
    public Page<ProjectResponseDTO> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable)
                //.map(project -> !project.isDeleted() ? modelMapper.map(project, ProjectResponseDTO.class): null) // For soft delete
                .map(project -> modelMapper.map(project, ProjectResponseDTO.class));
    }

    // Get projects by team (no pagination)
    public List<ProjectResponseDTO> getProjectsByTeamId(Long teamId) {
        return projectRepository.findByTeam_TeamId(teamId, Pageable.unpaged()).getContent().stream()
                //.filter(project -> !project.isDeleted()) // For soft delete
                .map(project -> modelMapper.map(project, ProjectResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Get projects by team with pagination
    public Page<ProjectResponseDTO> getProjectsByTeamId(Long teamId, Pageable pageable) {
        return projectRepository.findByTeam_TeamId(teamId, pageable)
                //.map(project -> !project.isDeleted() ? modelMapper.map(project, ProjectResponseDTO.class) : null)
                .map(project -> modelMapper.map(project, ProjectResponseDTO.class));
    }

    // Get projects by status
    public List<ProjectResponseDTO> getProjectsByStatus(ProjectStatus status) {
        return projectRepository.findByStatus(status).stream()
                //.filter(project -> !project.isDeleted()) // For soft delete
                .map(project -> modelMapper.map(project, ProjectResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Update project
    @Transactional
    public ProjectResponseDTO updateProject(Long projectId, ProjectRequestDTO requestDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        // if (project.isDeleted()) throw new ProjectNotFoundException(projectId); // For soft delete

        if (requestDTO.getName() != null &&
                !project.getName().equals(requestDTO.getName()) &&
                projectRepository.existsByNameAndTeam_TeamId(requestDTO.getName(), requestDTO.getTeamId() != null ? requestDTO.getTeamId() : project.getTeam().getTeamId())) {
            throw new RuntimeException("Project name already exists for this team.");
        }
        if (requestDTO.getName() != null) {
            project.setName(requestDTO.getName());
        }
        if (requestDTO.getDescription() != null) {
            project.setDescription(requestDTO.getDescription());
        }
        if (requestDTO.getStatus() != null) {
            project.setStatus(requestDTO.getStatus());
        }
        if (requestDTO.getTeamId() != null && !project.getTeam().getTeamId().equals(requestDTO.getTeamId())) {
            Team team = teamRepository.findById(requestDTO.getTeamId())
                    .orElseThrow(() -> new TeamNotFoundException(requestDTO.getTeamId()));
            project.setTeam(team);
        }
        Project updated = projectRepository.save(project);
        // log update event here if needed
        return modelMapper.map(updated, ProjectResponseDTO.class);
    }

    // Hard delete project
    @Transactional
    public void deleteProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException(projectId);
        }
        projectRepository.deleteById(projectId);
        // log delete event here if needed
    }

    // Soft delete project (optional, if using a deleted flag)
    @Transactional
    public void softDeleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        // if (project.isDeleted()) return;
        // project.setDeleted(true);
        projectRepository.save(project);
        // log soft delete event here if needed
    }
}
