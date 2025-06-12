package com.smarttask.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(Long teamId) {
        super("Team not found with ID: " + teamId);
    }
}
