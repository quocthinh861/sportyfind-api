package com.sportyfind.webapi.models;

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
    @ManyToOne
    @JoinColumn(name = "venueid")
    private VenueEntity venue;

    @Id
    @ManyToOne
    @JoinColumn(name = "customerid")
    private CustomerEntity customer;

    @Column(name = "createddate")
    private LocalDateTime createdDate;

    @Column(name = "score")
    private double score;

    @Column(name = "review")
    private String review;
}