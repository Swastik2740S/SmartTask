package com.smarttask.model;

import java.io.Serializable;
import java.util.Objects;

public class UserTeamKey implements Serializable {
    private Long userId;
    private Long teamId;

    // Default constructor (required by JPA)
    public UserTeamKey() {}

    // Parameterized constructor
    public UserTeamKey(Long userId, Long teamId) {
        this.userId = userId;
        this.teamId = teamId;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    // equals() and hashCode() (required for composite keys)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTeamKey that = (UserTeamKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(teamId, that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, teamId);
    }
}
