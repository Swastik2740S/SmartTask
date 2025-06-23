package com.smarttask.repository;

import com.smarttask.enums.Priority;
import com.smarttask.enums.TaskStatus;
import com.smarttask.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByStatusAndPriorityAndProject_ProjectIdAndAssignedTo_UserId(
            TaskStatus status,
            Priority priority,
            Long projectId,
            Long assignedTo,
            Pageable pageable
    );

    // For flexible filtering, you can also add more methods as needed.
    Page<Task> findAllByProject_ProjectId(Long projectId, Pageable pageable);
    Page<Task> findAllByAssignedTo_UserId(Long userId, Pageable pageable);

}
