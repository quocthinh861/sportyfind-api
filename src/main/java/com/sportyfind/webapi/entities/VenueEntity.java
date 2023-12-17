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

    @Column(name = "district")
    private String district;

    @Column(name = "province")
    private String province;

    @Column(name = "ward")
    private String ward;

    @Column(name = "lati")
    private String lati;

    @Column(name = "longti")
    private String longti;

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


    @Column(name = "additionalInfo", columnDefinition = "TEXT", length = 65535)
    private String additionalInfo;

    @OneToMany(mappedBy = "venue")
    private List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "venue")
    private List<FieldEntity> fields;
}
