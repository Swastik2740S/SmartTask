package com.smarttask.controller;

import com.smarttask.dto.ProjectRequestDTO;
import com.smarttask.dto.ProjectResponseDTO;
import com.smarttask.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Create Project
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO requestDTO) {
        ProjectResponseDTO response = projectService.createProject(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get Project by ID
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long projectId) {
        ProjectResponseDTO response = projectService.getProjectById(projectId);
        return ResponseEntity.ok(response);
    }

    // Get All Projects
    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        List<ProjectResponseDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    // Get All Projects for a Team
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<ProjectResponseDTO>> getProjectsByTeamId(@PathVariable Long teamId) {
        List<ProjectResponseDTO> projects = projectService.getProjectsByTeamId(teamId);
        return ResponseEntity.ok(projects);
    }

    // Update Project
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable Long projectId,
            @RequestBody ProjectRequestDTO requestDTO
    ) {
        ProjectResponseDTO response = projectService.updateProject(projectId, requestDTO);
        return ResponseEntity.ok(response);
    }

    // Delete Project
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }
}
