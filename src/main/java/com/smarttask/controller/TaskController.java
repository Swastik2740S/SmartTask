package com.smarttask.controller;

import com.smarttask.dto.TaskRequestDTO;
import com.smarttask.dto.TaskResponseDTO;
import com.smarttask.enums.Priority;
import com.smarttask.enums.TaskStatus;
import com.smarttask.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // 1. Create Task
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO requestDTO) {
        TaskResponseDTO response = taskService.createTask(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. Bulk Create Tasks
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<List<TaskResponseDTO>> bulkCreateTasks(@RequestBody List<TaskRequestDTO> requestDTOs) {
        List<TaskResponseDTO> created = taskService.bulkCreateTasks(requestDTOs);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // 3. Get All Tasks (non-paginated)
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // 4. Get All Tasks (paginated)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/paginated")
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasksPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.getAllTasksPaginated(page, size));
    }

    // 5. Get Tasks by Project (non-paginated)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByProjectId(@PathVariable Long projectId) {
        List<TaskResponseDTO> tasks = taskService.getTasksByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }

    // 6. Get Tasks by Project (paginated)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/project/{projectId}/paginated")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByProjectIdPaginated(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.getTasksByProjectIdPaginated(projectId, page, size));
    }

    // 7. Get Tasks by User (non-paginated)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskResponseDTO> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    // 8. Get Tasks by User (paginated)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}/paginated")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByUserIdPaginated(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.getTasksByUserIdPaginated(userId, page, size));
    }

    // 9. Get Tasks by Status (paginated)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/status")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByStatus(
            @RequestParam TaskStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.getTasksByStatus(status, PageRequest.of(page, size)));
    }

    // 10. Get Tasks by Priority (paginated)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/priority")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByPriority(
            @RequestParam Priority priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.getTasksByPriority(priority, PageRequest.of(page, size)));
    }

    // 11. Get Tasks by Project, Status, and User (paginated)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/filter")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByProjectStatusUser(
            @RequestParam Long projectId,
            @RequestParam TaskStatus status,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.getTasksByProjectStatusUser(
                projectId, status, userId, PageRequest.of(page, size)));
    }

    // 12. Get Tasks by Due Date Range (non-paginated)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/due-date")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByDueDateRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(taskService.getTasksByDueDateRange(start, end));
    }

    // 13. Check for duplicate task title within a project
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByTitleAndProject(
            @RequestParam String title,
            @RequestParam Long projectId) {
        return ResponseEntity.ok(taskService.existsByTitleAndProject(title, projectId));
    }

    // 14. Get Task by ID (must be after all specific routes)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long taskId) {
        TaskResponseDTO response = taskService.getTaskById(taskId);
        return ResponseEntity.ok(response);
    }

    // 15. Update Task
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskRequestDTO requestDTO) {
        TaskResponseDTO response = taskService.updateTask(taskId, requestDTO);
        return ResponseEntity.ok(response);
    }

    // 16. Delete Task
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
