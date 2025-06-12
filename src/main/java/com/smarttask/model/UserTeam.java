package com.smarttask.model;

import com.smarttask.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_team")
public class UserTeam {
    @EmbeddedId
    private UserTeamKey id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @MapsId("userId") // Maps userId from UserTeamKey
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("teamId") // Maps teamId from UserTeamKey
    @JoinColumn(name = "team_id")
    private Team team;
}
