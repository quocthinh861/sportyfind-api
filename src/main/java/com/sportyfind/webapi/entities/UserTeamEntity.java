package com.sportyfind.webapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "user_team",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "team_id"})
        })
public class UserTeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @Column(name = "createddate")
    private Date createdDate;

    @Column(name = "role")
    private String role = "MEMBER";

    // Constructors, getters, and setters

    public UserTeamEntity() {
        // Default constructor
    }

    public UserTeamEntity(UserEntity user, TeamEntity team) {
        this.user = user;
        this.team = team;
    }
}