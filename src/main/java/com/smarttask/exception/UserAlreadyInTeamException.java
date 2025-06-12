package com.smarttask.exception;

public class UserAlreadyInTeamException extends RuntimeException {
    public UserAlreadyInTeamException(Long userId, Long teamId) {
        super("User " + userId + " is already in team " + teamId);
    }
}
