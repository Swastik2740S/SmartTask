package com.smarttask.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
public class UserTeamKey implements Serializable {
    // Getters and Setters
    private Long userId;
    private Long teamId;

    // Default constructor (required by JPA)
    public UserTeamKey() {}

    // Parameterized constructor
    public UserTeamKey(Long userId, Long teamId) {
        this.userId = userId;
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
