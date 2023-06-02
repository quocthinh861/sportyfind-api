package com.sportyfind.webapi.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserTeamId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "team_id")
    private int teamId;

    // Default constructor
    public UserTeamId() {}

    // Parameterized constructor
    public UserTeamId(Long userId, int teamId) {
        this.userId = userId;
        this.teamId = teamId;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    // Implement equals() and hashCode() methods here.
}
