package com.sportyfind.webapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "starttime")
    private LocalDateTime startTime;

    @Column(name = "endtime")
    private LocalDateTime endTime;

    @Column(name = "bookingstatus")
    private String bookingStatus;
}
