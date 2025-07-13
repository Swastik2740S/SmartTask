package com.smarttask.controller;

import com.smarttask.dto.ProjectRequestDTO;
import com.smarttask.dto.ProjectResponseDTO;
import com.smarttask.enums.ProjectStatus;
import com.smarttask.service.ProjectService;
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
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Create Project
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody ProjectRequestDTO requestDTO) {
        ProjectResponseDTO response = projectService.createProject(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get Project by ID
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long projectId) {
        ProjectResponseDTO response = projectService.getProjectById(projectId);
        return ResponseEntity.ok(response);
    }

    // Get All Projects (with optional pagination)
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getAllProjects(
            Pageable pageable,
            @RequestParam(value = "paged", required = false, defaultValue = "false") boolean paged
    ) {
        if (paged) {
            Page<ProjectResponseDTO> projects = projectService.getAllProjects(pageable);
            return ResponseEntity.ok(projects);
        } else {
            List<ProjectResponseDTO> projects = projectService.getAllProjects();
            return ResponseEntity.ok(projects);
        }
    }

    // Get Projects by Team (with optional pagination)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/team/{teamId}")
    public ResponseEntity<?> getProjectsByTeamId(
            @PathVariable Long teamId,
            Pageable pageable,
            @RequestParam(value = "paged", required = false, defaultValue = "false") boolean paged
    ) {
        if (paged) {
            Page<ProjectResponseDTO> projects = projectService.getProjectsByTeamId(teamId, pageable);
            return ResponseEntity.ok(projects);
        } else {
            List<ProjectResponseDTO> projects = projectService.getProjectsByTeamId(teamId);
            return ResponseEntity.ok(projects);
        }
    }

    // Get Projects by Status
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectResponseDTO>> getProjectsByStatus(@PathVariable ProjectStatus status) {
        List<ProjectResponseDTO> projects = projectService.getProjectsByStatus(status);
        return ResponseEntity.ok(projects);
    }

    // Update Project
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectRequestDTO requestDTO
    ) {
        ProjectResponseDTO response = projectService.updateProject(projectId, requestDTO);
        return ResponseEntity.ok(response);
    }

    // Hard Delete Project
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }

    // Soft Delete Project (if using soft delete)
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{projectId}/soft-delete")
    public ResponseEntity<Void> softDeleteProject(@PathVariable Long projectId) {
        projectService.softDeleteProject(projectId);
        return ResponseEntity.noContent().build();
    }

    // Bulk Create Projects (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<List<ProjectResponseDTO>> createProjectsBulk(
            @Valid @RequestBody List<ProjectRequestDTO> projectRequests
    ) {
        List<ProjectResponseDTO> createdProjects = projectRequests.stream()
                .map(projectService::createProject)
                .toList();
        return new ResponseEntity<>(createdProjects, HttpStatus.CREATED);
    }
}
