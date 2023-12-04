package com.sportyfind.webapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "venue")
public class VenueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venueid")
    private Integer venueId;

    @Column(name = "venuename")
    private String venueName;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "openinghour")
    private Time openingHour;

    @Column(name = "closinghour")
    private Time closingHour;

    @Column(name = "url")
    private String url;

    @Column(name = "phone")
    private String phone;

    @Column(name = "thumbnail")
    private String thumbnail;

    @OneToMany(mappedBy = "venue")
    private List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "venue")
    private List<FieldEntity> fields;
}
