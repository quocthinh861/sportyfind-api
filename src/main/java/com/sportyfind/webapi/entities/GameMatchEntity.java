package com.sportyfind.webapi.entities;

import jdk.jfr.Description;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "game_match", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"team_a_id", "field_booking_id"})
})
public class GameMatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "team_a_id")
    private TeamEntity teamA;

    @ManyToOne
    @JoinColumn(name = "team_b_id")
    private TeamEntity teamB;

    @ManyToOne
    @JoinColumn(name = "field_booking_id")
    private FieldBookingEntity fieldBooking;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Description("0: created, 1: matched, 2: removed, 3: finished")
    private int status = 0;

    @Column(name = "game_type")
    @Description("0: ranking, 1: internal")
    private int gameType = 0;

    @Column(name = "team_a_score")
    private int teamAScore = 0;

    @Column(name = "team_b_score")
    private int teamBScore = 0;

    @Column(name = "winner_team_id")
    private Integer winner = 0;

    @Column(name = "loser_team_id")
    private Integer loser = 0;
}
