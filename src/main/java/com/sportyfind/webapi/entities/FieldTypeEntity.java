package com.sportyfind.webapi.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "fieldtype")
public class FieldTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fieldtypeid")
    private Integer fieldTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sportid")
    private SportEntity sport;

    @Column(name = "fieldtypename")
    private String fieldTypeName;
}

