package com.sportyfind.webapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "game_match")
public class GameMatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "team_A_id")
    private TeamEntity teamA;

    @ManyToOne
    @JoinColumn(name = "team_B_id")
    private TeamEntity teamB;

    @ManyToOne
    @JoinColumn(name = "field_booking_id")
    private FieldBookingEntity fieldBooking;

    @Column(name = "status")
    private int status = 1;

    @Column(name = "created_at")

    private Date createdAt;

    @Column(name = "description")
    private String description;
}
