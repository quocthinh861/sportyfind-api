package com.sportyfind.webapi.entities;

import jdk.jfr.Description;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "game_match_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "game_match_id"})
})
public class GameMatchUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_match_id")
    private GameMatchEntity gameMatch;

    private int status = 0;
}
