package com.sportyfind.webapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "sport")
public class SportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sportid")
    private Integer sportId;

    @Column(name = "sportname")
    private String sportName;
}
