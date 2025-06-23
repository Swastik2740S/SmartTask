package com.smarttask.controller;

import com.smarttask.dto.TaskRequestDTO;
import com.smarttask.dto.TaskResponseDTO;
import com.smarttask.enums.Priority;
import com.smarttask.enums.TaskStatus;
import com.smarttask.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Create Task
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO requestDTO) {
        TaskResponseDTO response = taskService.createTask(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get All Tasks (non-paginated)
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // Get All Tasks (paginated)
    @GetMapping("/paginated")
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasksPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.getAllTasksPaginated(page, size));
    }

    // Get Tasks by Project (non-paginated)
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByProjectId(@PathVariable Long projectId) {
        List<TaskResponseDTO> tasks = taskService.getTasksByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }

    // Get Tasks by Project (paginated)
    @GetMapping("/project/{projectId}/paginated")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByProjectIdPaginated(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.getTasksByProjectIdPaginated(projectId, page, size));
    }

    // Get Tasks by User (non-paginated)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskResponseDTO> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    // Get Tasks by User (paginated)
    @GetMapping("/user/{userId}/paginated")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByUserIdPaginated(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.getTasksByUserIdPaginated(userId, page, size));
    }

    // Advanced Search with Filters
    

    // Get Task by ID (must be after all specific routes)
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long taskId) {
        TaskResponseDTO response = taskService.getTaskById(taskId);
        return ResponseEntity.ok(response);
    }

    // Update Task
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskRequestDTO requestDTO) {
        TaskResponseDTO response = taskService.updateTask(taskId, requestDTO);
        return ResponseEntity.ok(response);
    }

    // Delete Task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
