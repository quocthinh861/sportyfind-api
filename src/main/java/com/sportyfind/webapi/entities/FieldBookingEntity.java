package com.sportyfind.webapi.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
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

    @Column(name = "isdeleted")
    private boolean isDeleted;

    public double calculatePrice() {
        double diff = this.endTime.getTime() - this.startTime.getTime();
        double diffInHours = diff / (60.0 * 60.0 * 1000.0); // convert milliseconds to hours with floating point
        return diffInHours * this.field.getHourlyRate();
    }
}
