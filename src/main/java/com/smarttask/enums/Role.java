package com.smarttask.enums;

/**
 * Defines the possible roles for users in the system with their associated permissions.
 */
public enum Role {
    /**
     * Full access: manage users, teams, projects, and system settings.
     */
    ADMIN,
    
    /**
     * Project management role: create/edit/delete projects, assign tasks,
     * and manage team composition for their projects.
     */
    PROJECT_MANAGER,
    
    /**
     * Team leadership role: manage tasks and members within their assigned team,
     * but not across all projects/teams.
     */
    TEAM_LEAD,
    
    /**
     * Standard user role: create/edit their own tasks and participate
     * in assigned projects/teams.
     */
    MEMBER,
    
    /**
     * Read-only access to projects and tasks.
     */
    VIEWER,
    
    /**
     * Limited, temporary, or external access for clients and contractors.
     */
    GUEST
}