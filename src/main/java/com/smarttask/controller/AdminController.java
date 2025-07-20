//package com.smarttask.controller;
//
//import com.smarttask.dto.UserResponseDTO;
//import com.smarttask.dto.ProjectRequestDTO;
//import com.smarttask.dto.ProjectResponseDTO;
//import com.smarttask.dto.TaskResponseDTO;
//import com.smarttask.dto.TeamRequestDTO;
//import com.smarttask.dto.TeamResponseDTO;
//import com.smarttask.service.UserService;
//import com.smarttask.service.TeamService;
//import com.smarttask.service.ProjectService;
//import com.smarttask.service.TaskService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/admin")
//@PreAuthorize("hasRole('ADMIN')")
//public class AdminController {
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private TeamService teamService;
//    @Autowired
//    private ProjectService projectService;
//    @Autowired
//    private TaskService taskService;
//
//    // --- USER MANAGEMENT ---
//    @GetMapping("/users")
//    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }
//
//    @DeleteMapping("/users/{userId}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
//        userService.deleteUser(userId);
//        return ResponseEntity.noContent().build();
//    }
//
//    // --- TEAM MANAGEMENT ---
//    @GetMapping("/teams")
//    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
//        return ResponseEntity.ok(teamService.getAllTeams());
//    }
//
//    @PostMapping("/teams")
//    public ResponseEntity<TeamResponseDTO> createTeam(@RequestBody TeamRequestDTO teamDTO) { // Fixed: TeamRequestDTO
//        return ResponseEntity.ok(teamService.createTeam(teamDTO));
//    }
//
//    @PutMapping("/teams/{teamId}")
//    public ResponseEntity<TeamResponseDTO> updateTeam(@PathVariable Long teamId, @RequestBody TeamRequestDTO teamDTO) { // Fixed: TeamRequestDTO
//        return ResponseEntity.ok(teamService.updateTeam(teamId, teamDTO));
//    }
//
//    @DeleteMapping("/teams/{teamId}")
//    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
//        teamService.deleteTeam(teamId);
//        return ResponseEntity.noContent().build();
//    }
//
//    // --- PROJECT MANAGEMENT ---
//    @GetMapping("/projects")
//    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
//        return ResponseEntity.ok(projectService.getAllProjects());
//    }
//
//    @PostMapping("/projects")
//    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO projectDTO) { // Fixed: ProjectRequestDTO
//        return ResponseEntity.ok(projectService.createProject(projectDTO));
//    }
//
//    @PutMapping("/projects/{projectId}")
//    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long projectId, @RequestBody ProjectRequestDTO projectDTO) { // Fixed: ProjectRequestDTO
//        return ResponseEntity.ok(projectService.updateProject(projectId, projectDTO));
//    }
//
//    @DeleteMapping("/projects/{projectId}")
//    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
//        projectService.deleteProject(projectId);
//        return ResponseEntity.noContent().build();
//    }
//
//    // --- TASK MANAGEMENT ---
//    @GetMapping("/tasks")
//    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
//        return ResponseEntity.ok(taskService.getAllTasks());
//    }
//
//    @DeleteMapping("/tasks/{taskId}")
//    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
//        taskService.deleteTask(taskId);
//        return ResponseEntity.noContent().build();
//    }
//
//    // --- ADMIN DASHBOARD ---
//    @GetMapping("/dashboard")
//    public ResponseEntity<String> adminDashboard() {
//        return ResponseEntity.ok("Welcome to the admin dashboard!");
//    }
//}
