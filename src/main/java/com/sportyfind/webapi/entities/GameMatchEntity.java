package com.sportyfind.webapi.entities;

import jdk.jfr.Description;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    @Description("0: canceled, 1: pending, 2: accepted, 3: rejected")
    private int status = 0;

    @Column(name = "game_type")
    @Description("0: ranking, 1: internal")
    private int gameType = 0;
}
