package com.smarttask.service;

import com.smarttask.dto.ProjectRequestDTO;
import com.smarttask.dto.ProjectResponseDTO;
import com.smarttask.exception.TeamNotFoundException;
import com.smarttask.model.Project;
import com.smarttask.model.Team;
import com.smarttask.repository.ProjectRepository;
import com.smarttask.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ProjectService(ProjectRepository projectRepository, TeamRepository teamRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ProjectResponseDTO createProject(ProjectRequestDTO requestDTO) {
        Team team = teamRepository.findById(requestDTO.getTeamId())
                .orElseThrow(() -> new TeamNotFoundException(requestDTO.getTeamId()));

        Project project = new Project();
        project.setName(requestDTO.getName());
        project.setDescription(requestDTO.getDescription());
        project.setTeam(team);
        project.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : com.smarttask.enums.ProjectStatus.PLANNING);

        Project saved = projectRepository.save(project);
        return modelMapper.map(saved, ProjectResponseDTO.class);
    }

    public ProjectResponseDTO getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));
        return modelMapper.map(project, ProjectResponseDTO.class);
    }

    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(project -> modelMapper.map(project, ProjectResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProjectResponseDTO> getProjectsByTeamId(Long teamId) {
        return projectRepository.findAll().stream()
                .filter(project -> project.getTeam().getTeamId().equals(teamId))
                .map(project -> modelMapper.map(project, ProjectResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ProjectResponseDTO updateProject(Long projectId, ProjectRequestDTO requestDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        project.setName(requestDTO.getName());
        project.setDescription(requestDTO.getDescription());
        if (requestDTO.getStatus() != null) {
            project.setStatus(requestDTO.getStatus());
        }
        if (requestDTO.getTeamId() != null && !project.getTeam().getTeamId().equals(requestDTO.getTeamId())) {
            Team team = teamRepository.findById(requestDTO.getTeamId())
                    .orElseThrow(() -> new TeamNotFoundException(requestDTO.getTeamId()));
            project.setTeam(team);
        }
        Project updated = projectRepository.save(project);
        return modelMapper.map(updated, ProjectResponseDTO.class);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException("Project not found with ID: " + projectId);
        }
        projectRepository.deleteById(projectId);
    }
}
