package com.sportyfind.webapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "venue")
public class VenueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venueid")
    private Integer venueId;

    @ManyToOne
    @JoinColumn(name = "ownerid")
    private OwnerEntity owner;

    @Column(name = "venuename")
    private String venueName;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "openinghour")
    private LocalTime openingHour;

    @Column(name = "closinghour")
    private LocalTime closingHour;
}
