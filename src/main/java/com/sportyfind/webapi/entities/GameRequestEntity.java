package com.sportyfind.webapi.entities;

import jdk.jfr.Description;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "game_request", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"team_id", "game_id"})
})
public class GameRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameMatchEntity game;

    @Description("0: pending, 1: accepted, 2: rejected")
    @Column(name = "status")
    private int status;

    @Column(name = "createddate")
    private Date createddate;

    @Column(name = "updateddate")
    private Date updateddate;
}