package com.sportyfind.webapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "fieldbooking")
public class FieldBookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fieldbookingid")
    private Integer fieldBookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fieldid")
    private FieldEntity field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid")
    private UserEntity customer;

    @Column(name = "bookingdate")
    private Date bookingDate;

    @Column(name = "starttime")
    private Time startTime;

    @Column(name = "endtime")
    private Time endTime;

    @Column(name = "bookingstatus")
    private String bookingStatus;
}
