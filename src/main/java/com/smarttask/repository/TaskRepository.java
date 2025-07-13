package com.smarttask.repository;

import com.smarttask.enums.Priority;
import com.smarttask.enums.TaskStatus;
import com.smarttask.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Find all tasks by project (paged)
    Page<Task> findAllByProject_ProjectId(Long projectId, Pageable pageable);

    // Find all tasks assigned to a user (paged)
    Page<Task> findAllByAssignedTo_UserId(Long userId, Pageable pageable);

    // Find all tasks by status (paged)
    Page<Task> findAllByStatus(TaskStatus status, Pageable pageable);

    // Find all tasks by priority (paged)
    Page<Task> findAllByPriority(Priority priority, Pageable pageable);

    // Find all tasks by project and status (paged)
    Page<Task> findAllByProject_ProjectIdAndStatus(Long projectId, TaskStatus status, Pageable pageable);

    // Find all tasks by project and assigned user (paged)
    Page<Task> findAllByProject_ProjectIdAndAssignedTo_UserId(Long projectId, Long userId, Pageable pageable);

    // Find all tasks by status, priority, project, and assigned user (paged)
    Page<Task> findAllByStatusAndPriorityAndProject_ProjectIdAndAssignedTo_UserId(
            TaskStatus status,
            Priority priority,
            Long projectId,
            Long assignedToUserId,
            Pageable pageable
    );

    // Find tasks by due date range (non-paged)
    List<Task> findAllByDueDateBetween(LocalDateTime start, LocalDateTime end);

    Page<Task> findAllByProject_ProjectIdAndStatusAndAssignedTo_UserId(
            Long projectId,
            TaskStatus status,
            Long userId,
            Pageable pageable
    );




    // Check for task title uniqueness within a project
    boolean existsByTitleAndProject_ProjectId(String title, Long projectId);


}
