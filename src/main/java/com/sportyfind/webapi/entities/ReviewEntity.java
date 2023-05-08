package com.sportyfind.webapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "review")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewid")
    private int reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venueid")
    private VenueEntity venue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private UserEntity customer;

    @Column(name = "createddate")
    private LocalDateTime createdDate;

    @Column(name = "score")
    private double score;

    @Column(name = "review")
    private String review;
}