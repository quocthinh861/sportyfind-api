package com.sportyfind.webapi.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "field")
public class FieldEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fieldid")
    private Integer fieldId;

    @Column(name = "fieldname")
    private String fieldName;

    @ManyToOne
    @JoinColumn(name = "fieldtypeid")
    private FieldTypeEntity fieldType;

    @ManyToOne
    @JoinColumn(name = "venueid")
    private VenueEntity venue;

    @ManyToOne
    @JoinColumn(name = "sportid")
    private SportEntity sport;

    @Column(name = "length")
    private double length;

    @Column(name = "width")
    private double width;

    @Column(name = "area")
    private double area;

    @Column(name = "hourlyrate")
    private double hourlyRate;
}