package com.smarttask.repository;

import com.smarttask.enums.ProjectStatus;
import com.smarttask.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Find all projects with a specific status
    List<Project> findByStatus(ProjectStatus status);

    // Check if a project with the same name exists within a given team
    boolean existsByNameAndTeam_TeamId(String name, Long teamId);

    // Pagination support for all projects
    Page<Project> findAll(Pageable pageable);

    // Pagination support for projects by team
    Page<Project> findByTeam_TeamId(Long teamId, Pageable pageable);
}
