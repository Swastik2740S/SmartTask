package com.smarttask.service;

import com.smarttask.dto.TaskRequestDTO;
import com.smarttask.dto.TaskResponseDTO;
import com.smarttask.enums.Priority;
import com.smarttask.enums.TaskStatus;
import com.smarttask.exception.ProjectNotFoundException;
import com.smarttask.exception.UserNotFoundException;
import com.smarttask.model.Project;
import com.smarttask.model.Task;
import com.smarttask.model.User;
import com.smarttask.repository.ProjectRepository;
import com.smarttask.repository.TaskRepository;
import com.smarttask.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository,
                       UserRepository userRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // Create a new task
    @Transactional
    public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
        Project project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException(requestDTO.getProjectId()));

        User assignedTo = null;
        if (requestDTO.getAssignedTo() != null) {
            assignedTo = userRepository.findById(requestDTO.getAssignedTo())
                    .orElseThrow(() -> new UserNotFoundException(requestDTO.getAssignedTo()));
        }

        Task task = new Task();
        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : TaskStatus.TODO);
        task.setPriority(requestDTO.getPriority() != null ? requestDTO.getPriority() : Priority.MEDIUM);
        task.setDueDate(requestDTO.getDueDate());
        task.setProject(project);
        task.setAssignedTo(assignedTo);

        Task saved = taskRepository.save(task);
        return modelMapper.map(saved, TaskResponseDTO.class);
    }

    // Get task by ID
    public TaskResponseDTO getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));
        return modelMapper.map(task, TaskResponseDTO.class);
    }

    // Get all tasks (non-paginated)
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(task -> modelMapper.map(task, TaskResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Get all tasks (paginated)
    public Page<TaskResponseDTO> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(task -> modelMapper.map(task, TaskResponseDTO.class));
    }

    // Get tasks by project (paged and non-paged)
    public List<TaskResponseDTO> getTasksByProjectId(Long projectId) {
        return taskRepository.findAllByProject_ProjectId(projectId, Pageable.unpaged())
                .getContent().stream()
                .map(task -> modelMapper.map(task, TaskResponseDTO.class))
                .collect(Collectors.toList());
    }

    public Page<TaskResponseDTO> getTasksByProjectId(Long projectId, Pageable pageable) {
        return taskRepository.findAllByProject_ProjectId(projectId, pageable)
                .map(task -> modelMapper.map(task, TaskResponseDTO.class));
    }

    // Get tasks by assigned user (paged and non-paged)
    public List<TaskResponseDTO> getTasksByUserId(Long userId) {
        return taskRepository.findAllByAssignedTo_UserId(userId, Pageable.unpaged())
                .getContent().stream()
                .map(task -> modelMapper.map(task, TaskResponseDTO.class))
                .collect(Collectors.toList());
    }

    public Page<TaskResponseDTO> getTasksByUserId(Long userId, Pageable pageable) {
        return taskRepository.findAllByAssignedTo_UserId(userId, pageable)
                .map(task -> modelMapper.map(task, TaskResponseDTO.class));
    }

    // Filter tasks by status (paged)
    public Page<TaskResponseDTO> getTasksByStatus(TaskStatus status, Pageable pageable) {
        return taskRepository.findAllByStatus(status, pageable)
                .map(task -> modelMapper.map(task, TaskResponseDTO.class));
    }

    // Filter tasks by priority (paged)
    public Page<TaskResponseDTO> getTasksByPriority(Priority priority, Pageable pageable) {
        return taskRepository.findAllByPriority(priority, pageable)
                .map(task -> modelMapper.map(task, TaskResponseDTO.class));
    }

    // Filter by project, status, and assigned user (paged)
    public Page<TaskResponseDTO> getTasksByProjectStatusUser(Long projectId, TaskStatus status, Long userId, Pageable pageable) {
        return taskRepository.findAllByProject_ProjectIdAndStatusAndAssignedTo_UserId(projectId, status, userId, pageable)
                .map(task -> modelMapper.map(task, TaskResponseDTO.class));
    }

    // Filter by due date range
    public List<TaskResponseDTO> getTasksByDueDateRange(LocalDateTime start, LocalDateTime end) {
        return taskRepository.findAllByDueDateBetween(start, end).stream()
                .map(task -> modelMapper.map(task, TaskResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Update a task
    @Transactional
    public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO requestDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        if (requestDTO.getTitle() != null) task.setTitle(requestDTO.getTitle());
        if (requestDTO.getDescription() != null) task.setDescription(requestDTO.getDescription());
        if (requestDTO.getStatus() != null) task.setStatus(requestDTO.getStatus());
        if (requestDTO.getPriority() != null) task.setPriority(requestDTO.getPriority());
        if (requestDTO.getDueDate() != null) task.setDueDate(requestDTO.getDueDate());
        if (requestDTO.getProjectId() != null && !task.getProject().getProjectId().equals(requestDTO.getProjectId())) {
            Project project = projectRepository.findById(requestDTO.getProjectId())
                    .orElseThrow(() -> new ProjectNotFoundException(requestDTO.getProjectId()));
            task.setProject(project);
        }
        if (requestDTO.getAssignedTo() != null) {
            User assignedTo = userRepository.findById(requestDTO.getAssignedTo())
                    .orElseThrow(() -> new UserNotFoundException(requestDTO.getAssignedTo()));
            task.setAssignedTo(assignedTo);
        }

        Task updated = taskRepository.save(task);
        return modelMapper.map(updated, TaskResponseDTO.class);
    }

    // Delete a task (hard delete)
    @Transactional
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Task not found with ID: " + taskId);
        }
        taskRepository.deleteById(taskId);
    }

    // Bulk create tasks
    @Transactional
    public List<TaskResponseDTO> bulkCreateTasks(List<TaskRequestDTO> requestDTOs) {
        List<Task> tasks = requestDTOs.stream().map(dto -> {
            Project project = projectRepository.findById(dto.getProjectId())
                    .orElseThrow(() -> new ProjectNotFoundException(dto.getProjectId()));
            User assignedTo = null;
            if (dto.getAssignedTo() != null) {
                assignedTo = userRepository.findById(dto.getAssignedTo())
                        .orElseThrow(() -> new UserNotFoundException(dto.getAssignedTo()));
            }
            Task task = new Task();
            task.setTitle(dto.getTitle());
            task.setDescription(dto.getDescription());
            task.setStatus(dto.getStatus() != null ? dto.getStatus() : TaskStatus.TODO);
            task.setPriority(dto.getPriority() != null ? dto.getPriority() : Priority.MEDIUM);
            task.setDueDate(dto.getDueDate());
            task.setProject(project);
            task.setAssignedTo(assignedTo);
            return task;
        }).collect(Collectors.toList());

        List<Task> saved = taskRepository.saveAll(tasks);
        return saved.stream()
                .map(task -> modelMapper.map(task, TaskResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Check for duplicate task title within a project
    public boolean existsByTitleAndProject(String title, Long projectId) {
        return taskRepository.existsByTitleAndProject_ProjectId(title, projectId);
    }

    // Paginated helper methods for controller compatibility
    public Page<TaskResponseDTO> getTasksByProjectIdPaginated(Long projectId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return getTasksByProjectId(projectId, pageable);
    }

    public Page<TaskResponseDTO> getTasksByUserIdPaginated(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return getTasksByUserId(userId, pageable);
    }

    public Page<TaskResponseDTO> getAllTasksPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return getAllTasks(pageable);
    }

    // Optional: Soft delete method (requires 'deleted' field in Task entity)
    /*
    @Transactional
    public void softDeleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));
        task.setDeleted(true);
        taskRepository.save(task);
    }
    */
}
