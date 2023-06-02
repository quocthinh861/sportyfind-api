package com.sportyfind.webapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "team_request", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "team_id"})
})
public class TeamRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @Column(name = "status")
    private int status;

    @Column(name = "createddate")
    private Date createddate;
}

