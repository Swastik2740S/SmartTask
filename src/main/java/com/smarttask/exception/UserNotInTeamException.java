package com.smarttask.exception;

public class UserNotInTeamException extends RuntimeException {
    public UserNotInTeamException(Long userId, Long teamId) {
        super("User " + userId + " is not in team " + teamId);
    }
}
